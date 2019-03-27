

package com.nfcmeeting.nfcmeeting.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nfcmeeting.nfcmeeting.AppData;


/**
 * Created_Time by ThirtyDegreesRay on 2017/10/19 17:31:18
 */

public class BrowserFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(AppData.INSTANCE.getAuthUser() != null){
            handleBrowserUri(this, getIntent().getData());
        } else {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.setData(getIntent().getData());
            startActivity(intent);
        }
        finish();
    }

    public static void handleBrowserUri(@NonNull Activity activity, @NonNull Uri uri){
        //handle shortcuts redirection

            //AppOpener.launchUrl(activity, uri);

    }

}
