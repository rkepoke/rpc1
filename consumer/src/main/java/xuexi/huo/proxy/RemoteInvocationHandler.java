package xuexi.huo.proxy;

import xuexi.huo.client.ConsumerRequest;
import xuexi.huo.client.Transport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.PrivateKey;

/**
 * 这个类是实现调用处理器接口的
 */
public class RemoteInvocationHandler implements InvocationHandler {
    /**
     * 用作确定 调用那台服务器提供的服务
     */
    private String host;
    private Integer port;

    public RemoteInvocationHandler(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 因为要远程调用，所以想到的方法是，把所有的方法名，参数等信息都封装成对象传输
     * 首先封装相关值，然后调用 Transport 去建立Socket做网络连接，发送消息,接受消息
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // private String host;  忘记方法里不能用private 了
        ConsumerRequest consumerRequest=new ConsumerRequest();
        consumerRequest.setClassName(method.getDeclaringClass().getName());
        consumerRequest.setMethodName(method.getName());
        consumerRequest.setParameters(args);
        Transport transport=new Transport(host,port);
        return transport.sendRequest(consumerRequest);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


}
