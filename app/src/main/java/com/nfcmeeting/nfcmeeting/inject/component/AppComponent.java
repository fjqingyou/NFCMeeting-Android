

package com.nfcmeeting.nfcmeeting.inject.component;

import com.nfcmeeting.nfcmeeting.AppApplication;
import com.nfcmeeting.nfcmeeting.dao.DaoSession;
import com.nfcmeeting.nfcmeeting.inject.module.AppModule;



import javax.inject.Singleton;

import dagger.Component;

/**
 * AppComponent
 * Created_Time by ThirtyDegreesRay on 2016/8/30 14:08
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    /**
     * 获取AppApplication
     * @return
     */
    AppApplication getApplication();

    /**
     * 获取数据库Dao
     * @return
     */
    DaoSession getDaoSession();

}
