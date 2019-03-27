package com.nfcmeeting.nfcmeeting.http.common;

import java.util.Map;

/**
 * Created_Time by hguang_gj@neusoft.com on 2018/12/30 20:52
 */
public class HttpResult {
    private String code;
    private String message;
    private Map data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Object, Object> getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
