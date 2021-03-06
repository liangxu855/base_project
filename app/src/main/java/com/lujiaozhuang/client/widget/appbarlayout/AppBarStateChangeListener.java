package com.lujiaozhuang.client.widget.appbarlayout;

import android.support.design.widget.AppBarLayout;

/**
 * **
 * 创建时间:2016/9/10　10:02
 *
 * <p>
 * 功能介绍：
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            onStateChanged(appBarLayout, State.EXPANDED, Math.abs(i), appBarLayout.getTotalScrollRange());
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            onStateChanged(appBarLayout, State.COLLAPSED, Math.abs(i), appBarLayout.getTotalScrollRange());
            mCurrentState = State.COLLAPSED;
        } else {
            onStateChanged(appBarLayout, State.IDLE, Math.abs(i), appBarLayout.getTotalScrollRange());
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state, int position, int total);
}
