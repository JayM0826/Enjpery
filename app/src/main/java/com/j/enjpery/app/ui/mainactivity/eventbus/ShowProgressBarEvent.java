package com.j.enjpery.app.ui.mainactivity.eventbus;

/**
 * Created by J on 2017/8/2.
 */

public class ShowProgressBarEvent {
    private boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ShowProgressBarEvent(boolean visible) {
        this.visible = visible;
    }
    public ShowProgressBarEvent() {
        this.visible = visible;
    }
}
