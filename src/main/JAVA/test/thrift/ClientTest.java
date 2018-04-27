package test.thrift;

public class ClientTest {
    public static void main(String[] args) throws Exception {

        ClientProxy client = new ClientProxy();
        client.start("test...");
    }
}