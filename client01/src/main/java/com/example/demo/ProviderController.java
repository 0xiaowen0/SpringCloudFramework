package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RefreshScope
@RestController
@DefaultProperties(defaultFallback = "defaultFail")
public class ProviderController {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    Environment environment;

    @Value("${foo}")
    private String foo;


    @HystrixCommand(fallbackMethod = "fail1")
    @RequestMapping(value = "/hello")
    public String hello(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info(s);
        }
        return "hello spring cloud!"+foo;
    }

    @HystrixCommand(fallbackMethod = "fail1")
    @RequestMapping(value = "/nice")
    public String nice(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info("gogogo" + s);
        }
        System.out.println("Inside MyRestController::backend...");
        String serverPort = environment.getProperty("local.server.port");
        System.out.println("Port : " + serverPort);
        return "Hello form Backend!!! " + " Host : localhost " + " :: Port : " + serverPort;
    }

    @HystrixCommand(fallbackMethod = "fail1")
    @GetMapping("/test1")
    public String test1() {
        throw new RuntimeException();
    }

    private String fail1() {
        System.out.println("fail1");
        return "fail1";
    }

    @HystrixCommand(fallbackMethod = "fail2")
    @GetMapping("/test2")
    public String test2() {
        throw new RuntimeException();
    }

    @HystrixCommand(fallbackMethod = "fail3")
    private String fail2() {
        System.out.println("fail2");
        throw new RuntimeException();
    }

    @HystrixCommand
    private String fail3() {
        System.out.println("fail3");
        throw new RuntimeException();
    }

    private String defaultFail() {
        System.out.println("default fail");
        return "default fail";
    }

}
