package com.j.enjpery.app.ui.mainactivity.eventbus;

/**
 * Created by luoyong on 2017/7/2/0002.
 */

public class NetworkEvent {
    private String networkInfo;

    public String getNetworkInfo() {
        return networkInfo;
    }

    public void setNetworkInfo(String networkInfo) {
        this.networkInfo = networkInfo;
    }

    public NetworkEvent() {
    }

    public NetworkEvent(String networkInfo) {

        this.networkInfo = networkInfo;
    }

}
