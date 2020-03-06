package com.popsun.reflection.dynamicproxy;


import com.popsun.reflection.object.Subject;
import com.popsun.reflection.util.Log;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 吴志祥
 * @create 2020-03-06 13:29
 * CGLIB实现的动态代理
 */
@AllArgsConstructor
public class CglibDynamicProxyOne implements InvocationHandler {
    private Subject subject;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Log.info("Proxy begin。。。。。。");
        Object o = method.invoke(subject, args);
        Log.info("Proxy end。。。。。。");
        return o;
    }
}
