

package com.nfcmeeting.nfcmeeting.common;

import org.greenrobot.eventbus.EventBus;

/**
 * 事件总线
 * Created_Time by ThirtyDegreesRay on 2016/8/22 14:55
 */

public enum  AppEventBus {
    INSTANCE;

    AppEventBus(){
        init();
    }

    private EventBus eventBus ;

    private void init(){
        eventBus = EventBus.builder()
                .installDefaultEventBus();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
