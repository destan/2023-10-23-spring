package com.example.demo.sample;


record SampleUserDto(String username, String email) {

    String uiValue() {
        return username + "(" + email + ")";
    }

}
