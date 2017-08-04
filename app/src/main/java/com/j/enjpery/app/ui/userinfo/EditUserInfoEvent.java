package com.j.enjpery.app.ui.userinfo;

/**
 * Created by J on 2017/8/4.
 */

class EditUserInfoEvent {

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public EditUserInfoEvent() {
    }

    public EditUserInfoEvent(boolean status) {

        this.status = status;
    }

    public void setStatus(boolean status) {
        this.status = status;

    }
}
