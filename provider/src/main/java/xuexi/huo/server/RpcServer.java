package xuexi.huo.server;

import xuexi.huo.service.impl.HelloServiceImpl;

/**
 * 模拟服务端的
 * 在启动服务端和客户端，进行通信尝试的时候
 * 服务端报：java.io.WriteAbortedException: writing aborted; java.io.NotSerializableException: xuexi.huo.client.ConsumerRequest
 * Caused by: java.io.NotSerializableException: xuexi.huo.client.ConsumerRequest
 * Exception in thread "pool-1-thread-1" java.lang.NullPointerException
 * 应该是xuexi.huo.client.ConsumerRequest没有序列化的意思。
 * 解决方法很简单，只要在ConsumerRequest上实现Serializable即可。但是要明白的是，网络间的通信数据是要进行序列化的
 */
public class RpcServer {
    public static void main(String[] args) {
        ProviderServer providerServer=new ProviderServer();
        providerServer.publisher(new HelloServiceImpl(),12345);
    }
}
