

package com.nfcmeeting.nfcmeeting.mvp.contract;

import android.support.annotation.NonNull;

import com.nfcmeeting.nfcmeeting.dao.AuthUser;
import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseContract;

import java.util.List;


public interface IMainContract {

    interface View extends IBaseContract.View{
        void restartApp();
    }

    interface Presenter extends IBaseContract.Presenter<IMainContract.View>{
        void logout();
    }

}
