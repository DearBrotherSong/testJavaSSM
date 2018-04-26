package test.consul;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConsulProxy {
    public static Consul _consul;

    @PostConstruct
    public void init(){
        _consul = new Consul();
        System.out.println("consul start ...");
    }
}
