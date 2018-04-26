package test.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.*;
import org.apache.thrift.transport.*;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;

/*
服务器（非阻塞多线程）
 */
@WebServlet("/thrift")
public class ThriftServerProxy{

    private static final long serialVersionUID = 2L;
    private static TServer _SERVER = null;
    private static int SERVER_PORT = 8100;

    @PostConstruct
    public void start() throws  Exception{
        Thread serverThread = new Thread(){
            public void run(){
                try {

                    // 处理器
                    TProcessor tprocessor = new ThriftService.Processor<ThriftService.Iface>(new ThriftServiceImpl());
                    // 传输通道 - 非阻塞方式
                    TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);
                    TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
                    tArgs.processor(tprocessor);

                    // 使用高密度二进制协议
                    tArgs.protocolFactory(new TCompactProtocol.Factory());
                    //tArgs.transportFactory(new TFramedTransport.Factory()); 二进制协议
                    TServer server = new TNonblockingServer(tArgs);

                    System.out.println("server start...");
                    server.serve(); // 启动服务
                }
                catch (Exception e)
                {
                    //// TODO: 2018/4/23 日志 
                }
            }
        };
        serverThread.start();

    }
    public void stop() throws Exception{
        _SERVER.stop();
    }
}
