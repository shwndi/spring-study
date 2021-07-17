package myspring.aop.proxy;

/**
 * @author czy
 * @date 2021/7/17
 */
public interface Proxy {
    /**
     * 执行链式代理
     * 链式代理：将多个代理通过一条链子串联起来，一个一个执行，
     * 执行顺序取决于代理添加到链上的先后顺序
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable ;
}
