

package com.nfcmeeting.nfcmeeting.mvp.contract;

import android.content.Intent;

import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseContract;
import com.nfcmeeting.nfcmeeting.mvp.model.BasicToken;


/**
 * Created_Time on 2017/7/12.
 *
 * @author ThirtyDegreesRay
 */

public interface ILoginContract {

    interface View extends IBaseContract.View{

        void onGetTokenSuccess(BasicToken basicToken);

        void onGetTokenError(String errorMsg);

        void onLoginComplete();

    }

    interface Presenter extends IBaseContract.Presenter<ILoginContract.View>{

        //void basicLogin(String userName, String password);

        void jsonLogin(String userName, String password);

        void getUserInfo(BasicToken basicToken);



    }

}
