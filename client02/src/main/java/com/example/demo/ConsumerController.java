package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IHelloService helloService;

    private static final String applicationName = "single-provider";

    @RequestMapping(value = "feignRequest")
    public Object feignRequest(){
        String s = helloService.nice();
        return s;
    }

    @RequestMapping(value = "commonRequest")
    public Object commonRequest(){
        String url = "http://"+ applicationName +"/hello";
        String s = restTemplate.getForObject(url,String.class);
        return s;
    }


    @RequestMapping(value = "/test1")
    public String test1(){
        return helloService.test1();
    }
    @RequestMapping(value = "/test2")
    public String test2(){
        return helloService.test2();
    }
    @RequestMapping(value = "/test3")
    public String test3(){
        return helloService.test3();
    }
}
