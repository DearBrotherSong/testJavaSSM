package test02.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.*;

import java.util.HashMap;

/*
客户端
 */
public class Client {
    public static final  String SERVER_IP="127.0.0.1";
    public static final int SERVER_PORT=8100;
    public static final int TIME_OUT=10000;

    public void start(String param) throws Exception{
        System.out.println("client start...");

        TSocket socket = new TSocket(SERVER_IP,SERVER_PORT,TIME_OUT);
        TTransport transport=new TFramedTransport(socket);
        //使用二进制协议（与服务器保持一致）
        TProtocol protocol = new TBinaryProtocol(transport);
        ThriftService.Client client = new ThriftService.Client(protocol);
        transport.open();

        System.out.print("result:"+client.ThriftApi(param));
        transport.close();
    }

}
