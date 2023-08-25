package dev.dfsanchezb.security_demo.utils;

import com.google.gson.Gson;
import dev.dfsanchezb.security_demo.response_model.ResponseBody;

public class JsonUtils {

    private JsonUtils() {
    }

    public static String getBodyAsJsonString(ResponseBody<String> body) {
        return new Gson().toJson(body);
    }
}
