package myspring.aop.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;

/**
 * @author czy
 * @date 2021/7/17
 */
public abstract class AspectProxy implements Proxy {
    private static Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        begin();
        try {
            if ((intercept(targetMethod, methodParams))) {
                before(targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetMethod, methodParams);
            } else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            logger.error("proxy failure",e);
            error(targetMethod,methodParams,e);
            throw e;
        }finally {
            end();
        }
        return result;

    }
    /**
     * 开始增强
     */
    public void begin() {
    }

    /**
     * 切入点判断
     */
    public boolean intercept(Method method, Object[] params) throws Throwable {
        return true;
    }

    /**
     * 前置增强
     * @return
     */
    public void before(Method method, Object[] params) throws Throwable {
    }

    /**
     * 后置增强
     */
    public void after(Method method, Object[] params) throws Throwable {
    }

    /**
     * 异常增强
     */
    public void error(Method method, Object[] params, Throwable e) {
    }

    /**
     * 最终增强
     */
    public void end() {
    }
}
