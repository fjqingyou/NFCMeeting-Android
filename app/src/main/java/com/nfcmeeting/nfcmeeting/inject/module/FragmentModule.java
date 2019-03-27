

package com.nfcmeeting.nfcmeeting.inject.module;

import android.content.Context;


import com.nfcmeeting.nfcmeeting.inject.FragmentScope;
import com.nfcmeeting.nfcmeeting.ui.fragment.base.BaseFragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created_Time on 2017/7/18.
 *
 * @author ThirtyDegreesRay
 */
@Module
public class FragmentModule {

    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public BaseFragment provideFragment(){
        return mFragment;
    }

    @Provides
    @FragmentScope
    public Context provideContext(){
        return mFragment.getActivity();
    }
}
