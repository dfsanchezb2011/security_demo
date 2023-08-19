package dev.dfsanchezb.security_demo.response_model;

import com.google.gson.Gson;
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

    public String getBodyAsJsonString () {
        return new Gson().toJson(this);
    }
}
