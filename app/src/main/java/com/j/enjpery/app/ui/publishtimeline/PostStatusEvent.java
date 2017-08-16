package com.j.enjpery.app.ui.publishtimeline;

/**
 * Created by J on 2017/8/16.
 */

class PostStatusEvent {
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public PostStatusEvent(Boolean status) {
        this.status = status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
