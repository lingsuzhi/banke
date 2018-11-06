package com.lsz.pxy;
import org.springframework.core.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestMain {
    Animal animal = null;

    public static void main(String[] args) {
//        ParameterNameDiscoverer parameterNameDiscoverer =
//                new LocalVariableTableParameterNameDiscoverer();
//        try {
//            String[] parameterNames = parameterNameDiscoverer
//                    .getParameterNames(TestMain.class.getDeclaredMethod("test",
//                            String.class, int.class));
//            System.out.print("test : ");
//            for (String parameterName : parameterNames) {
//                System.out.print(parameterName + ' ');
//            }
//        } catch (NoSuchMethodException | SecurityException e) {
//            e.printStackTrace();
//        }

        TestMain main1 = new TestMain();


        main1.testDo();
    }

    public  void test(String name12, int age11) {
        System.out.println(name12 + age11);
    }
    public void testDo(){
        InvocationHandler pxyHandler = new InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ParameterNameDiscoverer parameterNameDiscoverer =
                        new LocalVariableTableParameterNameDiscoverer();
                String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
                System.out.println(parameterNames);
                System.out.println(method.getName());
                return null;
            }
        };
        Animal proxy   =(Animal)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{Animal.class},pxyHandler);
        animal = proxy;

        animal.run();
        animal.eat("饭啊");
    }
}