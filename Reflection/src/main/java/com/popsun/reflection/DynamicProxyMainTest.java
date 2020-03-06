package com.popsun.reflection;

import com.popsun.reflection.dynamicproxy.DynamicProxy;
import com.popsun.reflection.object.Subject;
import com.popsun.reflection.object.impl.RealSubject;
import com.popsun.reflection.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author 吴志祥
 * @create 2020-03-06 11:28
 * JDK实现的动态代理
 */
public class DynamicProxyMainTest {
    public static void main(String[] args) {
        //JDK实现的动态代理
        Subject realSubject = new RealSubject();
        InvocationHandler invocationHandler = new DynamicProxy(realSubject);
        //第二个参数必须是接口类型，否则报错
        Subject subject = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), invocationHandler);
        subject.sayHello();
        subject.rent();
        Log.info("判断subject的类型是否为Subject：" + (subject instanceof Subject));
        Log.info("判断subject的类型是否为RealSubject：" + (subject instanceof RealSubject));
        Log.info("打印subject的确切类型：" + subject.getClass().getName());
        Log.info("判断原始对象realSubject是否为代理对象：" + Proxy.isProxyClass(realSubject.getClass()));
        Log.info("判断代理后的对象subject是否为代理对象：" + Proxy.isProxyClass(subject.getClass()));
        InvocationHandler invocationHandler1 = Proxy.getInvocationHandler(subject);
        Log.info("判断前后的调用处理器是否为1个：" + invocationHandler.equals(invocationHandler1));
    }
}
