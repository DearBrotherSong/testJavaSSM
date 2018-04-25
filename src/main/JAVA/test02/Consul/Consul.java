package test02.Consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Service;
import com.ecwid.consul.v1.kv.model.GetValue;
import org.springframework.stereotype.Component;
import test02.Infrastructure.CommonTools.CommonTool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Consul {

    private static ConsulClient _consulClient;

    public Consul(){

        ConcurrentHashMap properties = CommonTool.Tools.GetPropertiesMap("consul.properties");
        String host = properties.get("host").toString();
        int port = Integer.parseInt(properties.get("port").toString());

        ConsulRawClient client = new ConsulRawClient(host,port);
        _consulClient = new ConsulClient(client);

    }

    public Map<String,Service> getServices(){
        Map<String,Service> serviceMap = _consulClient.getAgentServices().getValue();

        return serviceMap;
    }

    public void registService(String name){
        NewService newService = new NewService();
        newService.setName(name);
        newService.setAddress("192.168.6.24");
        newService.setPort(8500);

        _consulClient.agentServiceRegister(newService);
    }
    public void setKVValue(String key,String Value){

        _consulClient.setKVValue("KVTest","KVValue");

        /*
        PutParams putParams = new PutParams();
        putParams.setAcquireSession("testAcquireSession");
        putParams.setCas((long)110);
        putParams.setCas((long)119);
        putParams.setFlags((long)120);
        putParams.setReleaseSession("testReleaseSession");
        _consulClient.setKVValue("KVPutTest","putValue",putParams);

        QueryParams queryParams = new QueryParams(ConsistencyMode.DEFAULT);
        _consulClient.setKVValue("KVParamTest","KVParamValue",queryParams);
        */
    }
    public List<GetValue> getKVValuesByPreFix(String keyPrefix){
        List<GetValue> list = _consulClient.getKVValues(keyPrefix).getValue();
        return list;
    }
    public String getKVValue(String key){
        GetValue value = _consulClient.getKVValue(key).getValue();
        String trueValue = value.getValue();
        String result = CommonTool.Tools.DecodeBase64(trueValue);

        return result;
    }
}
