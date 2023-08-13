package dev.dfsanchezb.security_demo.controller;


import dev.dfsanchezb.security_demo.response_model.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public ResponseEntity<ResponseBody<String>> home() {
        log.info("Testing Security Demo Connection.");
        ResponseBody<String> body = new ResponseBody<>(0, "Success", "API is up and running.");
        return ResponseEntity.ok().body(body);
    }
}
