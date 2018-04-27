package test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test.consul.ConsulProxy;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public String test(String param) throws Exception {
        String result = "";
        /*
        //thriftTest

        ClientProxy clientProxy = new ClientProxy();
        result = clientProxy.start(param);
        */


        //ConsulTest
        long startTime1 = System.currentTimeMillis();
        String kvValue = ConsulProxy._consul.getKVValue("KVTest");
        long startTime2 = System.currentTimeMillis();

        ConsulProxy._consul.getKVValuesByPreFix("KV");
        long endTime = System.currentTimeMillis();

        result = kvValue + "   time1 ->" + (startTime2 - startTime1) + " ms    time2 ->" + (endTime - startTime2) + " ms";

        return result;
    }
}
