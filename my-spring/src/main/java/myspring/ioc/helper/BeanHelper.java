package myspring.ioc.helper;

import myspring.ioc.bean.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * BeanHelper 在类加载时就会创建一个Bean容器 BEAN_MAP
 * 然后获取到应用中所有bean的Class对象
 * 再通过反射创建bean实例
 * 储存到 BEAN_MAP 中.
 * @author czy
 * @date 2021/7/12
 */
public final class BeanHelper {
    /**
     * BEAN_MAP相当于一个Spring容器, 拥有应用所有bean的实例
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static{
        //获取所有的bean
        Set<Class<?>> bean = ClassHelper.getBean();
        //实例化bean，放入bean容器
        for (Class<?> clazz : bean) {
            Object o = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz,o);
        }
    }
    /**
     * 获取 Bean 容器
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz){
        return (T) BEAN_MAP.get(clazz);
    }
    /**
     * 设置bean实例
     */
    public static void setBean(Class<?> clazz,Object o){
        BEAN_MAP.put(clazz,o);
    }
}










