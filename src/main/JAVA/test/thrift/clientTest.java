package test.thrift;

public class clientTest {
    public static void main(String[] args) throws Exception {

        ClientProxy client = new ClientProxy();
        client.start("test...");
    }
}