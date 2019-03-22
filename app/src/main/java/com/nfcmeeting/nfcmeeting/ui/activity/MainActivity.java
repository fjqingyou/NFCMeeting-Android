

package com.nfcmeeting.nfcmeeting.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.nfcmeeting.nfcmeeting.AppData;
import com.nfcmeeting.nfcmeeting.R;
import com.nfcmeeting.nfcmeeting.common.GlideApp;
import com.nfcmeeting.nfcmeeting.dao.AuthUser;
import com.nfcmeeting.nfcmeeting.inject.component.AppComponent;
import com.nfcmeeting.nfcmeeting.inject.component.DaggerActivityComponent;
import com.nfcmeeting.nfcmeeting.inject.module.ActivityModule;
import com.nfcmeeting.nfcmeeting.model.User;
import com.nfcmeeting.nfcmeeting.mvp.contract.IMainContract;
import com.nfcmeeting.nfcmeeting.mvp.model.filter.RepositoriesFilter;
import com.nfcmeeting.nfcmeeting.mvp.presenter.MainPresenter;
import com.nfcmeeting.nfcmeeting.ui.activity.base.BaseDrawerActivity;
import com.nfcmeeting.nfcmeeting.ui.fragment.RepositoriesFragment;
import com.nfcmeeting.nfcmeeting.ui.fragment.base.BaseFragment;
import com.nfcmeeting.nfcmeeting.util.PrefUtils;
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseDrawerActivity<MainPresenter>
        implements IMainContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.frame_layout_content) FrameLayout frameLayoutContent;

    private AppCompatImageView toggleAccountBn;

    private final Map<Integer, String> TAG_MAP = new HashMap<>();

    private final int SETTINGS_REQUEST_CODE = 100;

    @AutoAccess int selectedPage ;
    private boolean isAccountsAdded = false;

    private final List<Integer> FRAGMENT_NAV_ID_LIST = Arrays.asList(
            R.id.nav_owned,  R.id.nav_starred
    );

    private final List<String> FRAGMENT_TAG_LIST = Arrays.asList(

            RepositoriesFragment.RepositoriesType.OWNED.name(),

            RepositoriesFragment.RepositoriesType.STARRED.name()

    );

    private final List<Integer> FRAGMENT_TITLE_LIST = Arrays.asList(
            R.string.my_repos, R.string.starred_repos
    );

    {
        for(int i = 0; i < FRAGMENT_NAV_ID_LIST.size(); i++){
            TAG_MAP.put(FRAGMENT_NAV_ID_LIST.get(i), FRAGMENT_TAG_LIST.get(i));
        }
    }


    /**
     * 依赖注入的入口
     *
     * @param appComponent appComponent
     */
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    protected void initActivity() {
        super.initActivity();

        setStartDrawerEnable(true);
        setEndDrawerEnable(true);

    }

    /**
     * 获取ContentView id
     *
     * @return
     */
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    /**
     * 初始化view
     *
     * @param savedInstanceState
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        setToolbarScrollAble(true);
        updateStartDrawerContent(R.menu.activity_main_drawer);
        removeEndDrawer();

        selectedPage =  R.id.nav_owned;
        updateFragmentByNavId(selectedPage);

        navViewStart.setCheckedItem(selectedPage);

        ImageView avatar = navViewStart.getHeaderView(0).findViewById(R.id.avatar);
        TextView name = navViewStart.getHeaderView(0).findViewById(R.id.name);
        TextView mail = navViewStart.getHeaderView(0).findViewById(R.id.mail);

        toggleAccountBn = navViewStart.getHeaderView(0).findViewById(R.id.toggle_account_bn);
        toggleAccountBn.setOnClickListener(v -> {
            toggleAccountLay();
        });

        //AuthUser loginUser = AppData.INSTANCE.getAuthUser();
        User loginUser = AppData.INSTANCE.getLoggedUser();
        GlideApp.with(getActivity())
                .load(loginUser.getAvatar())
                .onlyRetrieveFromCache(!PrefUtils.isLoadImageEnable())
                .into(avatar);
        name.setText(loginUser.getUserName() );
//        String joinTime = getString(R.string.joined_at).concat(" ")
//                .concat(StringUtils.getDateStr(loginUser.getCreatedAt()));
        mail.setText(loginUser.getName());

        tabLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        MenuItem menuItem = menu.findItem(R.id.nav_sort);
        menuItem.setVisible(selectedPage == R.id.nav_owned || selectedPage == R.id.nav_starred);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateMainMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected boolean isEndDrawerMultiSelect() {
        return true;
    }

    @Override
    protected int getEndDrawerToggleMenuItemId() {
        return R.id.nav_sort;
    }

    protected void onNavItemSelected(@NonNull MenuItem item, boolean isStartDrawer) {
        super.onNavItemSelected(item, isStartDrawer);
        if (!isStartDrawer) {
            handlerEndDrawerClick(item);
            return;
        }
        int id = item.getItemId();
        updateFragmentByNavId(id);
    }

    private void updateFragmentByNavId(int id){
        if(FRAGMENT_NAV_ID_LIST.contains(id)){
            updateTitle(id);
            loadFragment(id);
            updateFilter(id);
            return;
        }
        switch (id) {
            case R.id.nav_profile:
                ProfileActivity.show(getActivity(), AppData.INSTANCE.getLoggedUser().getUserId().toString(),
                        AppData.INSTANCE.getLoggedUser().getAvatar());
                break;
            case R.id.nav_notifications:
                NotificationsActivity.show(getActivity());
                break;

            case R.id.nav_search:
                SearchActivity.show(getActivity());
                break;
            case R.id.nav_settings:
                SettingsActivity.show(getActivity(), SETTINGS_REQUEST_CODE);
                break;
            case R.id.nav_about:
                AboutActivity.show(getActivity());
                break;
            case R.id.nav_logout:
                logout();
                break;
            default:
                break;
        }
    }

    private void updateFilter(int itemId) {
        if (itemId == R.id.nav_owned) {
            updateEndDrawerContent(R.menu.menu_meetings_filter);
            RepositoriesFilter.initDrawer(navViewEnd, RepositoriesFragment.RepositoriesType.OWNED);
        } else if (itemId == R.id.nav_starred) {
            updateEndDrawerContent(R.menu.menu_meetings_filter);
            RepositoriesFilter.initDrawer(navViewEnd, RepositoriesFragment.RepositoriesType.STARRED);
        } else {
            removeEndDrawer();
        }
        invalidateOptionsMenu();
    }

    private void updateTitle(int itemId) {
        int titleId = FRAGMENT_TITLE_LIST.get(FRAGMENT_NAV_ID_LIST.indexOf(itemId));
        setToolbarTitle(getString(titleId));
    }

    private void loadFragment(int itemId) {
        selectedPage = itemId;
        String fragmentTag = TAG_MAP.get(itemId);
        Fragment showFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        boolean isExist = true;
        if (showFragment == null) {
            isExist = false;
            showFragment = getFragment(itemId);
        }
        if (showFragment.isVisible()) {
            return;
        }

        Fragment visibleFragment = getVisibleFragment();
        if (isExist) {
            showAndHideFragment(showFragment, visibleFragment);
        } else {
            addAndHideFragment(showFragment, visibleFragment, fragmentTag);
        }
    }

    @NonNull
    private Fragment getFragment(int itemId) {
        switch (itemId) {
            case R.id.nav_owned:
                return RepositoriesFragment.create(RepositoriesFragment.RepositoriesType.OWNED,
                        AppData.INSTANCE.getLoggedUser().getUserId().toString());
            case R.id.nav_starred:
                return RepositoriesFragment.create(RepositoriesFragment.RepositoriesType.STARRED,
                        AppData.INSTANCE.getLoggedUser().getUserId().toString());

        }
        return null;
    }

    private void showAndHideFragment(@NonNull Fragment showFragment, @Nullable Fragment hideFragment) {
        if (hideFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(showFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(showFragment)
                    .hide(hideFragment)
                    .commit();
        }

    }

    private void addAndHideFragment(@NonNull Fragment showFragment,
                                    @Nullable Fragment hideFragment, @NonNull String addTag) {
        if (hideFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout_content, showFragment, addTag)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout_content, showFragment, addTag)
                    .hide(hideFragment)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == RESULT_OK) {
            recreate();
        }
    }

    @Override
    protected void onToolbarDoubleClick() {
        super.onToolbarDoubleClick();
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).scrollToTop();
        }
    }

    private void handlerEndDrawerClick(MenuItem item) {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof RepositoriesFragment
                && (selectedPage == R.id.nav_owned || selectedPage == R.id.nav_starred)) {
            ((RepositoriesFragment) fragment).onDrawerSelected(navViewEnd, item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private boolean isManageAccount = false;
    private void toggleAccountLay(){
        isManageAccount = !isManageAccount;
        toggleAccountBn.setImageResource(isManageAccount ? R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down);
        invalidateMainMenu();
    }

    private void invalidateMainMenu(){
        if(navViewStart == null){
            return ;
        }
        Menu menu = navViewStart.getMenu();

//        if(!isAccountsAdded){
//            isAccountsAdded = true;
//            List<AuthUser> users = mPresenter.getLoggedUserList();
//            for(AuthUser user : users){
//                MenuItem menuItem = menu.add(R.id.manage_accounts, Menu.NONE, 1, user.getLoginId())
//                        .setIcon(R.drawable.ic_menu_person)
//                        .setOnMenuItemClickListener(item -> {
//                            mPresenter.toggleAccount(item.getTitle().toString());
//                            return true;
//                        });
//            }
//        }

        menu.setGroupVisible(R.id.my_account, isManageAccount);
        //menu.setGroupVisible(R.id.manage_accounts, isManageAccount);

        menu.setGroupVisible(R.id.my, !isManageAccount);
        menu.setGroupVisible(R.id.meetings, !isManageAccount);
        menu.setGroupVisible(R.id.search, !isManageAccount);
        menu.setGroupVisible(R.id.setting, !isManageAccount);

    }

    @Override
    public void restartApp() {
        getActivity().finishAffinity();
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        startActivity(intent);
    }

    private void logout() {
        new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setTitle(R.string.warning_dialog_tile)
                .setMessage(R.string.logout_warning)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.logout, (dialog, which) -> {
                    mPresenter.logout();
                })
                .show();
    }

}
