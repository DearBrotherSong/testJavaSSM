package test02.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import test02.Consul.ConsulProxy;
import test02.thrift.ClientProxy;
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public String Test(String param) throws Exception {
        String result = "";
        /*
        //thriftTest

        ClientProxy clientProxy = new ClientProxy();
        result = clientProxy.start(param);
        */


        //ConsulTest
        long startTime1=System.currentTimeMillis();
        ConsulProxy._consul.getKVValue("KVTest");
        long startTime2=System.currentTimeMillis();

        ConsulProxy._consul.getKVValuesByPreFix("KV");
        long endTime=System.currentTimeMillis();

        result = "time1 ->"+(startTime2-startTime1)+" ms    time2 ->"+(endTime-startTime2)+" ms";


        return result;
    }
}