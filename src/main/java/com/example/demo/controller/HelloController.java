package com.example.demo.controller;

import com.example.demo.domain.DemoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public DemoResponse hello () {
        return new DemoResponse("HelloWorld!");
    }

    @GetMapping("/myname")
    public DemoResponse sayMyName(@RequestParam("name") String name, @RequestParam("times") Integer times) {
        return new DemoResponse((name + "\n").repeat(times));
    }
}
