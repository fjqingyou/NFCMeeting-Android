

package com.nfcmeeting.nfcmeeting.mvp.contract;

import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseContract;
import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBasePagerContract;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;




public interface IRepoInfoContract {

    interface View extends IBaseContract.View, IBasePagerContract.View{
        void showRepoInfo(Repository repository);
        void showReadMe(String content);
        void showReadMeLoader();
        void showNoReadMe();
    }

    interface Presenter extends IBasePagerContract.Presenter<IRepoInfoContract.View>{
        void loadReadMe();
    }

}
