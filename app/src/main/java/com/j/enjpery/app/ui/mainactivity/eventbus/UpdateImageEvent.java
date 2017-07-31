package com.j.enjpery.app.ui.mainactivity.eventbus;

import com.avos.avoscloud.AVException;

/**
 * Created by J on 2017/7/31.
 */

public class UpdateImageEvent {

    public UpdateImageEvent(AVException exception, boolean status) {
        this.exception = exception;
        this.status = status;
    }

    public AVException getException() {
        return exception;
    }

    public void setException(AVException exception) {
        this.exception = exception;
    }

    private AVException exception;

    public UpdateImageEvent() {
    }

    private boolean status;

    public UpdateImageEvent(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
