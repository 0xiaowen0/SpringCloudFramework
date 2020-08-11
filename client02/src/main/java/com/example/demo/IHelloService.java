package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("single-provider")
public interface IHelloService {

    @RequestMapping(value = "/hello")
    String hello();

    @RequestMapping(value = "/nice")
    String nice();

    @RequestMapping(value = "/test1")
    String test1();

    @RequestMapping(value = "/test2")
    String test2();

    @RequestMapping(value = "/test3")
    String test3();
}
