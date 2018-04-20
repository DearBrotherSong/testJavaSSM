package test02.thrift;


public class ThriftApplication {
    public static void main(String[] args) throws Exception {

        Service service = new Service();
        service.start();
    }
}
