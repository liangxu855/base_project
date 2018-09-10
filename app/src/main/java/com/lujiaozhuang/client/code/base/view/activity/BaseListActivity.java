package com.lujiaozhuang.client.code.base.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lujiaozhuang.client.code.base.presenter.BasePresenter;
import com.lujiaozhuang.client.code.base.view.iview.BaseView;
import com.lujiaozhuang.client.widget.autoloadding.StatusChangListener;
import com.lujiaozhuang.client.widget.autoloadding.SuperRecyclerView;

import java.util.Collection;


/**
 * Created by Administrator on 2016/8/8.
 */

public abstract class BaseListActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpActivity<V, T>
        implements SuperRecyclerView.ListSwipeViewListener {
    protected SuperRecyclerView listSwipeView;

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return listSwipeView.getSwipeRefreshLayout();
    }

    public StatusChangListener getStatusChangListener() {
        return listSwipeView.getStatusChangListener();
    }

    @Override
    public void initView() {
        listSwipeView.getRecyclerView().addItemDecoration(getItemDecoration());
        listSwipeView.getRecyclerView().setLayoutManager(getLayoutManager());
        listSwipeView.setAdapter(getAdapter());
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.setOnLoaddingListener(this);

    }

    public abstract RecyclerView.ItemDecoration getItemDecoration();

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    public abstract RecyclerView.Adapter getAdapter();

    /**
     * 获取分页的有效数据
     */
    public <T> Collection<T> getValidData(Collection<T> c) {
        return listSwipeView.getPagingHelp().getValidData(c);
    }

    public void clearPagingData() {
        listSwipeView.getPagingHelp().clear();
    }

    public int getPageindex() {
        return listSwipeView.getPagingHelp().getPageindex();
    }

    public int getPageCount() {
        return listSwipeView.getPagingHelp().getPageCount();
    }
}
