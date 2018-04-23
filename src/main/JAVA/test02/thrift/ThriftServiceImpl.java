package test02.thrift;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.jar.Attributes;

@Component
public class ThriftServiceImpl implements ThriftService.Iface {
    @Override
    public String ThriftApi(String data) throws TException {
        System.out.println("service recieve ->"+data);
        return "server get String "+data;
    }
}