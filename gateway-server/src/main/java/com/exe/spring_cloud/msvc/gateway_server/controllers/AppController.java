package com.exe.spring_cloud.msvc.gateway_server.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/authorize")
    public Map<String,String> authorize(@RequestParam String code){

        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        return map;
    }

    @PostMapping("/logout")
    public Map<String,String> logout(){

        return Collections.singletonMap("logout","Ok");
    }

}
