package com.nfcmeeting.nfcmeeting.mvp.contract.base;

/**
 * Created_Time by ThirtyDegreesRay on 2017/9/22 10:47:52
 */

public interface IBaseListContract {

    interface View {
        void showLoadError(String errorMsg);

        void setCanLoadMore(boolean canLoadMore);
    }

}
