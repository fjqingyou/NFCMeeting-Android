

package com.nfcmeeting.nfcmeeting.inject.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.nfcmeeting.nfcmeeting.inject.ActivityScope;
import com.nfcmeeting.nfcmeeting.ui.activity.base.BaseActivity;


import dagger.Module;
import dagger.Provides;

/**
 * ActivityModule
 * Created_Time by ThirtyDegreesRay on 2016/8/30 14:53
 */
@Module
public class ActivityModule {

    private BaseActivity mActivity;

    public ActivityModule(BaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public BaseActivity provideActivity(){
        return mActivity;
    }

    @Provides
    @ActivityScope
    public Context provideContext(){
        return mActivity;
    }

    @Provides
    @ActivityScope
    public FragmentManager provideFragmentManager(){
        return mActivity.getSupportFragmentManager();
    }

}
