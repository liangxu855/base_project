package com.lujiaozhuang.client.view.message.fragment;

import com.lujiaozhuang.client.R;
import com.lujiaozhuang.client.code.base.view.fragment.BaseMvpFragment;
import com.lujiaozhuang.client.presenter.message.MessagePresenter;

public class MessageFragment extends BaseMvpFragment<MessageFragmentInterface,MessagePresenter>
        implements MessageFragmentInterface {
    @Override
    public MessagePresenter initPressenter() {
        return new MessagePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView() {

    }

    @Override
    public void clearData() {

    }
}
