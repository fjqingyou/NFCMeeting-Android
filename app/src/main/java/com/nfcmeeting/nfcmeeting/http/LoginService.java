

package com.nfcmeeting.nfcmeeting.http;

import android.support.annotation.NonNull;

import com.nfcmeeting.nfcmeeting.http.common.HttpResult;
import com.nfcmeeting.nfcmeeting.http.model.AuthRequestModel;


import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created on 2017/8/1.
 *
 * @author ThirtyDegreesRay
 */

public interface LoginService {

//    @POST("authorizations")
//    @Headers("Accept: application/json")
//    Observable<Response<BasicToken>> authorizations(
//            @NonNull @Body AuthRequestModel authRequestModel
//    );
//
//    @POST("login/oauth/access_token")
//    @Headers("Accept: application/json")
//    Observable<Response<OauthToken>> getAccessToken(
//            @Query("client_id") String clientId,
//            @Query("client_secret") String clientSecret,
//            @Query("code") String code,
//            @Query("state") String state
//    );
/*
自动添加的header会产生空格，似乎不符合rfc标准，导致springboot报错
https://tools.ietf.org/html/rfc5234#appendix-B.1
https://stackoverflow.com/questions/40010715/android-retrofit-api-call-with-space-in-parameter
 */
    @POST("login")
    @Headers({"Accept: application/json","Content-Type: application/json;charset=UTF-8"})
    Observable<Response<HttpResult>> login(
            @NonNull @Body AuthRequestModel authRequestModel
    );


}
