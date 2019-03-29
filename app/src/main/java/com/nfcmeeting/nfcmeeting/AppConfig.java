

package com.nfcmeeting.nfcmeeting;



import java.util.Arrays;
import java.util.List;

/**
 * Created_Time on 2016/11/23.
 *
 * @author ThirtyDegreesRay
 */

public class AppConfig {

   // public final static String  NFCMEETING_BASE_URL = "http://nfcmeeting.com:1989/NFCMeeing/";
    public final static String  NFCMEETING_BASE_URL = "http://192.168.0.116:1989/NFCMeeing/";




    /**
     * This link are for OpenHub only. Please do not use this endpoint in your applications.
     * If you want to get trending repositories, you may stand up your own instance.
     * https://github.com/thedillonb/GitHub-Trending
     */
    public final static String OPENHUB_BASE_URL = "http://openhub.raysus.win:3000/";

    public final static int HTTP_TIME_OUT = 32 * 1000;

    public final static int HTTP_MAX_CACHE_SIZE = 32 * 1024 * 1024;

    public final static int IMAGE_MAX_CACHE_SIZE = 32 * 1024 * 1024;

    public final static int CACHE_MAX_AGE = 4 * 7 * 24 * 60 * 60;

    public final static String DB_NAME = "NFCMeeting.db";

    //public final static String BUGLY_APPID = BuildConfig.DEBUG ? BuildConfig.DEBUG_BUGLY_ID : BuildConfig.BUGLY_ID;



}
