package com.popsun.reflection;

import com.popsun.reflection.object.impl.SimpleObject;
import com.popsun.reflection.util.Log;

import java.lang.reflect.*;

/**
 * @author 吴志祥
 * @create 2020-03-05 15:08
 */
public class ReflectMainTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        SimpleObject simpleObject = new SimpleObject();
        Class clazzForObject = simpleObject.getClass();
        Class clazzForClass = SimpleObject.class;
        Class clazzForName = Class.forName("com.popsun.reflection.object.impl.SimpleObject");
        Log.info("通过对象获取的Class信息和通过类获取的是否equal相等：" + clazzForObject.equals(clazzForClass));
        Log.info("通过对象获取的Class信息和通过类获取的是否==相等：" + (clazzForObject == clazzForClass));
        Log.info("通过对象获取的Class信息和通过类全路径名字获取的是否equal相等：" + clazzForObject.equals(clazzForName));
        Log.info("通过对象获取的Class信息和通过类全路径名字获取的是否==相等：" + (clazzForObject == clazzForName));
        Constructor[] constructors = clazzForName.getConstructors();
        for (Constructor constructor : constructors) {
            Log.info("声明为public的构造方法：" + constructor.toGenericString());
            Type[] types = constructor.getGenericParameterTypes();
            String name = "可变参数调用";
            Integer[] ints = new Integer[6];
            if (types.length == 2 && types[0].getTypeName().equals(name.getClass().getName())) {
                if (types[1] instanceof Class) {
                    Class param2 = (Class) types[1];
                    if (param2.getName().equals(ints.getClass().getName())) {
                        SimpleObject simpleObject1 = (SimpleObject) constructor.newInstance(name, ints);
                        Log.info("反射通过可变参数new构造方法创建出来的对象：" + simpleObject1);
                    }
                }
            }
        }
        constructors = clazzForName.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            Log.info("所有的构造方法：" + constructor.toGenericString());
        }
        Log.info("类所在的包：" + clazzForObject.getPackage().getName());
        ClassLoader classLoader = clazzForObject.getClassLoader();
        Log.info("类的classloader：" + classLoader.toString());

        simpleObject = (SimpleObject) clazzForObject.newInstance();
        Field[] fields = clazzForObject.getDeclaredFields();
        for (Field field : fields) {
            Log.info("类下面所有的属性：" + Modifier.toString(field.getModifiers()) + " " + field.getType().getName() + " " + field.getName());
            if (!Modifier.isFinal(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {//(field.getModifiers() & Modifier.PUBLIC) != 0) {
                field.set(simpleObject, "run");
            } else if (Modifier.isPrivate(field.getModifiers())) {
                Method method = clazzForObject.getMethod("set" + upperCaseFirst(field.getName()), field.getType());
                if (method != null) {
                    method.invoke(simpleObject, "run");
                }
//                //不建议下面的这种写法
//                field.setAccessible(true);
//                field.set(simpleObject, "1234");
//                field.setAccessible(false);
            }

        }
        fields = clazzForObject.getFields();
        for (Field field : fields) {
            Log.info("类下面公共的属性：" + field.getName());
            if (!Modifier.isFinal(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {//(field.getModifiers() & Modifier.PUBLIC) != 0) {
                field.set(simpleObject, "Running");
            }
        }
        Method[] methods = clazzForObject.getMethods();
        for (Method method : methods) {
            Log.info("类下面所有的public方法：" + Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() + "(" + method.getParameterTypes() + ")");
            if (method.getName().equals("setCode")) {
                method.invoke(simpleObject, "12211");
            }
        }
        methods = clazzForObject.getDeclaredMethods();
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                Log.info("类下面所有的非public的方法：" + Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName());
            }
        }
        Log.info("反射通过默认构造方法创建出来的对象：" + simpleObject);

        Log.info("父类清单："+clazzForClass.getSuperclass());
        Log.info("接口清单："+clazzForClass.getInterfaces().length);

    }

    public static String upperCaseFirst(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
