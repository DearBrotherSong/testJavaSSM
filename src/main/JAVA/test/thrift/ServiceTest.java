package test.thrift;

public class ServiceTest {
    public static void main(String[] args) throws Exception {

        ThriftServerProxy serverProxy = new ThriftServerProxy();
        serverProxy.start();
    }
}
