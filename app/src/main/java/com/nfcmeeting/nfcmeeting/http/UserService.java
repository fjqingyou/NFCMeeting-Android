

package com.nfcmeeting.nfcmeeting.http;

import android.support.annotation.NonNull;


import com.nfcmeeting.nfcmeeting.model.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/8/1.
 *
 * @author ThirtyDegreesRay
 */

public interface UserService {

    @NonNull
    @GET("user")
    Observable<Response<User>> getPersonInfo(
            @Header("forceNetWork") boolean forceNetWork
    );


}
