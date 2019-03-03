

package com.nfcmeeting.nfcmeeting;

import android.support.annotation.Nullable;

import com.nfcmeeting.nfcmeeting.dao.AuthUser;
import com.thirtydegreesray.dataautoaccess.annotation.AutoAccess;


import java.util.Locale;



public enum AppData {
    INSTANCE;


    /*@AutoAccess(dataName = "appData_authUser")*/ AuthUser authUser;
    @AutoAccess(dataName = "appData_systemDefaultLocal") Locale systemDefaultLocal;



    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Nullable
    public String getAccessToken() {
        return authUser == null ? null : authUser.getAccessToken();
    }

    public Locale getSystemDefaultLocal() {
        if(systemDefaultLocal == null){
            systemDefaultLocal = Locale.getDefault();
        }
        return systemDefaultLocal;
    }

}
