package com.popsun.reflection;

import com.popsun.reflection.dynamicproxy.CglibDynamicProxyOne;
import com.popsun.reflection.object.Subject;
import com.popsun.reflection.object.impl.RealSubject;
import com.popsun.reflection.object.impl.SimpleObject;
import com.popsun.reflection.util.Log;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.Proxy;

import java.lang.reflect.Method;


/**
 * @author 吴志祥
 * @create 2020-03-06 11:28
 * CGLIB实现的动态代理
 */
public class CglibDynamicProxyMainTest {
    public static void main(String[] args) {
        Subject subject = new RealSubject();
        CglibDynamicProxyOne cglibDynamicProxyOne = new CglibDynamicProxyOne(subject);
        //针对接口编程
        Object object = Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), cglibDynamicProxyOne);
        if (object instanceof Subject) {
            Subject proxySubject = (Subject) object;
            proxySubject.sayHello();
            proxySubject.rent();
            proxySubject.toString();
        }
        object = Enhancer.create(RealSubject.class, cglibDynamicProxyOne);
        if (object instanceof Subject) {
            Subject proxySubject = (Subject) object;
            proxySubject.sayHello();
            proxySubject.rent();
            proxySubject.toString();
        }
        //终极大招
        object = Enhancer.create(RealSubject.class, (MethodInterceptor) (obj, method, args1, proxy) -> {
            Log.info("Proxy2 begin。。。。。。");
            proxy.invokeSuper(obj, args1);
            Log.info("Proxy2 end。。。。。。");
            return null;
        });
        if (object instanceof RealSubject) {
            RealSubject proxySubject = (RealSubject) object;
            proxySubject.sayHello();
            proxySubject.rent();
            proxySubject.toString();
            proxySubject.getClass();//这个不会被代理到
        }
        //还可以使用CallbackFilter对想要代理的对象设置不同的Callback，这里暂不展开
    }

    public static void testImmutableBean() {
        RealSubject bean = new RealSubject();
        bean.setData("12");
        RealSubject immutableBean = (RealSubject) ImmutableBean.create(bean); //创建不可变类,注意这里有问题，待后续讨论
        bean.setData("Hello world, again"); //可以通过底层对象来进行修改
        //immutableBean.setData("Hello cglib"); //直接修改将throw exception
    }
}
