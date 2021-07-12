package myspring.ioc.annocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理器方法注解
 * @author czy
 * @date 2021/7/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequestMapping {
    /**
     * 请求路径
     *
     * @return
     */
    String  value() default "";

    /**
     * 请求方法
     *
     * @return
     */
    RequestMethod method() default RequestMethod.GET;

}

