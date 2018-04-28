package test.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;

/*
服务器（非阻塞多线程）
 */
@Component
public class ThriftServerProxy {

    private static final long serialVersionUID = 2L;
    private TServer _server = null;
    private int _serverPort = 8100;

    @PostConstruct
    public void start() throws  Exception {
        Thread serverThread = new Thread() {
            public void run() {
                try {

                    // 处理器
                    TProcessor tprocessor = new ThriftService.Processor<ThriftService.Iface>(new ThriftServiceImpl());
                    // 传输通道 - 非阻塞方式
                    TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(_serverPort);
                    TNonblockingServer.Args thriftArgs = new TNonblockingServer.Args(serverTransport);
                    thriftArgs.processor(tprocessor);

                    // 使用高密度二进制协议
                    thriftArgs.protocolFactory(new TCompactProtocol.Factory());
                    //thriftArgs.transportFactory(new TFramedTransport.Factory()); 二进制协议
                    _server = new TNonblockingServer(thriftArgs);

                    System.out.println("server start...");
                    _server.serve(); // 启动服务
                } catch (Exception e) {
                    //// TODO: 2018/4/23 日志 
                }
            }
        };
        serverThread.start();

    }

    public void stop() throws Exception {
        _server.stop();
    }
}
