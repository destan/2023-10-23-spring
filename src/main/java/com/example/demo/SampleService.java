package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
class SampleService {

    private final Translator translator;

    private final RequestTime requestTime;

    public SampleService(Translator translator, RequestTime requestTime) {
        this.translator = translator;
        this.requestTime = requestTime;
        log.info("SampleService created");
    }

    String hello(String name, String lang) {

        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
        log.trace("trace");

        final String message = translator.hello(lang) + " " + name;

        log.info("Request time for message '{}' is {}", message, requestTime.getTime());

        return message;
    }


}
