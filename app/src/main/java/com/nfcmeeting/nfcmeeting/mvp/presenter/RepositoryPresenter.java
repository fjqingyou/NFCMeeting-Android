

package com.nfcmeeting.nfcmeeting.mvp.presenter;

import com.nfcmeeting.nfcmeeting.R;
import com.nfcmeeting.nfcmeeting.dao.DaoSession;
import com.nfcmeeting.nfcmeeting.http.core.HttpObserver;
import com.nfcmeeting.nfcmeeting.http.core.HttpResponse;
import com.nfcmeeting.nfcmeeting.mvp.contract.IRepositoryContract;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;
import com.nfcmeeting.nfcmeeting.mvp.presenter.base.BasePresenter;
import com.nfcmeeting.nfcmeeting.util.StarWishesHelper;
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ThirtyDegreesRay on 2017/8/9 21:42:47
 */

public class RepositoryPresenter extends BasePresenter<IRepositoryContract.View>
        implements IRepositoryContract.Presenter {

    @AutoAccess(dataName = "repository")
    Repository repository;

    @AutoAccess String owner;
    @AutoAccess String repoName;


    private boolean starred;
    private boolean watched;

    private boolean isStatusChecked = false;
    @AutoAccess boolean isTraceSaved = false;

    private boolean isBookmarkQueried = false;
    private boolean bookmarked = false;

    @Inject
    public RepositoryPresenter(DaoSession daoSession) {
        super(daoSession);
    }

    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
        if (repository != null) {
            owner = repository.getModeratorName();
            repoName = repository.getTitle();
            mView.showRepo(repository);
            getRepoInfo(false);
            checkStatus();
        } else {
            getRepoInfo(true);
        }
    }



    @Override
    public void starRepo(boolean star) {
        starred = star;
        Observable<Response<ResponseBody>> observable = starred ?
                getMeetingService().starMeeting(owner, repoName) :
                getMeetingService().unstarMeeting(owner, repoName);
        executeSimpleRequest(observable);
    }







    private void getRepoInfo(final boolean isShowLoading) {
        if (isShowLoading) mView.showLoading();
        HttpObserver<Repository> httpObserver =
                new HttpObserver<Repository>() {
                    @Override
                    public void onError(Throwable error) {
                        if (isShowLoading) mView.hideLoading();
                        mView.showErrorToast(getErrorTip(error));
                    }

                    @Override
                    public void onSuccess(HttpResponse<Repository> response) {
                        if (isShowLoading) mView.hideLoading();
                        repository = response.body();
                        mView.showRepo(repository);
                        checkStatus();
                        //saveTrace();
                    }
                };

        generalRxHttpExecute(new IObservableCreator<Repository>() {
            @Override
            public Observable<Response<Repository>> createObservable(boolean forceNetWork) {
                return getMeetingService().getMeetingInfo(forceNetWork, repoName);
            }
        }, httpObserver, true);
    }

    private void checkStatus(){
        if(isStatusChecked) return;
        isStatusChecked = true;
        checkStarred();
        //checkWatched();
    }


    private void checkStarred() {
        checkStatus(
                getMeetingService().checkMeetingStarred(owner, repoName),
                new CheckStatusCallback() {
                    @Override
                    public void onChecked(boolean status) {
                        starred = status;
                        mView.invalidateOptionsMenu();
                    }
                }
        );
    }




    public Repository getRepository() {
        return repository;
    }


    public boolean isStarred() {
        return starred;
    }










    public String getRepoName() {
        return repository == null ? repoName : repository.getTitle();
    }






//    private void saveTrace(){
//        daoSession.runInTx(() ->{
//            if(!isTraceSaved){
//                Trace trace = daoSession.getTraceDao().queryBuilder()
//                        .where(TraceDao.Properties.RepoId.eq(repository.getId()))
//                        .unique();
//
//                if(trace == null){
//                    trace = new Trace(UUID.randomUUID().toString());
//                    trace.setType("repo");
//                    trace.setRepoId((long) repository.getId());
//                    Date curDate = new Date();
//                    trace.setStartTime(curDate);
//                    trace.setLatestTime(curDate);
//                    trace.setTraceNum(1);
//                    daoSession.getTraceDao().insert(trace);
//                } else {
//                    trace.setTraceNum(trace.getTraceNum() + 1);
//                    trace.setLatestTime(new Date());
//                    daoSession.getTraceDao().update(trace);
//                }
//            }
//
//            LocalRepo localRepo = daoSession.getLocalRepoDao().load((long) repository.getId());
//            LocalRepo updateRepo = repository.toLocalRepo();
//            if(localRepo == null){
//                daoSession.getLocalRepoDao().insert(updateRepo);
//            } else {
//                daoSession.getLocalRepoDao().update(updateRepo);
//            }
//        });
//        isTraceSaved = true;
//    }

}
