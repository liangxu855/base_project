package com.lujiaozhuang.client.view.collect.fragment;

import com.lujiaozhuang.client.R;
import com.lujiaozhuang.client.code.base.view.fragment.BaseMvpFragment;
import com.lujiaozhuang.client.presenter.collect.CollectPresenter;

public class CollectFrament extends BaseMvpFragment<CollectFragmentInterface,CollectPresenter>
        implements CollectFragmentInterface{
    @Override
    public CollectPresenter initPressenter() {
        return new CollectPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_collect;
    }

    @Override
    public void initView() {

    }

    @Override
    public void clearData() {

    }
}
