package test02.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
服务器（非阻塞多线程）
 */
public class Service{

    private static TServer _SERVER = null;
    private static int SERVER_PORT = 8100;
    private String dataService;

    public Service(){

    }

    public void start() throws  Exception{
        Thread serverThread = new Thread();
        serverThread.start();
        System.out.println("server start...");
        //处理器
        TProcessor tProcessor = new ThriftService.Processor<ThriftService.Iface>(new ThriftServiceImpl());

        //非阻塞传输通道
        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);
        //多线程服务端使用 非阻塞IO
        TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
        //加载处理器
        tArgs.processor(tProcessor);
        //传输协议操作类
        //tArgs.transportFactory(new TTransportFactory());
        //传输协议（二进制协议）
        tArgs.protocolFactory(new TBinaryProtocol.Factory());

        _SERVER = new TThreadedSelectorServer(tArgs);

        _SERVER.serve();//开启服务
        System.out.println(_SERVER.getEventHandler());

    }
    public void stop() throws Exception{
        _SERVER.stop();
    }

}
