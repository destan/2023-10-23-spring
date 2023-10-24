package com.example.demo;

class Translator {

    String hello(String lang) {
        if (lang.startsWith("en")) {
            return "Hello";
        }
        return "Merhaba";
    }

}
