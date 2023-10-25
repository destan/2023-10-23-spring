package com.example.demo.sample;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("demo")
class SampleController {

    private final SampleService sampleService;

    private final SampleInterface sampleInterface;

    private final List<SampleUserDto> users = Collections.synchronizedList(new ArrayList<>());

    @ResponseBody
    @GetMapping("param")
    String param() {
        return sampleInterface.param();
    }

    @ResponseBody
    @GetMapping("hello")
    String hello(/*@RequestHeader Map<String, String> headers*/ @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
                                                                @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
                                                                @RequestParam String name) {


        return sampleService.hello(name, language);
    }

    @ResponseBody
    @GetMapping("users/{userId}")
    String detail(@PathVariable long userId) {


        return "Hello";
    }

    @ResponseBody
    @GetMapping("users") //?page=2
    String list(@RequestParam Integer page) {


        return "Hello";
    }

    @ResponseBody
    @PostMapping("users")
    String create(@RequestBody SampleUserDto user) {

        users.add(user);

        return "Hello";
    }

    @ResponseBody
    @PostMapping("hello")
    String helloPost(HttpServletRequest request) {
        return "Hello";
    }

}
