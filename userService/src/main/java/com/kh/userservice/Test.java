package com.kh.userservice;

import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test {

    static void main() {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        fieldErrors.put("x", "123");
        fieldErrors.put("y", "456");
        System.out.println(new ObjectMapper().writeValueAsString(fieldErrors));
    }
}
