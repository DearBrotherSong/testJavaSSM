package test02.thrift;

public class clientTest {
    public static void main(String[] args) throws Exception {

        ClientProxy client = new ClientProxy();
        client.start("test...");
    }
}