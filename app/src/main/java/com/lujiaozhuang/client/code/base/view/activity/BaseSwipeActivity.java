package com.lujiaozhuang.client.code.base.view.activity;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.lujiaozhuang.client.code.base.presenter.BasePresenter;
import com.lujiaozhuang.client.code.base.view.iview.BaseView;
import com.lujiaozhuang.client.widget.empty_layout.OnRetryClickListion;





/**
 * Created by yang on 2016/8/22.
 * 下拉刷新的基类
 */

public abstract class BaseSwipeActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpActivity<V, T> implements SwipeRefreshLayout.OnRefreshListener, OnRetryClickListion {
    @Nullable
    public SwipeRefreshLayout swipe;

    @Override
    public void initView() {
        if (swipe == null) {
            swipe = getSwipeRefreshLayout();
        }
        swipe.setOnRefreshListener(this);
    }

    /**
     * 获取下拉刷新控件
     */
    public abstract SwipeRefreshLayout getSwipeRefreshLayout();
}
