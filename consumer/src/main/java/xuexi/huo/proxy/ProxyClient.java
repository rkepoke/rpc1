package xuexi.huo.proxy;

import xuexi.huo.service.HelloService;

import java.lang.reflect.Proxy;

/**
 * 该类是生成动态代理的
 */
public class ProxyClient {


    /**
     * @param helloService
     * @param host
     * @param port
     * @return
     */
//    public HelloService clientProxy(HelloService helloService, String host, int port) {
//        return (HelloService) Proxy.newProxyInstance(helloService.getClass().getClassLoader(),helloService.getClass().getInterfaces(),new RemoteInvocationHandler(host,port));
//    }



    /**
     * 发现以上方法是硬编码不好，不够通用，只能接受HelloService服务，应该用泛型，
     * @param interfaceService
     * @param host
     * @param port
     * @return
     */
//    public T clientProxy(T interfaceService, String host, int port) {
//        return (T) Proxy.newProxyInstance(interfaceService.getClass().getClassLoader(),interfaceService.getClass().getInterfaces(),new RemoteInvocationHandler(host,port));
//    }



    /**
     * 一开始写泛型方法写错了，方法的书写格式不对
     * 运行时发现报Exception in thread "main" java.lang.NullPointerException 空指针异常，
     * 百度了好久，因为一下的方式是通过实现类动态代理对象的方式，而我用的是接口，赋值为null，所以会报空指针异常
     *
     * 我想了一下，换一种方式 用接口的去构造。
     * @param //interfaceService
     * @param host
     * @param port
     * @param <T>
     * @return
     */
//    public <T> T clientProxy(T interfaceService, String host, int port) {
//        return (T) Proxy.newProxyInstance(interfaceService.getClass().getClassLoader(),interfaceService.getClass().getInterfaces(),new RemoteInvocationHandler(host,port));
//    }


    /**
     * 出现了ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to xuexi.huo.service.HelloService错误
     * 百度了一下，一开始没懂，看不懂别人的解释，最好后补充了一下Java反射的相关知识才看懂的，这里简单解释一下
     * 1首先要去实现INvocationHandler接口，供代理对象进行方法的调用，对原方法进行增强
     * 2Proxy；类通过指定的classLoader对象和一组interfaces接口创建动态代理类
     * 3通过反射机制获取获取动态代理的构造函数，参数类型是调用处理器接口类型
     * clazz.getConstructor(new Class[]){InvocationHandler.class};
     * 4.通过构造函数创建代理类的实例，此时需要调用处理器对象作为参数传入（interface）constructor.newInstance(new Object[]{handler})
     * ClassCastException:问题出在类型转化异常。要想得到一组接口，两种办法
     * 1是由接口的实现类  实现类.getClass().getinterfaces();获得
     * 2是直接把接口自身传过去 new Class[]{interfaceClass}
     * 而interfaceClass.getClass().getInterfaces()这么写 获得的是 接口对象的所有接口，所以会报错
     * 两种解决办法：1传一个实现类进来调用就行了,但是我们要做远程调用，实现类是在服务端所以不能用这种方法
     * 2直接把接口自身传过去去构造代理类即可
     * @param interfaceClass
     * @param host
     * @param port
     * @param <T>
     * @return
     */
//    public <T> T  clientProxy(Class<T > interfaceClass, String host, int port) {
//        return (T) Proxy.newProxyInstance(interfaceClass.getClass().getClassLoader(),interfaceClass.getClass().getInterfaces(),new RemoteInvocationHandler(host,port));
//    }

    /**
     * 这种写法，测试通过。
     * @param interfaceClass
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public <T> T  clientProxy(Class<T > interfaceClass, String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class[]{interfaceClass},new RemoteInvocationHandler(host,port));
    }
}
