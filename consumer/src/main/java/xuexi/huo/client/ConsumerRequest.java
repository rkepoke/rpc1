package xuexi.huo.client;

import java.io.Serializable;

/**
 * 这个类是用于封装 请求数据的
 */
public class ConsumerRequest implements Serializable{
    private String className;
    private String methodName;
   // private String args;参数可能有多个  用数组接受
    //因为调用时报错getArgs( ) in ConsumerRequest cannot be applied to (java.lang.Object[]) 应该将String转为OBject通用
//    private String[] args;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
