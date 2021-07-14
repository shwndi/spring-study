package myspring.ioc.helper;

import myspring.ioc.annocation.MyController;
import myspring.ioc.annocation.MyService;
import myspring.ioc.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 借助 ClassUtil 来实现 ClassHelper 助手类
 * ClassHelper 助手类在自身被加载的时候通过 ConfigHelper 助手类获取应用的基础包名
 * 然后通过 ClassUtil 工具类来获取基础包名下所有类, 存储到 CLASS_SET 集合中.
 * 除此之外, 其他的方法在后面的代码中会经常被使用到.
 *
 * @author czy
 * @date 2021/7/12
 */
public final class ClassHelper {
    /**
     * 定义类集合（存放在基础包名下的所有类）
     */
    private static final Set<Class<?>> CLASS_SET;

    /**
     * 静态代码块初始化包名下类信息
     */
    static{
        //获取基础包名
        String basePackage = ConfigHelper.getAppBasePackage();
        //获取基础包名下的所有类
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取基础包名下所有类
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取基础包名下所有Service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(MyService.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取基础包名下所有 Controller 类
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(MyController.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }
    /**
     * 获取基础包名下所有 Bean 类（包括：Controller、Service）
     */
    public static Set<Class<?>> getBeanClass(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * 获取基础包名下某父类的所有子类 或某接口的所有实现类
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(clazz)&&!superClass.equals(clazz)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }
    /**
     * 获取基础包名下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }
}
