package test02.thrift;

import org.apache.thrift.TException;

import java.util.Map;

public class ThriftServiceImpl implements ThriftService.Iface {
    @Override
    public String ThriftApi(String data) throws TException {
        return "server get String "+data;
    }
}
