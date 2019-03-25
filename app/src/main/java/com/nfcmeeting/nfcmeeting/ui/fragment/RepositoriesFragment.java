

package com.nfcmeeting.nfcmeeting.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.nfcmeeting.nfcmeeting.R;
import com.nfcmeeting.nfcmeeting.inject.component.AppComponent;
import com.nfcmeeting.nfcmeeting.inject.component.DaggerFragmentComponent;
import com.nfcmeeting.nfcmeeting.inject.module.FragmentModule;
import com.nfcmeeting.nfcmeeting.model.PageInfo;
import com.nfcmeeting.nfcmeeting.mvp.contract.IRepositoriesContract;
import com.nfcmeeting.nfcmeeting.mvp.model.Repository;
import com.nfcmeeting.nfcmeeting.mvp.model.SearchModel;
import com.nfcmeeting.nfcmeeting.mvp.model.filter.RepositoriesFilter;
import com.nfcmeeting.nfcmeeting.mvp.presenter.RepositoriesPresenter;
import com.nfcmeeting.nfcmeeting.ui.Adapter.RepositoriesAdapter;
import com.nfcmeeting.nfcmeeting.ui.activity.RepositoryActivity;
import com.nfcmeeting.nfcmeeting.ui.fragment.base.ListFragment;
import com.nfcmeeting.nfcmeeting.ui.fragment.base.OnDrawerSelectedListener;
import com.nfcmeeting.nfcmeeting.util.BundleHelper;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created on 2017/7/18.
 *
 * @author ThirtyDegreesRay
 */

public class RepositoriesFragment extends ListFragment<RepositoriesPresenter, RepositoriesAdapter>
            implements IRepositoriesContract.View, OnDrawerSelectedListener {

    public enum RepositoriesType{
        OWNED, PUBLIC, STARRED, TRENDING, SEARCH, FORKS, TRACE, BOOKMARK, COLLECTION, TOPIC
    }

    public static RepositoriesFragment create(@NonNull RepositoriesType type,
                                              @NonNull String user){
        RepositoriesFragment fragment = new RepositoriesFragment();
        fragment.setArguments(BundleHelper.builder().put("type", type)
                .put("user", user).build());
        return fragment;
    }



    public static RepositoriesFragment createForForks(@NonNull String user, @NonNull String repo){
        RepositoriesFragment fragment = new RepositoriesFragment();
        fragment.setArguments(BundleHelper.builder()
                .put("type", RepositoriesType.FORKS)
                .put("user", user)
                .put("repo", repo)
                .build());
        return fragment;
    }

    public static RepositoriesFragment createForSearch(@NonNull SearchModel searchModel){
        RepositoriesFragment fragment = new RepositoriesFragment();
        fragment.setArguments(
                BundleHelper.builder()
                        .put("type", RepositoriesType.SEARCH)
                        .put("searchModel", searchModel)
                        .build()
        );
        return fragment;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void showRepositories(ArrayList<Repository> repositoryList) {
        adapter.setData(repositoryList);
        postNotifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initFragment(Bundle savedInstanceState){
        super.initFragment(savedInstanceState);
        setLoadMoreEnable(!RepositoriesType.COLLECTION.equals(mPresenter.getType()));
    }

    @Override
    protected void onReLoadData() {
        mPresenter.loadRepositories(true, new PageInfo());
    }

    @Override
    protected String getEmptyTip() {

        return getString(R.string.no_repository);
    }

    @Override
    public void onItemClick(int position, @NonNull View view) {
        super.onItemClick(position, view);
            RepositoryActivity.show(getActivity(), adapter.getData().get(position));

    }

    @Override
    protected void onLoadMore(PageInfo page) {
        super.onLoadMore(page);
        mPresenter.loadRepositories(false, page);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        if(mPresenter.getType().equals(RepositoriesType.OWNED)){
//            inflater.inflate(R.menu.menu_owned_repo, menu);
//            if(!mPresenter.getUser().equals(AppData.INSTANCE.getLoggedUser().getLogin())){
//                menu.findItem(R.id.action_filter_public).setVisible(false);
//                menu.findItem(R.id.action_filter_private).setVisible(false);
//            }
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentShowed() {
        super.onFragmentShowed();
        if(mPresenter != null) mPresenter.prepareLoadData();
    }

    @Override
    public void onDrawerSelected(@NonNull NavigationView navView, @NonNull MenuItem item) {
        RepositoriesFilter filter = RepositoriesFilter.generateFromDrawer(navView);
        mPresenter.loadRepositories(filter);
    }


}
