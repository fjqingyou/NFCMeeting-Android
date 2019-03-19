

package com.nfcmeeting.nfcmeeting.mvp.presenter;


import android.support.annotation.NonNull;

import com.nfcmeeting.nfcmeeting.AppData;
import com.nfcmeeting.nfcmeeting.dao.AuthUser;
import com.nfcmeeting.nfcmeeting.dao.AuthUserDao;
import com.nfcmeeting.nfcmeeting.dao.DaoSession;
import com.nfcmeeting.nfcmeeting.http.common.Headers;
import com.nfcmeeting.nfcmeeting.http.common.HttpResult;
import com.nfcmeeting.nfcmeeting.http.core.HttpObserver;
import com.nfcmeeting.nfcmeeting.http.core.HttpResponse;
import com.nfcmeeting.nfcmeeting.http.core.HttpSubscriber;
import com.nfcmeeting.nfcmeeting.http.model.AuthRequestModel;
import com.nfcmeeting.nfcmeeting.model.User;
import com.nfcmeeting.nfcmeeting.mvp.contract.ILoginContract;
import com.nfcmeeting.nfcmeeting.mvp.model.BasicToken;
import com.nfcmeeting.nfcmeeting.mvp.presenter.base.BasePresenter;
import com.nfcmeeting.nfcmeeting.util.StringUtils;
import com.orhanobut.logger.Logger;


import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import okhttp3.Credentials;
import retrofit2.Response;
import rx.Observable;

/**
 * Created on 2017/7/12.
 *
 * @author ThirtyDegreesRay
 */

public class LoginPresenter extends BasePresenter<ILoginContract.View>
        implements ILoginContract.Presenter {

    @Inject
    public LoginPresenter(DaoSession daoSession) {
        super(daoSession);
    }


    @Override
    public void jsonLogin(String userName, String password) {
        Logger.t("jsonLogin").i("jsonLogin:" + userName + " " + password);

        AuthRequestModel authRequestModel = new AuthRequestModel(userName, password);
        Observable<Response<HttpResult>> observable =
                getLoginService().login(authRequestModel);
        HttpSubscriber<HttpResult> subscriber =
                new HttpSubscriber<>(new HttpObserver<HttpResult>() {
                    @Override
                    public void onError(Throwable error) {
                        Logger.t("jsonLogin").i("jsonLogin failed:" + error.getStackTrace());
                        error.printStackTrace();
                        mView.onGetTokenError(getErrorTip(error));

                    }

                    @Override
                    public void onSuccess(HttpResponse<HttpResult> response) {
                        BasicToken basicToken = new BasicToken();
                        basicToken.setToken(response.getOriResponse().headers().get(Headers.SET_COOKIE));

                        System.out.println(response.getOriResponse().raw().headers());

                        Logger.t("jsonLogin").i("jsonLogin success:" + basicToken + " " + basicToken.getToken());

                        if (basicToken != null) {
                            mView.onGetTokenSuccess(basicToken);
                        } else {
                            mView.onGetTokenError(response.getOriResponse().message());
                        }
                    }
                });
        generalRxHttpExecute(observable, subscriber);

    }

    @Override
    public void getUserInfo(final BasicToken basicToken) {
        HttpSubscriber<User> subscriber = new HttpSubscriber<>(
                new HttpObserver<User>() {
                    @Override
                    public void onError(Throwable error) {
                        Logger.t("getUserInfo error").e(getErrorTip(error),error.toString());
                        error.printStackTrace();
                        mView.dismissProgressDialog();
                        mView.showErrorToast(getErrorTip(error));
                    }

                    @Override
                    public void onSuccess(HttpResponse<User> response) {
//                        mView.dismissProgressDialog();
                        saveAuthUser(basicToken, response.body());
                        mView.onLoginComplete();
                    }
                }
        );
        Observable<Response<User>> observable = getUserService(basicToken.getToken()).
                getPersonInfo(true);
        generalRxHttpExecute(observable, subscriber);
        mView.showProgressDialog(getLoadTip());

    }

    private void saveAuthUser(BasicToken basicToken, User userInfo) {
        Logger.t("saveAuthUser").i("userInfo:" + userInfo + " " + basicToken);

        String updateSql = "UPDATE " + daoSession.getAuthUserDao().getTablename()
                + " SET " + AuthUserDao.Properties.Selected.columnName + " = 0";
        daoSession.getAuthUserDao().getDatabase().execSQL(updateSql);

        String deleteExistsSql = "DELETE FROM " + daoSession.getAuthUserDao().getTablename()
                + " WHERE " + AuthUserDao.Properties.LoginId.columnName
                + " = '" + userInfo.getUserId() + "'";
        daoSession.getAuthUserDao().getDatabase().execSQL(deleteExistsSql);

        AuthUser authUser = new AuthUser();
        //String scope = StringUtils.listToString(basicToken.getScopes(), ",");
        Date date = new Date();
        authUser.setAccessToken(basicToken.getToken());
        authUser.setScope("all");
        authUser.setAuthTime(date);
        authUser.setExpireIn(360 * 24 * 60 * 60);
        authUser.setSelected(true);
        authUser.setLoginId(userInfo.getUserId().toString());
        authUser.setName(userInfo.getName());
        authUser.setAvatar(userInfo.getAvatar());
        daoSession.getAuthUserDao().insert(authUser);

        AppData.INSTANCE.setAuthUser(authUser);
        AppData.INSTANCE.setLoggedUser(userInfo);
    }


}
