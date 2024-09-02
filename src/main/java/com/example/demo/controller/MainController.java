package com.example.demo.controller;

import com.example.demo.service.MainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MainService mainService;
    @GetMapping("/main")
    public String main(){
        return "main";
    }
    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<?> test(@RequestBody String param) throws JsonProcessingException {
        String resultStr = mainService.sendAi(param);
        return ResponseEntity.ok(resultStr);
    }
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public final ResponseEntity<?> handleAllException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }}
