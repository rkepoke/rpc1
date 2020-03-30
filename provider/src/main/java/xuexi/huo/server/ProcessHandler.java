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
    private Object service;
    private Socket socket;

    public ProcessHandler(Object service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    /**
     * 用于处理穿过来的对象，然后返回结果;这里是通过流的方式返回
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
            ConsumerRequest consumnerRequest = (ConsumerRequest) inputStream.readObject();
            Object result = invoke(consumnerRequest);
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
     * 服务请求的真正实现
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
        Method method = service.getClass().getMethod(consumnerRequest.getMethodName(), parametersType);
        return method.invoke(service, parameters);
    }
}













