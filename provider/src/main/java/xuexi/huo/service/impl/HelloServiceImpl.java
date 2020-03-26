package xuexi.huo.service.impl;

import xuexi.huo.service.HelloService;

/**
 * 接口的实现类，放在服务端供远程调用
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello(String name) {
        return name+"你好啊！";
    }
}
