package com.it.fan.mycall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan on 2019/6/1.
 */

public class PlayCallFragment extends BaseFragment{


    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] titles = new String[]{"全部通话","未接来电"};
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_play_call;
    }

    @Override
    protected void initView() {
        mTabLayout = rootView.findViewById(R.id.fragment_play_call_tablayout);
        mViewPager = rootView.findViewById(R.id.fragment_palay_call_viewpager);
    }

    @Override
    protected void initListener() {
        mFragments.clear();
        mFragments.add(new AllCallFragment());
        mFragments.add(new MissedCallFragment());
        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(),mFragments,titles);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void initData() {

    }
}
