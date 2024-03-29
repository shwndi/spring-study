package myspring.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理工厂类
 *
 * @author czy
 * @date 2021/7/17
 */
public class ProxyFactory {
    /**
     * 输入一个目标类和一组Proxy接口实现, 输出一个代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass,
                                    final List<Proxy> proxyList){
        
        return (T)Enhancer.create(targetClass, new MethodInterceptor() {
            /**
             * 代理方法, 每次调用目标方法时都会先创建一个 ProxyChain 对象, 然后调用该对象的 doProxyChain() 方法.
             */
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                ProxyChain proxyChain = new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList);
                System.out.println("proxyChain"+proxyChain);
                Object o = proxyChain.doProxyChain();
                System.out.println("doProxyChain"+o);
                return o;
            }
        });
    }
}
