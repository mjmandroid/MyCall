package com.it.fan.mycall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.fan.mycall.R;
import com.it.fan.mycall.activity.PatientListActivity;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.StringUtil;
import com.it.fan.mycall.view.ApplyForPop;
import com.it.fan.mycall.view.PatientInfoPop;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by fan on 2019/9/1.
 */

public class PatientInfoQueryFragment extends BaseFragment {


    @BindView(R.id.fragment_patient_info_cityEditText)
    EditText mCityEditText;
    @BindView(R.id.fragment_patient_info_provinceEditText)
    EditText mProvinceEditText;
    @BindView(R.id.fragment_patient_info_hospitalEditText)
    EditText mHospitalEditText;
    @BindView(R.id.fragment_patient_info_drugstoreEditText)
    EditText mDrugstoreEditText;
    @BindView(R.id.fragment_patient_info_injectStartDateText)
    TextView mInjectStartDateText;

    @BindView(R.id.fragment_patient_info_injectEndDateText)
    TextView mInjectEndDateText;
    @BindView(R.id.fragment_patient_info_userNameEditText)
    EditText mUserNameEditText;
    @BindView(R.id.fragment_patient_info_userCallEditText)
    EditText mUserCallEditText;
    @BindView(R.id.fragment_patient_info_applyForStutas)
    TextView mApplyForStutas;
    @BindView(R.id.fragment_patient_info_searchBtn)
    TextView mSearchBtn;
    Unbinder unbinder;
    @BindView(R.id.fragment_patient_info_injectStartTimeText)
    TextView mInjectStartTimeText;
    @BindView(R.id.fragment_patient_info_injectEndTimeText)
    TextView mInjectEndTimeText;
    @BindView(R.id.fragment_patient_info_injectDateRL)
    RelativeLayout mInjectDateRL;
    @BindView(R.id.fragment_patient_info_injectTimeRL)
    RelativeLayout mInjectTimeRL;
    @BindView(R.id.fragment_patient_info_applyForIntro)
    TextView mIntro;
    private int dateCount;
    private boolean isStartDate=true;
    private double timeqCount;
    private boolean isStartTime=true;
    private int timeCount;

    @Override
    protected int getLayout() {
        return R.layout.fragment_patient_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    private void startDatePicker() {
        DatePicker datePicker = new DatePicker(getActivity(), DatePicker.YEAR_MONTH_DAY);
        if (dateCount % 2 == 0) {
            //选择开始日期
            datePicker.setTitleText("选择开始日期");
            isStartDate = true;
        }else {
            //选择结束日期
            datePicker.setTitleText("选择结束日期");
            isStartDate = false;
        }


        datePicker.setRangeStart(2017, 9, 1);
        datePicker.setRangeEnd(2060, 9, 1);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String date= year + "-" + month + "-" + day;
                dateCount++;
                if (isStartDate){
                    mInjectStartDateText.setText(date);
                }else {
                    mInjectEndDateText.setText("至"+date);
                }
            }
        });
        datePicker.show();

    }

    private void startTimePicker() {
        TimePicker picker = new TimePicker(getActivity(), TimePicker.HOUR_24);
        if (timeCount % 2 == 0) {
            //选择开始日期
            picker.setTitleText("选择开始时间");
            isStartTime = true;
        }else {
            //选择结束日期

            picker.setTitleText("选择结束时间");
            isStartTime = false;
        }
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                String time = hour + ":" + minute;
                timeCount++;
                if (isStartTime){
                    mInjectStartTimeText.setText(time);
                }else {
                    mInjectEndTimeText.setText("至"+time);
                }
            }
        });
        picker.show();

    }



    @OnClick({R.id.fragment_patient_info_injectDateRL, R.id.fragment_patient_info_injectTimeRL, R.id.fragment_patient_info_searchBtn,R.id.fragment_patient_info_applyForStutas})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_patient_info_injectDateRL:
                startDatePicker();
                break;
            case R.id.fragment_patient_info_injectTimeRL:
                startTimePicker();
                break;
            case R.id.fragment_patient_info_applyForStutas:
                ApplyForPop pop = new ApplyForPop(getContext());
                pop.setPopupGravity(BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR,Gravity.TOP);
                pop.setOnQueryTypeSelectedListener(new ApplyForPop.OnQueryTypeSelectedListener() {
                    @Override
                    public void onItemClicked(String bean,int position) {
                        mApplyForStutas.setText(bean);
                        mIntro.setText(StringUtil.introStrs[position]);
                    }
                });
                pop.showPopupWindow(mApplyForStutas);

                /*pop.setPopupGravity(Gravity.CENTER);
                pop.showPopupWindow();*/
                break;

            case R.id.fragment_patient_info_searchBtn:


                goSearch();
                break;
        }
    }

    private void goSearch() {
        String city = mCityEditText.getText().toString();
        String province = mProvinceEditText.getText().toString();
        String hospital = mHospitalEditText.getText().toString();
        String drugstore = mDrugstoreEditText.getText().toString();
        String startDate = mInjectStartDateText.getText().toString();//预约开始日期
        String endDate = mInjectEndDateText.getText().toString();
        String startTime = mInjectStartTimeText.getText().toString();
        String endTime = mInjectEndTimeText.getText().toString();
        String userName = mUserNameEditText.getText().toString();
        String userCall = mUserCallEditText.getText().toString();
        String applyforStatus = mApplyForStutas.getText().toString();

        /*if (TextUtils.isEmpty(city) && TextUtils.isEmpty(province) && TextUtils.isEmpty(hospital) && TextUtils.isEmpty(drugstore)
                && TextUtils.isEmpty(userName) && TextUtils.isEmpty(userCall)){
            return;
        }*/

        endDate = endDate.replace("至","");
        endTime = endTime.replace("至","");
        mInjectStartDateText.setText("");
        mInjectEndDateText.setText("");
        mInjectStartTimeText.setText("");
        mInjectEndTimeText.setText("");
        Intent intent = new Intent(getContext(), PatientListActivity.class);
        intent.putExtra("city",city);
        intent.putExtra("province",province);
        intent.putExtra("hospital",hospital);
        intent.putExtra("drugstore",drugstore);
        intent.putExtra("startDate",startDate);
        intent.putExtra("endDate",endDate);
        intent.putExtra("startTime",startTime);
        intent.putExtra("endTime",endTime);
        intent.putExtra("userName",userName);
        intent.putExtra("userCall",userCall);
        intent.putExtra("applyforStatus",getTextStatus(applyforStatus));
        getActivity().startActivity(intent);

    }


    private String getTextStatus(String status) {
        for (int i = 0; i < StringUtil.sortStrs.length; i++) {
            if (status.equals(StringUtil.sortStrs[i])) {
                return StringUtil.paramsStrs[i];
            }
        }
        return status;
    }
}
