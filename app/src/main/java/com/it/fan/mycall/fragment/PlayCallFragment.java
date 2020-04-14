package com.it.fan.mycall.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.MyFragmentPagerAdapter;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.ConfigBean;
import com.it.fan.mycall.gloable.TableLayoutCallbck;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.view.CallTypePopupWindow;
import com.it.fan.mycall.view.MyTablayout;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fan on 2019/6/1.
 */

public class PlayCallFragment extends BaseFragment{


    private MyTablayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] titles = new String[]{"全部通话","未接来电"};
    private MyFragmentPagerAdapter mAdapter;
    private CallTypePopupWindow mCallTypeLeftPopupWindow;
    private CallTypePopupWindow mCallTypeRightPopupWindow;
    private List<ConfigBean> configBeanList;

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
                if(isLeftArrow){
                    if(mCallTypeLeftPopupWindow == null){
                        mCallTypeLeftPopupWindow = new CallTypePopupWindow(getActivity(),mTabLayout,configBeanList);
                        mCallTypeLeftPopupWindow.setBackgroundColor(0x0);
                        mCallTypeLeftPopupWindow.setmCallback(new CallTypePopupWindow.Callback() {
                            @Override
                            public void queryId(ConfigBean bean) {
                                mTabLayout.restoreArrow(bean.getProName(),null);
                               AllCallFragment fragment = (AllCallFragment) mFragments.get(0);
                               fragment.classifyQuery(bean.getId());
                            }
                        });
                    }
                    mCallTypeLeftPopupWindow.showPopupWindow(mTabLayout);
                } else if(isRightArrow){
                    if(mCallTypeRightPopupWindow == null){
                        mCallTypeRightPopupWindow = new CallTypePopupWindow(getActivity(),mTabLayout,configBeanList);
                        mCallTypeRightPopupWindow.setBackgroundColor(0x0);
                        mCallTypeRightPopupWindow.setmCallback(new CallTypePopupWindow.Callback() {
                            @Override
                            public void queryId(ConfigBean bean) {
                                mTabLayout.restoreArrow(null,bean.getProName());
                                MissedCallFragment fragment = (MissedCallFragment) mFragments.get(1);
                                fragment.classifyQuery(bean.getId());
                            }
                        });
                    }
                    mCallTypeRightPopupWindow.showPopupWindow(mTabLayout);
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
        OkGo.post(Api.CONFIG_INFO)
                .execute(new JsonCallback<BaseBean<List<ConfigBean>>>() {
                    @Override
                    public void onSuccess(BaseBean<List<ConfigBean>> listBaseBean, Call call, Response response) {
                        if(listBaseBean.getResult() == 0){
                            configBeanList = listBaseBean.getData();
                        }
                    }
                });
    }
}
