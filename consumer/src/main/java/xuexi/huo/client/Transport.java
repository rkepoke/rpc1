package xuexi.huo.client;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 模拟dubbo的Transport层，建立网络通信的
 */
public class Transport {
    private String host;
    private Integer port;

    public Transport(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 发现把创建Socket的代码放在sendRequest里面会让代码臃肿，而且不符合功能单一职责
     * 所以抽出来了
     * 发现当把Socket socket=null;抽出来后，会报返回错误，假聊一个finally就好了
     * @return
     */
    public Socket getSocket(){
        Socket socket=null;
        System.out.println("准备创建远程连接："+host+":"+port);
        try {
            socket=new Socket(host,port);
            return socket;
        } catch (IOException e) {
            throw new RuntimeException("创建远程连接失败"+host+":"+ port +"。请重试。");
        }finally {
            return socket;
        }
    }

    /**
     * 忘了传输对象用什么io流了，百度之后发现用ObjectOutputStream，ObjectInputStream其实里面包装的是getOutputStream，getInputStream
     * 这也是装饰着模式的应用了，减轻了开发人员的负担
     * @param consumerRequest
     * @return
     */
    public Object sendRequest(ConsumerRequest consumerRequest)throws IOException{
        Socket socket=getSocket();
        ObjectOutputStream outputStream=null;
        ObjectInputStream inputStream=null;
        try {
            outputStream= new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(consumerRequest);
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
            try {
                Object result = inputStream.readObject();
                return result;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("发起远程调用异常",e);
            }
        } catch (IOException e) {
            throw new RuntimeException("发起远程调用异常",e);
        }finally {
//            OutputStream.close();因为ObjectOutputStream  ObjectInputStream都是在方法块内部调用，没办法在finally中关闭
            //显示Unhandled exception: java.io.IOException   在方法上抛出异常即可
            outputStream.close();
            inputStream.close();
            socket.close();
        }
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
