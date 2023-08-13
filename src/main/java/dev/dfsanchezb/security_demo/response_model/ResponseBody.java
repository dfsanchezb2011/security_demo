package dev.dfsanchezb.security_demo.response_model;

import lombok.Data;

@Data
public class ResponseBody <T> {

    private Integer apiCode;
    private String apiMessage;
    private T apiData;

    public ResponseBody(Integer apiCode, String apiMessage, T apiData) {
        this.apiCode = apiCode;
        this.apiMessage = apiMessage;
        this.apiData = apiData;
    }
}
