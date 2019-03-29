package com.nfcmeeting.nfcmeeting.http;

import android.support.annotation.NonNull;
import com.nfcmeeting.nfcmeeting.model.PageInfo;
import com.nfcmeeting.nfcmeeting.model.User;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

import java.util.ArrayList;

/**
 * Created_Time by hguang_gj@neusoft.com on 2019/3/20 17:44
 */
public interface MeetingService {


    @POST("meeting/getStaredMeeting")
    Observable<Response<ArrayList<Repository>>> getStaredMeeting(
            @Header("forceNetWork") boolean forceNetWork,
                    @NonNull @Body PageInfo page
            );

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

    @NonNull @PUT("meeting/starMeeting/{owner}/{repo}")
    Observable<Response<ResponseBody>> starMeeting(
            @Path("owner") String owner,
            @Path("meeting") String meeting
    );

    @NonNull @DELETE("meeting/unstarMeeting/{owner}/{repo}")
    Observable<Response<ResponseBody>> unstarMeeting(
            @Path("owner") String owner,
            @Path("meeting") String meeting

    );

    @NonNull @GET("meeting/getMeetingInfo/{owner}/{repo}")
    Observable<Response<Repository>> getMeetingInfo(
            @Header("forceNetWork") boolean forceNetWork,

            @Path("meeting") String meeting
    );

    @NonNull @GET("meeting/checkMeetingStarred/{owner}/{repo}")
    Observable<Response<ResponseBody>> checkMeetingStarred(
            @Path("owner") String owner,
            @Path("repo") String repo
    );

}
