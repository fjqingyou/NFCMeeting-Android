package com.nfcmeeting.nfcmeeting.mvp.contract.base;

/**
 * Created_Time by ThirtyDegreesRay on 2017/9/22 10:47:32
 */

public interface IBasePagerContract {

    interface View extends IBaseContract.View {
        boolean isPagerFragment();
        boolean isFragmentShowed();
    }

    interface Presenter<V extends IBasePagerContract.View>
            extends IBaseContract.Presenter<V> {
        void prepareLoadData();
    }

}
