package circular.test;

import myspring.ioc.helper.ClassHelper;

import java.util.Set;

/**
 * @author czy
 * @date 2021/7/13
 */
public class Test2 {
    public static void main(String[] args) {
        Set<Class<?>> bean = ClassHelper.getBean();
        System.out.println(bean);
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        for (Class<?> aClass : classSet) {
        System.out.println(aClass);

        }
    }
}
