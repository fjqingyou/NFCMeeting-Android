

package com.nfcmeeting.nfcmeeting.inject.component;



import com.nfcmeeting.nfcmeeting.inject.FragmentScope;
import com.nfcmeeting.nfcmeeting.inject.module.FragmentModule;
import com.nfcmeeting.nfcmeeting.ui.fragment.RepositoriesFragment;
import dagger.Component;

/**
 * Created on 2017/7/18.
 *
 * @author ThirtyDegreesRay
 */

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(RepositoriesFragment fragment);

}
