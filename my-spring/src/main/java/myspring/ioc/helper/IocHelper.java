package myspring.ioc.helper;

import myspring.ioc.annocation.MyAutowired;
import myspring.ioc.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * IOC的实现
 * IOC的实现思路如下:
 *
 * 1、首先有一个配置文件定义了应用的基础包, 也就是Java源码路径.
 * 2、读取基础包名, 然后通过类加载器获取到应用中所有的Class对象, 存储到一个集合中.
 * 3、获取应用中所有Bean (Controller和Service) 的Class对象, 通过反射创建实例, 然后存储到 Bean容器中.
 * 4、遍历Bean容器中的所有Bean, 为所有带 @Autowired 注解的属性注入实例.
 * 5、IOC操作要在应用启动时就完成, 所以必须写在静态代码块中.
 *
 * 我们需要做的就是遍历bean容器中的所有bean
 * 为所有带@MyAutowired注解的属性注入实例
 * 这个实力将从bean容器中获取
 * @author czy
 * @date 2021/7/12
 */
public final class IocHelper {
    static{
        //获取bean容器
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //判断不为空
        if (MapUtils.isNotEmpty(beanMap)){
            //遍历所有的bean
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> key = beanEntry.getKey();
                //bean实例
                Object value = beanEntry.getValue();
                //暴力反射获取属性
                Field[] declaredFields = key.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(declaredFields)){
                    for (Field declaredField : declaredFields) {
                        //判断属性是否带Autowired注解
                        if (declaredField.isAnnotationPresent(MyAutowired.class)){
                            //属性类型
                            Class<?> beanFieldClass   = declaredField.getType();
                            //如果beanFieldClass是接口, 就获取接口对应的实现类
                            beanFieldClass = findImplementClass(beanFieldClass );
                            //获取Class类对应的实例
                            Object beanFieldInstance  = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(value, declaredField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取接口对应的实现类
     */
    public  static Class<?> findImplementClass(Class<?> interfaceClass){
        Class<?> implementClass = interfaceClass;
        //接口对应的所有实现类
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(interfaceClass);
        if (CollectionUtils.isNotEmpty(classSetBySuper)){
            //获取第一个实现类
            implementClass = classSetBySuper.iterator().next();
        }
        return implementClass;
    }
}
