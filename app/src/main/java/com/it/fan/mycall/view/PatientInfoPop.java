package com.it.fan.mycall.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.fan.mycall.R;
import com.it.fan.mycall.activity.WebActivity;
import com.it.fan.mycall.bean.PatientDeatilBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.StringUtil;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by fan on 2019/6/7.
 */

public class PatientInfoPop extends BasePopupWindow {


    @BindView(R.id.popup_info_close)
    ImageView mClose;
    @BindView(R.id.popup_info_userName)
    TextView mUserName;
    @BindView(R.id.popup_info_viewpager)
    ViewPager mViewPager;

    private Context mContext;
    private FragmentManager fm;
    private int mSortPosition;
    private String userId;
    private List<PatientDeatilBean.DataBean> patientDetailList;
    private List<Fragment> mFragments = new ArrayList<>();


    public PatientInfoPop(Context context, String userId) {
        super(context);
        this.userId = userId;
        mContext=context;
        //initViewPager();
        initData();
    }



    private void initData() {
        OkGo.post(Api.PatientInfoDetail)
                .params("userId", userId)
                .execute(new JsonCallback<PatientDeatilBean>() {
                    @Override
                    public void onSuccess(PatientDeatilBean patientDeatilBean, Call call, Response response) {
                        if (patientDeatilBean != null) {
                            if (patientDeatilBean.getResult() == 0) {
                                patientDetailList = patientDeatilBean.getData();
                                initViewPager();
                            }
                        }
                    }

                });
    }


    private void initViewPager() {
        if (patientDetailList!=null && patientDetailList.size()>0){
            mUserName.setText(patientDetailList.get(0).getUserName());
            if (mViewPager!=null)
            mViewPager.setAdapter(new MyPagerAdapter());
        }
    }






    @Override
    public View onCreateContentView() {
        View view = createPopupById(R.layout.popup_patient_info);
        ButterKnife.bind(this, view);

        return view;
    }


    @OnClick({R.id.popup_info_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.popup_info_close:
                dismiss();
                break;
        }
    }


    private class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return patientDetailList.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(container.getContext(),R.layout.fragment_patient_detail,null);
            TextView mHospital = view.findViewById(R.id.popup_info_hospital);
            TextView mDoctor = view.findViewById(R.id.popup_info_doctor);
            TextView mDrugstore = view.findViewById(R.id.popup_info_drugstore);
            TextView mCount = view.findViewById(R.id.popup_info_count);
            TextView mDate = view.findViewById(R.id.popup_info_date);
            TextView mTime = view.findViewById(R.id.popup_info_time);
            TextView mStatus = view.findViewById(R.id.popup_info_status);
            TextView mDetailQuery = view.findViewById(R.id.popup_info_detail_query);

            if (patientDetailList!=null){
                PatientDeatilBean.DataBean dataBean = patientDetailList.get(position);
                if (dataBean != null) {
                    //mUserName.setText(dataBean.getUserName());
                    mHospital.setText(dataBean.getHospital());
                    mDoctor.setText(dataBean.getDoctors());
                    mDrugstore.setText(dataBean.getDrugstore());
                    mCount.setText("第" + dataBean.getNumber() + "次");
                    mDate.setText(dataBean.getAppointmentDateStr());
                    mTime.setText(dataBean.getAppointmentDateTimeStr());
                    mStatus.setText(getTextStatus(dataBean.getStatus()));
                }
            }

            mDetailQuery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (patientDetailList!=null) {
                        PatientDeatilBean.DataBean dataBean = patientDetailList.get(position);
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra(GloableConstant.PATIENT_NAME_DETAIL, Api.PatientDetailH5 + dataBean.getUserId() + "&rid=" + dataBean.getAppointmentNumber());
                        getContext().startActivity(intent);
                    }
                }
            });

            container.addView(view);
            //最后要返回的是控件本身
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }


    private String getTextStatus(String status) {
        for (int i = 0; i < StringUtil.paramsStrs.length; i++) {
            if (status.equals(StringUtil.paramsStrs[i])) {
                return StringUtil.sortStrs[i];
            }
        }
        return status;
    }
}
