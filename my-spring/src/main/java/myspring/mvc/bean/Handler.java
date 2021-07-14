package myspring.mvc.bean;

import java.lang.reflect.Method;

/**
 * 处理器：封装了Controller对象和Method
 *
 * @author czy
 * @date 2021/7/12
 */
public class Handler {
    /**
     * Controller 类
     */
    private Class<?> controllerClass;
    /**
     * Controller 方法
     */
    private Method controllerMethod;

    public Handler(Class<?> controllerClass, Method controllerMethod){
        this.controllerClass= controllerClass;
        this.controllerMethod = controllerMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
