package com.example.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class LangFlowApi {
    public static String sendApi(String question) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 URL
        String url = "http://localhost:7860/api/v1/run/9747b75f-9faa-443e-99cd-a3b6aede9056?stream=false";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> param = bindingParameter(question);

        // ObjectMapper를 사용하여 Map을 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(param);
        // HttpEntity에 JSON 문자열과 헤더를 설정
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // 응답 결과 출력
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Response: " + response.getBody());
            JSONObject resultObj = new JSONObject(response.getBody());
            //@TODO ㅋㅋㅋㅋㅋㅋㅋ 나중에 바꿔야함
            String resultStr = resultObj.getJSONArray("outputs").getJSONObject(0).getJSONArray("outputs").getJSONObject(0).getJSONArray("messages").getJSONObject(0).optString("message");
            return resultStr;
        } else {
            System.out.println("Failed to get response: " + response.getStatusCode());
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Map<String,Object> bindingParameter(String question) {
        // 요청 데이터를 위한 Map 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("input_value", question);
        requestBody.put("output_type", "text");
        requestBody.put("input_type", "chat");

        // 'tweaks' 필드에 해당하는 빈 Map 객체 생성 및 추가
        Map<String, Object> tweaks = new HashMap<>();
        tweaks.put("ChatInput-AcYRJ", new HashMap<>());
        tweaks.put("Prompt-pDAgO", new HashMap<>());
        tweaks.put("ChatOutput-3jjvm", new HashMap<>());
        tweaks.put("OllamaModel-zumDh", new HashMap<>());
        requestBody.put("tweaks", tweaks);
        return requestBody;
    }
}
