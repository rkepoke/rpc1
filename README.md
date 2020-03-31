# rpc1
version1
整个编写的思路是:

先编写服务消费方，针对接口编程，从上往下，通过反射技术来生成动态代理对象，在往下是由transport对象建立网络连接

在编写服务提供方，首先开启对指定端口的监听，来一个请求就创建一个线程去执行它，在每个线程里面是用接口的实现类
去提供服务，然后把处理好的结果返回。

编写顺序，由左边开始，从上往下，在到右边，从下往上。

编写的时候有参考到的资料：
dubbo的原理机制：
https://blog.csdn.net/paul_wei2008/article/details/19355681

Java的动态代理：
https://blog.csdn.net/yaomingyang/article/details/80981004
https://blog.csdn.net/yaomingyang/article/details/81040390

代理异常的解决方法：
https://blog.csdn.net/s3395719/article/details/76064217
https://www.cnblogs.com/libin6505/p/10705749.html

反射的类型类理解：
https://blog.csdn.net/zhawabcd/article/details/78289140

理解Java的动态代理：
https://blog.csdn.net/zhawabcd/article/details/78439520

了解动态代理的一片很好的文章：
https://www.cnblogs.com/softwarewebdesign/p/5926687.html

在理解之后反射问题得以解决：
https://blog.csdn.net/s3395719/article/details/76064217

正确的使用并书写泛型方法：
https://www.cnblogs.com/coprince/p/8603492.html


