package com.example.demo.sample;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile({"staging"})
public class SampleServiceStaging implements SampleInterface {
    @Override
    public String param() {
        return "staging parameter";
    }
}
