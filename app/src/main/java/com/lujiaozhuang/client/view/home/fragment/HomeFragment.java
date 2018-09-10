package com.lujiaozhuang.client.view.home.fragment;

import com.lujiaozhuang.client.R;
import com.lujiaozhuang.client.code.base.view.fragment.BaseMvpFragment;
import com.lujiaozhuang.client.presenter.home.HomePresenter;

public class HomeFragment extends BaseMvpFragment<HomeFragmentInterface, HomePresenter>
        implements HomeFragmentInterface{

    @Override
    public HomePresenter initPressenter() {
        return new HomePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public void clearData() {

    }
}
