package test02.thrift;

public class serviceTest {
    public static void main(String[] args) throws Exception {

        ThriftServerProxy serverProxy = new ThriftServerProxy();
        serverProxy.start();
    }
}
