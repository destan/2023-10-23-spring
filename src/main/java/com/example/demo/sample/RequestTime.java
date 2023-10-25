package com.example.demo.sample;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;

@Slf4j
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestTime {

    @Getter
    private final Instant time;

    public RequestTime() {
        this.time = Instant.now();
        log.debug("RequestTime object created at {}", time.toEpochMilli());
    }
}
