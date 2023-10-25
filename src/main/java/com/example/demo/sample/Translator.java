package com.example.demo.sample;

class Translator {

    String hello(String lang) {
        if (lang.startsWith("en")) {
            return "Hello";
        }
        return "Merhaba";
    }

}
