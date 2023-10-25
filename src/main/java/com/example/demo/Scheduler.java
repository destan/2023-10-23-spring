package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class Scheduler {

    public Scheduler() {
        log.info("init");
    }

    @Scheduled(cron = "*/2 * * * * *")
    void printTime() {
        log.info("Run at {}", Instant.now());
    }

}
