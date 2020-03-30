package xuexi.huo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 监听端口，来一个请求开启一个线程处理一个请求
 */
public class ProviderServer {
    /**
     * 阿里巴巴开发手册建议使用ThreadPoolExecutor 创建线程池，避免资源耗尽风险这里就简单用Executor实现
     */
    private ExecutorService executor = Executors.newCachedThreadPool();

    public void publisher(Object service, Integer port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket=new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new ProcessHandler(service,socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
