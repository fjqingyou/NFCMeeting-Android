

package com.nfcmeeting.nfcmeeting.mvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nfcmeeting.nfcmeeting.R;
import com.nfcmeeting.nfcmeeting.common.Event;
import com.nfcmeeting.nfcmeeting.dao.DaoSession;
import com.nfcmeeting.nfcmeeting.http.core.HttpObserver;
import com.nfcmeeting.nfcmeeting.http.core.HttpResponse;
import com.nfcmeeting.nfcmeeting.http.error.HttpPageNoFoundError;
import com.nfcmeeting.nfcmeeting.model.PageInfo;
import com.nfcmeeting.nfcmeeting.mvp.contract.IRepositoriesContract;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;
import com.nfcmeeting.nfcmeeting.mvp.model.SearchModel;
import com.nfcmeeting.nfcmeeting.mvp.model.SearchResult;
import com.nfcmeeting.nfcmeeting.mvp.model.filter.RepositoriesFilter;
import com.nfcmeeting.nfcmeeting.mvp.presenter.base.BasePagerPresenter;
import com.nfcmeeting.nfcmeeting.ui.fragment.RepositoriesFragment;
import com.nfcmeeting.nfcmeeting.util.StringUtils;
import com.orhanobut.logger.Logger;
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess;


import org.greenrobot.eventbus.Subscribe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class RepositoriesPresenter extends BasePagerPresenter<IRepositoriesContract.View>
        implements IRepositoriesContract.Presenter {

    private ArrayList<Repository> repos;

    @AutoAccess RepositoriesFragment.RepositoriesType type;
    @AutoAccess String user;
    @AutoAccess String repo;

    @AutoAccess
    SearchModel searchModel;

    @AutoAccess
    RepositoriesFilter filter;



    @Inject
    public RepositoriesPresenter(DaoSession daoSession) {
        super(daoSession);
    }

    @Override
    public void onViewInitialized() {
        super.onViewInitialized();
        if (type.equals(RepositoriesFragment.RepositoriesType.SEARCH)) {
            setEventSubscriber(true);
        }
    }

    @Override
    protected void loadData() {
        if (RepositoriesFragment.RepositoriesType.SEARCH.equals(type)) {
            if (searchModel != null) searchRepos(new PageInfo());
            return;
        }

        loadRepositories(false, new PageInfo());
    }

    @Override
    public void loadRepositories(final boolean isReLoad, PageInfo page) {
        filter = getFilter();
        if (type.equals(RepositoriesFragment.RepositoriesType.SEARCH)) {
            searchRepos(page);
            return;
        }

        mView.showLoading();
        final boolean readCacheFirst = !isReLoad && page.getPageNum() == 1;

        HttpObserver<ArrayList<Repository>> httpObserver = new HttpObserver<ArrayList<Repository>>() {
            @Override
            public void onError(@NonNull Throwable error) {
                mView.hideLoading();
                handleError(error);
            }

            @Override
            public void onSuccess(@NonNull HttpResponse<ArrayList<Repository>> response) {
                mView.hideLoading();
                if (isReLoad || readCacheFirst || repos == null || page.getPageNum() == 1) {
                    repos = response.body();
                } else {
                    repos.addAll(response.body());
                }
                if (response.body().size() == 0 && repos.size() != 0) {
                    mView.setCanLoadMore(false);
                } else {
                    mView.showRepositories(repos);
                }
            }
        };

        generalRxHttpExecute(new IObservableCreator<ArrayList<Repository>>() {
            @Nullable
            @Override
            public Observable<Response<ArrayList<Repository>>> createObservable(boolean forceNetWork) {
                return getObservable(forceNetWork, page);
            }
        }, httpObserver, readCacheFirst);

    }

    @Override
    public void loadRepositories(RepositoriesFilter filter) {
        this.filter = filter;
        loadRepositories(false, new PageInfo());
    }

    private Observable<Response<ArrayList<Repository>>> getObservable(boolean forceNetWork, PageInfo page) {
        switch (type) {
            case OWNED:
                return getMeetingService().getToAttendMeeting(forceNetWork, page);
            case STARRED:
                return getMeetingService().getFinishedMeeting(forceNetWork,  page);

            default:
                return null;
        }
    }

    private void searchRepos(PageInfo page) {
        mView.showLoading();

        HttpObserver<SearchResult<Repository>> httpObserver =
                new HttpObserver<SearchResult<Repository>>() {
                    @Override
                    public void onError(@NonNull Throwable error) {
                        mView.hideLoading();
                        handleError(error);
                    }

                    @Override
                    public void onSuccess(@NonNull HttpResponse<SearchResult<Repository>> response) {
                        mView.hideLoading();
                        if (repos == null || page.getPageNum() == 1) {
                            repos = response.body().getItems();
                        } else {
                            repos.addAll(response.body().getItems());
                        }
                        if (response.body().getItems().size() == 0 && repos.size() != 0) {
                            mView.setCanLoadMore(false);
                        } else {
                            mView.showRepositories(repos);
                        }
                    }
                };
        generalRxHttpExecute(new IObservableCreator<SearchResult<Repository>>() {
            @Nullable
            @Override
            public Observable<Response<SearchResult<Repository>>> createObservable(boolean forceNetWork) {
                return getSearchService().searchRepos(searchModel.getQuery(), searchModel.getSort(),
                        searchModel.getOrder(), page.getPageSize(),page.getPageNum());
            }
        }, httpObserver);
    }

    @Subscribe
    public void onSearchEvent(@NonNull Event.SearchEvent searchEvent) {
        if (!searchEvent.searchModel.getType().equals(SearchModel.SearchType.Repository)) return;
        setLoaded(false);
        this.searchModel = searchEvent.searchModel;
        prepareLoadData();
    }

    private void handleError(Throwable error) {
        if (!StringUtils.isBlankList(repos)) {
            mView.showErrorToast(getErrorTip(error));
        } else if (error instanceof HttpPageNoFoundError) {
            mView.showRepositories(new ArrayList<Repository>());
        } else {
            mView.showLoadError(getErrorTip(error));
        }
    }

    public String getUser() {
        return user;
    }

    public RepositoriesFragment.RepositoriesType getType() {
        return type;
    }

    public RepositoriesFilter getFilter() {
        if (filter == null) {
            filter = RepositoriesFragment.RepositoriesType.STARRED.equals(type) ?
                    RepositoriesFilter.DEFAULT_STARRED_REPO : RepositoriesFilter.DEFAULT;
        }
        return filter;
    }


    private void showQueryRepos(ArrayList<Repository> queryRepos, int page){
        if(repos == null || page == 1){
            repos = queryRepos;
        } else {
            repos.addAll(queryRepos);
        }

        mView.showRepositories(repos);
        mView.hideLoading();
    }

}
