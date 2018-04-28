package test.consul;

public class ConsulTest {
    public static void main(String[] args){
        long startTime=System.currentTimeMillis();

        //consul.Regist("testNode");
        //Map<String,Service> map = consul.getServices();
        //consul.setKVValue("","");
        //consul.getKVValuesByPreFix("KV");
        long startTime1=System.currentTimeMillis();
        ConsulProxy._consul.getKVValue("KVTest");
        long startTime2=System.currentTimeMillis();

        ConsulProxy._consul.getKVValuesByPreFix("KV");
        long endTime=System.currentTimeMillis();


        System.out.println("time1："+(startTime2-startTime1)+"    time2："+(endTime-startTime2)+"ms");
        return;
    }
}
