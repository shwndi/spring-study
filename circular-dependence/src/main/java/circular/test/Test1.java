package circular.test;


import circular.app.AppConfig;
import circular.service.X;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author czy
 * @date 2021/7/9
 */
public class Test1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        ac.setAllowBeanDefinitionOverriding(false);
        ac.setAllowCircularReferences(false);
        System.out.println(ac.getBean(X.class));
    }
}
