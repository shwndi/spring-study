package myspring.mvc.helper;

import myspring.mvc.bean.Param;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 前端控制器接收到HTTP请求后, 从HTTP中获取请求参数, 然后封装到Param对象中
 *
 * @author czy
 * @date 2021/7/12
 */
public final class RequestHelper {
    public static Param createParam(HttpServletRequest request)throws IOException{
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        //没有参数
        if (!parameterNames.hasMoreElements()){
            return null;
        }
        //get和post参数都能获取得到
        while(parameterNames.hasMoreElements()){
            String fieldName = parameterNames.nextElement();
            String parameter = request.getParameter(fieldName);
            paramMap.put(fieldName,parameter);
        }
        return new Param(paramMap);
    }

}
