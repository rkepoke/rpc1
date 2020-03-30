package xuexi.huo.client;

import xuexi.huo.proxy.ProxyClient;
import xuexi.huo.service.HelloService;

/**
 * 写一个main方法用于模拟服务消费方
 */
public class RpcClient {
    public static void main(String[] args) {
        ProxyClient proxy=new ProxyClient();
        /*
        HelloService helloService=null;
        helloService=proxy.clientProxy(helloService,"127.0.0.1",12345);
        */
        HelloService helloService = proxy.clientProxy(HelloService.class,"127.0.0.1",12345);
        String result = helloService.sayHello("小小");
        System.out.println(result);
    }
}
