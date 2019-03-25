package com.nfcmeeting.nfcmeeting.http;

import android.support.annotation.NonNull;
import com.nfcmeeting.nfcmeeting.model.PageInfo;
import com.nfcmeeting.nfcmeeting.model.User;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

import java.util.ArrayList;

/**
 * Created by hguang_gj@neusoft.com on 2019/3/20 17:44
 */
public interface MeetingService {


    @POST("meeting/getFinishedMeeting")
    Observable<Response<ArrayList<Repository>>> getFinishedMeeting(
            @Header("forceNetWork") boolean forceNetWork,
                    @NonNull @Body PageInfo page
            );

    @POST("meeting/getToAttendMeeting")
    Observable<Response<ArrayList<Repository>>> getToAttendMeeting(
            @Header("forceNetWork") boolean forceNetWork,
            @NonNull @Body PageInfo page
    );

    @POST("meeting/getAllMeeting")
    Observable<Response<ArrayList<Repository>>> getAllMeeting(
            @Header("forceNetWork") boolean forceNetWork,
            @NonNull @Body PageInfo page
    );
}
