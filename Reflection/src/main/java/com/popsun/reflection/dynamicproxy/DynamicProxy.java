package com.popsun.reflection.dynamicproxy;

import com.popsun.reflection.object.Subject;
import com.popsun.reflection.util.Log;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 吴志祥
 * @create 2020-03-06 11:21
 * JDK实现的动态代理
 */
@AllArgsConstructor
public class DynamicProxy implements InvocationHandler {

    private Subject subject;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.info("Proxy begin。。。。。。");
        method.invoke(subject, args);
        Log.info("Proxy end。。。。。。");
        return null;
    }
}
