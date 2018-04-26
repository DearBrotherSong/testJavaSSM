package test.thrift;

public class serviceTest {
    public static void main(String[] args) throws Exception {

        ThriftServerProxy serverProxy = new ThriftServerProxy();
        serverProxy.start();
    }
}
