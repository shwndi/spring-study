package myspring;

import myspring.aop.Helper.AopHelper;
import myspring.ioc.helper.BeanHelper;
import myspring.ioc.helper.ClassHelper;
import myspring.ioc.helper.IocHelper;
import myspring.ioc.util.ClassUtil;
import myspring.mvc.helper.ControllerHelper;

/**
 * 我们创建了ClassHelper, BeanHelper, IocHelper, ControllerHelper,AopHelper这四个Helper类
 *  我们需要一个入口程序来加载他们(实际上是加载静态代码块)
 *  当然就算没有这个入口程序, 这些类也会被加载, 我们这里只是为了让加载更加集中.
 * @author czy
 * @date 2021/7/12
 */
public final class HelperLoader {
    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                ControllerHelper.class,
                AopHelper.class,
                IocHelper.class
        };
        for (Class<?> clazz : classList) {
            ClassUtil.loadClass(clazz.getName());
        }
    }
}
