package com.example.demo;


record SampleUserDto(String username, String email) {

    String uiValue() {
        return username + "(" + email + ")";
    }

}
