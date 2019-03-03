package com.nfcmeeting.nfcmeeting.http.common;

import okhttp3.HttpUrl;

public class HttpUtil {
    public  static String getFileName(HttpUrl url){
        int index = url.pathSegments().size();
        return  url.pathSegments().get(index-1);
    }
}
