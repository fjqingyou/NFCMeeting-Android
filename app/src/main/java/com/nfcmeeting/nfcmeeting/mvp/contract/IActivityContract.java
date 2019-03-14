

package com.nfcmeeting.nfcmeeting.mvp.contract;

import android.support.annotation.NonNull;

import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseContract;
import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseListContract;
import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBasePagerContract;

public interface IActivityContract {

    interface View extends IBaseContract.View, IBasePagerContract.View, IBaseListContract.View {
        void showEvents(ArrayList<Event> events);
    }

    interface Presenter extends IBasePagerContract.Presenter<IActivityContract.View>{
        void loadEvents(boolean isReload, int page);
        ArrayList<ActivityRedirectionModel> getRedirectionList(@NonNull Event event);
    }

}
