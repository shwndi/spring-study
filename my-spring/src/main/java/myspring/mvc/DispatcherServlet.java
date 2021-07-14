package myspring.mvc;
import com.alibaba.fastjson.JSON;
import myspring.ioc.helper.BeanHelper;
import myspring.ioc.helper.ConfigHelper;
import myspring.ioc.util.ReflectionUtil;
import myspring.mvc.bean.Data;
import myspring.mvc.bean.Param;
import myspring.mvc.bean.View;
import myspring.mvc.helper.ControllerHelper;
import myspring.mvc.bean.Handler;
import myspring.mvc.HelperLoader;
import myspring.mvc.helper.RequestHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 实现前端控制器
 *
 * 前端控制器实际上是一个servlet, 这里配置的是拦截所有请求, 在服务器启动时实例化.
 *
 * 当DispatcherServlet实例化时, 首先执行 init() 方法,
 *  这时会调用{@link HelperLoader#init() init} 方法来加载相关的helper类, 并注册处理相应资源的Servlet.
 *
 *  对于每一次客户端请求都会执行 service() 方法,
 *  这时会首先将请求方法和请求路径封装为Request对象,
 *  然后从映射处理器 (REQUEST_MAP) 中获取到处理器.
 *  接着从客户端请求中获取到Param参数对象, 执行处理器方法.
 *  最后判断处理器方法的返回值,
 *   -若为view类型, 则跳转到jsp页面
 *   -若为data类型, 则返回json数据.
 *
 * @author czy
 * @date 2021/7/12
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关的helper类
        HelperLoader.init();

        //获取ServletContext对象，用于注册Servlet
        ServletContext servletContext = config.getServletContext();

        //注册处理jsp和静态资源servlet
        registerServler(servletContext);
    }

    /**
     *  DefaultServlet和JspServlet都是由Web容器创建
     *  org.apache.catalina.servlets.DefaultServlet
     *  org.apache.jasper.servlet.JspServlet
     * @param servletContext
     */
    private void registerServler(ServletContext servletContext) {
        //动态注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath());

        //动态注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath());
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String reqMethod = req.getMethod().toUpperCase();
        String reqPath = req.getPathInfo();

        //这里根据Tomcat的配置路径有两种情况, 一种是 "/userList", 另一种是 "/context地址/userList".
        String[] splits = reqPath.split("/");
        if(splits.length>2){
            reqPath = "/"+splits[2];
        }

        //根据请求获取处理器(这里类似于SpringMVC中的映射处理器)
        Handler handler = ControllerHelper.getHandler(reqMethod, reqPath);
        if (handler!=null){
            Class<?> controllerClass = handler.getControllerClass();
            Object bean = BeanHelper.getBean(controllerClass);

            //初始化参数
            Param param = RequestHelper.createParam(req);

            //调用与请求对应的方法(这里类似于SpringMVC中的处理器适配器)
            Object result;
            Method controllerMethod = handler.getControllerMethod();

            if (param ==null||param.isEmpty()){
                result = ReflectionUtil.invokeMethod(bean,controllerMethod);
            }else{
                result = ReflectionUtil.invokeMethod(bean,controllerMethod,param);
            }

            //跳转页面或返回json数据(这里类似于SpringMVC中的视图解析器)
            if (result instanceof View){
                handlerViewResult((View)result,req,res);
            }else if (result instanceof Data){
                handlerDataResult((Data)result,req,res);
            }

        }


    }

    /**
     * 跳转页面
     * @param view
     * @param req
     * @param res
     */
    private void handlerViewResult(View view, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtils.isNotEmpty(path)) {
            if (path.startsWith("/")) { //重定向
                res.sendRedirect(req.getContextPath() + path);
            } else { //请求转发
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, res);
            }
        }
    }

    /**
     * 返回json请求
     * @param data
     * @param req
     * @param res
     */
    private void handlerDataResult(Data data, HttpServletRequest req, HttpServletResponse res) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            PrintWriter writer = res.getWriter();
            String json = JSON.toJSON(model).toString();
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}
