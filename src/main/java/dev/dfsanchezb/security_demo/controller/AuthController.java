package dev.dfsanchezb.security_demo.controller;

import dev.dfsanchezb.security_demo.response_model.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping
    public ResponseEntity<ResponseBody<String>> auth() {
        log.info("Testing Authorized Connection.");
        ResponseBody<String> body = new ResponseBody<>(0, "Success", "Connection authenticated");
        return ResponseEntity.ok().body(body);
    }
}
