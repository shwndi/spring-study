package circular.pojo.test;

import circular.controller.UserController;
import myspring.mvc.HelperLoader;
import myspring.ioc.helper.BeanHelper;
import myspring.ioc.helper.ClassHelper;

import java.util.Set;

/**
 * IOC测试
 * IOC实现思路：1、获取配置路径下handwritten.framework.app.base_package=circular下的类文件
 *              放入Set<Class>中
 *              2、通过Set<Class>初始化bean，然后放在Map<Class,Bean>中
 *              3、IOC类中通过Map<Class,Bean>获取到类信息和Bean对象，并在需要的地方注入Bean信息作为字段值
 *              完成实例化
 * IOC测试思路：
 *              1、通过获取Class集合测试第一步
 *              2、通过获取Bean集合来测试第二步
 *              3、通过获取带有自动注入注解的Bean和注入的Bean来测试第三步
 *
 * 操作方法：打印获取的类信息
 *
 * @author czy
 * @date 2021/7/13
 */
public class Test2 {

    public static void main(String[] args) throws InterruptedException {
        HelperLoader.init();
        Set<Class<?>> beanClass = ClassHelper.getBeanClass();
        System.out.println(beanClass);
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        for (Class<?> aClass : classSet) {
            System.out.println(aClass);
        }
        UserController bean1 = BeanHelper.getBean(UserController.class);
        System.out.println(bean1);
        System.out.println(bean1.userService);
    }
}
