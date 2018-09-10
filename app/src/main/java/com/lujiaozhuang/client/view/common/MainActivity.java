package com.lujiaozhuang.client.view.common;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.lujiaozhuang.client.R;
import com.lujiaozhuang.client.code.base.view.activity.BaseActivity;
import com.lujiaozhuang.client.view.collect.fragment.CollectFrament;
import com.lujiaozhuang.client.view.home.fragment.HomeFragment;
import com.lujiaozhuang.client.view.message.fragment.MessageFragment;
import com.lujiaozhuang.client.view.mine.fragment.MineFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private View tabHome, tabCollect, tabMessage, tabMine;
    private Fragment currentFragment; //当前fragment

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        tabHome = findViewById(R.id.tabHome);
        tabCollect = findViewById(R.id.tabCollect);
        tabMessage = findViewById(R.id.tabMessage);
        tabMine = findViewById(R.id.tabMine);


        tabHome.setOnClickListener(this);
        tabCollect.setOnClickListener(this);
        tabMessage.setOnClickListener(this);
        tabMine.setOnClickListener(this);

        tabHome.performClick();
    }

    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    public int getStatusBarResource() {
        return R.color.main_red;
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        switch (vId) {
            case R.id.tabHome:
            case R.id.tabCollect:
            case R.id.tabMessage:
            case R.id.tabMine:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(vId));
                if (fragment == null) {
                    if (view == tabHome) {
                        fragment = new HomeFragment();
                    } else if (view == tabCollect) {
                        fragment = new CollectFrament();
                    } else if (view == tabMessage) {
                        fragment = new MessageFragment();
                    } else if (view == tabMine) {
                        fragment = new MineFragment();
                    }
                    transaction.add(R.id.flContainer, fragment, String.valueOf(vId));
                }
                if (fragment != currentFragment) {
                    if (currentFragment != null) {
                        transaction.hide(currentFragment);
                    }
                    transaction.show(fragment);
                    transaction.commit();
                    currentFragment = fragment;
                }
                break;
        }
    }
}
