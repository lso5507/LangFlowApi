package com.example.demo.service;

import com.example.demo.api.LangFlowApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    public String sendAi(String param) throws JsonProcessingException {
        return LangFlowApi.sendApi(param);
    }
}
