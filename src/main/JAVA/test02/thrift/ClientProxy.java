package test02.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.*;

import java.util.HashMap;

/*
客户端
 */
public class ClientProxy {
    public static final  String SERVER_IP="127.0.0.1";
    public static final int SERVER_PORT=8100;
    public static final int TIME_OUT=10000;

    public String start(String param) throws Exception{
        System.out.println("client start...");

        TTransport transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT,TIME_OUT));
        // 协议要和服务端一致
        // 使用高密度二进制协议
        TProtocol protocol = new TCompactProtocol(transport);

        // 使用二进制协议
        //TProtocol protocol = new TBinaryProtocol(transport);
        ThriftService.Client client = new ThriftService.Client(protocol);
        transport.open();
        String result = client.ThriftApi(param);
        System.out.println("result -> " + result);
        // 关闭资源
        transport.close();
        return  result;
    }

}
