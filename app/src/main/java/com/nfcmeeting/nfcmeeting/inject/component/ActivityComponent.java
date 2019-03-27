

package com.nfcmeeting.nfcmeeting.inject.component;

import com.nfcmeeting.nfcmeeting.inject.ActivityScope;
import com.nfcmeeting.nfcmeeting.inject.module.ActivityModule;
import com.nfcmeeting.nfcmeeting.ui.activity.LoginActivity;
import com.nfcmeeting.nfcmeeting.ui.activity.MainActivity;
import com.nfcmeeting.nfcmeeting.ui.activity.SplashActivity;


import dagger.Component;

/**
 * ActivityComponent
 * Created_Time by ThirtyDegreesRay on 2016/8/30 14:56
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(SplashActivity activity);
    void inject(LoginActivity activity);
    void inject(MainActivity activity);
}
