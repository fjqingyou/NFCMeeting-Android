

package com.nfcmeeting.nfcmeeting.inject.module;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;


import com.nfcmeeting.nfcmeeting.AppApplication;
import com.nfcmeeting.nfcmeeting.AppConfig;
import com.nfcmeeting.nfcmeeting.dao.DBOpenHelper;
import com.nfcmeeting.nfcmeeting.dao.DaoMaster;
import com.nfcmeeting.nfcmeeting.dao.DaoSession;




import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * AppModule
 */
@Module
public class AppModule {

    private AppApplication application;

    public AppModule(AppApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public AppApplication provideApplication() {
        return application;
    }

    @NonNull
    @Provides
    @Singleton
    public DaoSession provideDaoSession() {
        DBOpenHelper helper = new DBOpenHelper(application, AppConfig.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }


}
