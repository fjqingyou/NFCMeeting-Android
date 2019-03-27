

package com.nfcmeeting.nfcmeeting.mvp.presenter;

import android.support.annotation.NonNull;

import com.nfcmeeting.nfcmeeting.AppData;
import com.nfcmeeting.nfcmeeting.dao.AuthUser;
import com.nfcmeeting.nfcmeeting.dao.AuthUserDao;
import com.nfcmeeting.nfcmeeting.dao.DaoSession;
import com.nfcmeeting.nfcmeeting.model.User;
import com.nfcmeeting.nfcmeeting.mvp.contract.IMainContract;
import com.nfcmeeting.nfcmeeting.mvp.presenter.base.BasePresenter;
import com.nfcmeeting.nfcmeeting.util.PrefUtils;


import java.util.List;

import javax.inject.Inject;

/**
 * Created_Time on 2017/7/18.
 *
 * @author ThirtyDegreesRay
 */

public class MainPresenter extends BasePresenter<IMainContract.View>
        implements IMainContract.Presenter{

    @Inject
    public MainPresenter(DaoSession daoSession) {
        super(daoSession);
    }

    @Override
    public void logout() {
        daoSession.getAuthUserDao().delete(AppData.INSTANCE.getAuthUser());
        AppData.INSTANCE.setAuthUser(null);
        mView.restartApp();
    }

}
