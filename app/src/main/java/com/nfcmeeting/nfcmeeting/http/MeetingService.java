package com.nfcmeeting.nfcmeeting.http;

import android.support.annotation.NonNull;
import com.nfcmeeting.nfcmeeting.model.PageInfo;
import com.nfcmeeting.nfcmeeting.model.User;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hguang_gj@neusoft.com on 2019/3/20 17:44
 */
public interface MeetingService {


    @POST("meeting/getFinishedMeeting")
    Observable<Response<User>> getFinishedMeeting(
                    @NonNull @Body PageInfo page
            );

    @POST("meeting/getToAttendMeeting")
    Observable<Response<User>> getToAttendMeeting(
            @NonNull @Body PageInfo page
    );
}
