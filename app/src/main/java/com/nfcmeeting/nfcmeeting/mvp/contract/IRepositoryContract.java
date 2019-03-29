

package com.nfcmeeting.nfcmeeting.mvp.contract;



import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseContract;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;

import java.util.ArrayList;

/**
 * Created by ThirtyDegreesRay on 2017/8/9 21:40:25
 */

public interface IRepositoryContract {

    interface View extends IBaseContract.View{
        void showRepo(Repository repo);
        void invalidateOptionsMenu();
    }

    interface Presenter extends IBaseContract.Presenter<IRepositoryContract.View>{
        void starRepo(boolean star);

    }

}
