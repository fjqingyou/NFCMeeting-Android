

package com.nfcmeeting.nfcmeeting.mvp.contract;



import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseContract;
import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBaseListContract;
import com.nfcmeeting.nfcmeeting.mvp.contract.base.IBasePagerContract;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;
import com.nfcmeeting.nfcmeeting.mvp.model.filter.RepositoriesFilter;

import java.util.ArrayList;



public interface IRepositoriesContract {

    interface View extends IBaseContract.View, IBasePagerContract.View, IBaseListContract.View {

        void showRepositories(ArrayList<Repository> repositoryList);

    }

    interface Presenter extends IBasePagerContract.Presenter<IRepositoriesContract.View> {
        void loadRepositories(boolean isReLoad, int page);
        void loadRepositories(RepositoriesFilter filter);
    }

}
