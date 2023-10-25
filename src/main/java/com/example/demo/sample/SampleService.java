package com.example.demo.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
class SampleService implements SampleInterface {

    private final Translator translator;

    private final RequestTime requestTime;

    private final String param;

    public SampleService(Translator translator, RequestTime requestTime, @Value("${sample-app.request-timeout}") Long param) {
        this.translator = translator;
        this.requestTime = requestTime;
        this.param = param.toString();
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

    @Override
    public String param() {
        return param;
    }
}
