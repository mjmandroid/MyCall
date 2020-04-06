package com.it.fan.mycall.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.MyFragmentPagerAdapter;
import com.it.fan.mycall.gloable.TableLayoutCallbck;
import com.it.fan.mycall.view.CallTypePopupWindow;
import com.it.fan.mycall.view.MyTablayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan on 2019/6/1.
 */

public class PlayCallFragment extends BaseFragment{


    private MyTablayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] titles = new String[]{"全部通话","未接来电"};
    private MyFragmentPagerAdapter mAdapter;
    private CallTypePopupWindow mCallTypePopupWindow;

    @Override
    protected int getLayout() {
        return R.layout.fragment_play_call;
    }

    @Override
    protected void initView() {
        mTabLayout = rootView.findViewById(R.id.tabLayout);
        mViewPager = rootView.findViewById(R.id.fragment_palay_call_viewpager);
        mFragments.clear();
        mFragments.add(new AllCallFragment());
        mFragments.add(new MissedCallFragment());
        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(),mFragments,titles);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

        mTabLayout.setmCallbck(new TableLayoutCallbck() {
            @Override
            public void checked(boolean leftChecked) {
                if(leftChecked){
                    mViewPager.setCurrentItem(0);
                } else {
                    mViewPager.setCurrentItem(1);
                }

            }

            @Override
            public void arrow(boolean leftChecked, boolean isLeftArrow, boolean isRightArrow) {
                if(isLeftArrow || isRightArrow){
                    if(mCallTypePopupWindow == null){
                        mCallTypePopupWindow = new CallTypePopupWindow(getActivity(),mTabLayout);
                        mCallTypePopupWindow.setBackgroundColor(0x0);
                    }
                    mCallTypePopupWindow.showPopupWindow(mTabLayout);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i == 0){
                    mTabLayout.setCheckedLeft(true);
                } else {
                    mTabLayout.setCheckedLeft(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
