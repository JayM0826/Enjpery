package com.j.enjpery.app.ui.mainactivity.eventbus;

/**
 * Created by J on 2017/7/30.
 */

public class FragmentVisibleEvent {
    private boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public FragmentVisibleEvent(boolean visible) {
        this.visible = visible;
    }

    public FragmentVisibleEvent() {
    }
}
