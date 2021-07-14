package myspring.mvc.helper;

import myspring.ioc.annocation.MyRequestMapping;
import myspring.ioc.helper.ClassHelper;
import myspring.mvc.bean.Handler;
import myspring.mvc.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 映射处理器
 * ControllerHelper 助手类定义了一个"请求-处理器" 的映射 REQUEST_MAP
 * REQUEST_MAP 就相当于Spring MVC里的映射处理器
 * 接收到请求后返回对应结果的处理器.
 *
 * REQUEST_MAP 映射处理器的实现逻辑如下:
 *
 * 1、首先通过 ClassHelper 工具类获取到应用中所有Controller的Class对象
 * 2、然后遍历Controller及其所有方法
 * 3、将所有带 @RequestMapping 注解的方法封装为处理器
 * 4、将 @RequestMapping 注解里的请求路径和请求方法封装成请求对象, 然后存入 REQUEST_MAP 中.
 *
 * @author czy
 * @date 2021/7/12
 */
public class ControllerHelper {
    /**
     * REQUEST_MAP为 "请求-处理器" 的映射
     */
    private static final Map<Request, Handler> REQUEST_MAP = new HashMap<Request, Handler>();

    static{
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)){
            for (Class<?> clazz : controllerClassSet) {
                //暴力反射获取所有方法
                Method[] methods = clazz.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)){
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(MyRequestMapping.class)){
                            MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
                            //请求路径
                            String requestPath = annotation.value();
                            //请求方法
                            String requestMethod = annotation.method().name();

                            //封装请求和处理器
                            Request request = new Request( requestMethod,requestPath);
                            Handler handler = new Handler(clazz, method);
                            REQUEST_MAP.put(request,handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     *
     * @param requestMethod Controller请求方法
     * @param requestPath Controller请求路径
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_MAP.get(request);
    }
}
