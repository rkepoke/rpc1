package xuexi.huo.server;

import xuexi.huo.client.ConsumerRequest;
import xuexi.huo.service.HelloService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.net.Socket;

/**
 * 在这里实现真正的方法调用
 */
public class ProcessHandler implements Runnable {
    //    private HelloService helloService;  你如果把这里写si，还是就报转换错误，应该用OBject接受
    private Object service;
    private Socket socket;

    public ProcessHandler(Object service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    /**
     * 用于处理穿过来的对象，然后返回结果;这里是通过流的方式返回的
     * 同一个工程下引入不同模块的对象使用,进行三步处理
     * 1.在调用模块的pom.xml中，引用被调用模块的依赖
     * 2将被调用模块 install一下
     * 3将调用模块rebuild module一下
     */
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            //  ConsumerRequest consumnerRequest = inputStream.readObject();
            //同一个工程下引入不同模块的对象使用  要三步见上面
            ConsumerRequest consumnerRequest = (ConsumerRequest) inputStream.readObject();
            //到这里才真正调用实现类的方法去实现服务
            Object result = invoke(consumnerRequest);
            //把得到的结果返回
            outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 这个invoke才是远程调用真正的服务提供者，不过在使用是报了Unhandled exception: java.lang.NoSuchMethodException异常，抛出即可
     * 查看了JavaAPI文档的Class 的getMethod方法才知道，getMethod只能调用public声明的方法，而getDeclaredMethod基本可以调用任何类型声明的方法
     *
     * @param consumnerRequest
     * @return
     */
    private Object invoke(ConsumerRequest consumnerRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("实现服务已被调用！");
        Object[] parameters = consumnerRequest.getParameters();
        Class[] parametersType = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parametersType[i] = parameters[i].getClass();
        }
        //z这里报Unhandled exception: java.lang.NoSuchMethodException 异常，解决见上
        Method method = service.getClass().getMethod(consumnerRequest.getMethodName(), parametersType);
        return method.invoke(service, parameters);
    }
}













