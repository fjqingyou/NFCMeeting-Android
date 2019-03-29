

package com.nfcmeeting.nfcmeeting.mvp.presenter;

import com.nfcmeeting.nfcmeeting.dao.DaoSession;
import com.nfcmeeting.nfcmeeting.mvp.contract.IRepoInfoContract;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;
import com.nfcmeeting.nfcmeeting.mvp.presenter.base.BasePagerPresenter;
import com.nfcmeeting.nfcmeeting.util.StringUtils;
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by ThirtyDegreesRay on 2017/8/11 11:34:31
 */

public class RepoInfoPresenter extends BasePagerPresenter<IRepoInfoContract.View>
        implements IRepoInfoContract.Presenter{

    @AutoAccess
    Repository repository;
    @AutoAccess String curBranch = "";
    private String readmeSource;

    @Inject
    public RepoInfoPresenter(DaoSession daoSession) {
        super(daoSession);
    }

    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
    }

    @Override
    protected void loadData() {
        mView.showRepoInfo(repository);
        if(readmeSource == null){
            loadReadMe();
        }
    }

    @Override
    public void loadReadMe() {


//        if(!StringUtils.isBlank(readmeSource)){
//            mView.showReadMe(readmeSource, baseUrl);
//            return;
//        }

        mView.showReadMeLoader();
        mView.showReadMe(repository.getContent());
    }

    public Repository getRepository() {
        return repository;
    }

    /**
     * check if the string size is too large to save
     */
    private void checkReadmeSourceSize(){
        if(readmeSource != null && readmeSource.getBytes().length > 128 * 1024){
            readmeSource = null;
        }
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void setCurBranch(String curBranch) {
        this.curBranch = curBranch;
        readmeSource = null;
    }
}
