package com.lujiaozhuang.client.view.mine.fragment;

import com.lujiaozhuang.client.R;
import com.lujiaozhuang.client.code.base.view.fragment.BaseMvpFragment;
import com.lujiaozhuang.client.presenter.mine.MinePresenter;
import com.lujiaozhuang.client.view.message.fragment.MessageFragmentInterface;

public class MineFragment extends BaseMvpFragment<MineFragmentInterface,MinePresenter>
        implements MessageFragmentInterface {
    @Override
    public MinePresenter initPressenter() {
        return new MinePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {

    }

    @Override
    public void clearData() {

    }
}
