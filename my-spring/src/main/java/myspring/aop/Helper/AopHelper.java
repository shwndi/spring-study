package myspring.aop.Helper;

import myspring.aop.annocation.Aspect;
import myspring.aop.proxy.AspectProxy;
import myspring.aop.proxy.Proxy;
import myspring.aop.proxy.ProxyFactory;
import myspring.ioc.helper.BeanHelper;
import myspring.ioc.helper.ClassHelper;
import myspring.ioc.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 用于初始化aop框架
 *
 * @author czy
 * @date 2021/7/17
 */
public final class AopHelper {
    private static Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            //切面类、集合类映射
            Map<Class<?>, Set<Class<?>>> aspectMap = createAspectMap();
            //目标类-切面对象列表的映射
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(aspectMap);
            //把切面对象植入到目标类中，创建代理对象
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetCalss = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyFactory.createProxy(targetCalss, proxyList);
                //覆盖Bean容器里目标类对应的实例, 下次从Bean容器获取的就是代理对象了
                BeanHelper.setBean(targetCalss, proxy);
            }
        }catch (Exception e) {
            logger.error("aop failure", e);
        }
    }
    /**
     * 获取切面类-目标类集合的映射
     */
    private static Map<Class<?>,Set<Class<?>>> createAspectMap() throws Exception{
        HashMap<Class<?>, Set<Class<?>>> aspectMap = new HashMap<>();
        addAspectProxy(aspectMap);
        return aspectMap;
    }
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> aspectMap)throws Exception{
        Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> aspectClass : aspectClassSet) {
            if (aspectClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = aspectClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet =  createTargetClassSet(aspect);
                aspectMap.put(aspectClass,targetClassSet);
            }
        }
    }
    private static Set<Class<?>> createTargetClassSet(Aspect aspect)throws Exception{
        HashSet<Class<?>> targetClassSet = new HashSet<>();
        String pkg = aspect.pkg();
        String cls = aspect.cls();
        if (!pkg.equals("")&&!cls.equals("")) {
            targetClassSet.add(Class.forName(pkg+"."+cls));
        }else if (!pkg.equals("")){
            targetClassSet.addAll(ClassUtil.getClassSet(pkg));
        }
        return targetClassSet;
    }
    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> aspectMap)throws Exception{
        HashMap<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : aspectMap.entrySet()) {
            Class<?> aspectClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy aspect= (Proxy) aspectClass.newInstance();
                if (targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(aspect);
                }else{
                    List<Proxy> aspectList = new ArrayList<>();
                    aspectList.add(aspect);
                    targetMap.put(targetClass,aspectList);
                }
            }
        }
        return targetMap;
    }
}

