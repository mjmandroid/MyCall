package com.it.fan.mycall.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.CallRecordAdapter;
import com.it.fan.mycall.bean.ConfigBean;
import com.it.fan.mycall.bean.TodayCallBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.view.ProgressHUD;
import com.it.fan.mycall.view.QueryTypePop;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.CallRecordBean;
import com.it.fan.mycall.bean.CallRecordListBean;
import com.it.fan.mycall.bean.QueryTypeBean;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.GlobalUtil;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.view.ScreenCallRecordPop;
import com.it.fan.mycall.view.date.MDatePickerDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fan on 2019/6/1.
 */

public class CallRecordFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_call_record_real_caller)
    EditText mRealCaller;
    @BindView(R.id.fragment_call_record_real_called)
    EditText mRealCalled;
    @BindView(R.id.fragment_call_record_real_startTime)
    TextView mStartTime;
    @BindView(R.id.fragment_call_record_real_endTime)
    TextView mEndTime;
    @BindView(R.id.fragment_call_record_real_queryType)
    TextView mQueryType;
    @BindView(R.id.fragment_call_record_real_callStatus)
    TextView mCallStatus;
    @BindView(R.id.fragment_call_record_real_search)
    TextView mSearch;
    @BindView(R.id.fragment_project_name)
    TextView fragment_project_name;
    @BindView(R.id.tv_enter_num)
    TextView tv_enter_num;
    @BindView(R.id.tv_out_num)
    TextView tv_out_num;
    @BindView(R.id.tv_loss_num)
    TextView tv_loss_num;


    List<QueryTypeBean> mlist = new ArrayList<>();
    List<CallRecordBean> mrecordList = new ArrayList<>();
    @BindView(R.id.fragment_call_record_real_recyclerView)
    RecyclerView mRecyclerView;

    private String queryTypeNum = "";
    private String callStatusNum = "";
    private String startTime = "";
    private String endTime = "";
    private CallRecordAdapter mAdapter;

    private boolean isPullToRef = true;
    private int pageNum = 1;
    private int loadMorepageNum = 1;
    private TextView mCountText;
    private View mHeaderView;
    private ScreenCallRecordPop mScreenCallRecordPop;
    private List<ConfigBean> configBeanList;
    private String proId = "";

    @Override
    protected int getLayout() {
        return R.layout.fragment_call_record;
    }

    @Override
    protected void initView() {
        //mRecyclerView.setVisibility(View.GONE);
    }

    private void addHeader() {
        mHeaderView = View.inflate(getActivity(), R.layout.item_call_record_header, null);
        mCountText = mHeaderView.findViewById(R.id.item_call_record_header_count);
        mAdapter.addHeaderView(mHeaderView);

        mHeaderView.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mAdapter = new CallRecordAdapter(mrecordList);
        mRecyclerView.setLayoutManager(manager);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        //上拉加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        isPullToRef = false;
                        //adapter.loadMoreEnd(true);//true is gone,false is visible

                        goSearch();


                    }
                });

            }
        });
        rootView.findViewById(R.id.btn_saixuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScreenCallRecordPop == null) {
                    mScreenCallRecordPop = new ScreenCallRecordPop(getContext(), configBeanList);
                    mScreenCallRecordPop.setBackgroundColor(0x0);
                    mScreenCallRecordPop.setmItemSelectListener(new ScreenCallRecordPop.IItemSelectListener<ConfigBean>() {
                        @Override
                        public void onSelect(ConfigBean data) {
                            getTodayData(data.getId());
                        }
                    });
                }
                mScreenCallRecordPop.showPopupWindow(rootView.findViewById(R.id.h_line));
            }
        });
    }

    @Override
    protected void initData() {
        addHeader();
        getProjectList();
        getTodayData(-1);
    }

    private void getTodayData(int configId) {
        PostRequest request = OkGo.post(Api.TODAY_CALL)
                .params("attacheTrue", SpUtil.getString(getContext(), GloableConstant.ATTACHETRUE));
        if (configId != -1) {
            request.params("proId", configId);
        }
        request.execute(new JsonCallback<BaseBean<TodayCallBean>>() {
            @Override
            public void onSuccess(BaseBean<TodayCallBean> todayCallBeanBaseBean, Call call, Response response) {
                if(todayCallBeanBaseBean.getResult() == 0){
                    TodayCallBean data = todayCallBeanBaseBean.getData();
                    tv_enter_num.setText(data.getInCount());
                    tv_out_num.setText(data.getOutCount());
                    tv_loss_num.setText(data.getMissCount());
                }
            }
        });
    }

    private void getProjectList() {
        OkGo.post(Api.CONFIG_INFO).execute(new JsonCallback<BaseBean<List<ConfigBean>>>() {
            @Override
            public void onSuccess(BaseBean<List<ConfigBean>> listBaseBean, Call call, Response response) {
                configBeanList = listBaseBean.getData();
            }
        });
    }


    @OnClick({R.id.fragment_call_record_real_startTime, R.id.fragment_call_record_real_endTime,
            R.id.fragment_call_record_real_queryType, R.id.fragment_call_record_real_callStatus,
            R.id.fragment_call_record_real_search, R.id.fragment_project_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_call_record_real_startTime:
                selectDate(mStartTime);
                break;
            case R.id.fragment_call_record_real_endTime:
                selectDate(mEndTime);
                break;
            case R.id.fragment_call_record_real_queryType:
                setQueryType();
                break;
            case R.id.fragment_call_record_real_callStatus:
                setCallStatus();
                break;
            case R.id.fragment_call_record_real_search:
                isPullToRef = true;
                goSearch();
                break;
            case R.id.fragment_project_name:
                ScreenCallRecordPop configPop = new ScreenCallRecordPop(getContext(), configBeanList);
                configPop.setBackgroundColor(0x0);
                configPop.setmItemSelectListener(new ScreenCallRecordPop.IItemSelectListener<ConfigBean>() {
                    @Override
                    public void onSelect(ConfigBean data) {
                        fragment_project_name.setText(data.getProName());
                        proId = data.getId()+"";
                    }
                });
                configPop.showPopupWindow(fragment_project_name);
                break;
        }
    }

    private void goSearch() {

        if (isPullToRef) {
            pageNum = 1;
            loadMorepageNum = 1;
        } else {
            loadMorepageNum++;
            pageNum = loadMorepageNum;
        }
        String realCaller = mRealCaller.getText().toString().trim();
        String realCalled = mRealCalled.getText().toString().trim();
        startTime = mStartTime.getText().toString().trim();
        endTime = mEndTime.getText().toString().trim();
        final ProgressHUD loadDialog = ProgressHUD.show(getContext(), getContext().getResources().getString(R.string.loading));

//        if (TextUtils.isEmpty(realCaller)) {
//            GlobalUtil.shortToast(getActivity(), "真实主叫不能为空");
//            return;
//        }
//
//        if (TextUtils.isEmpty(realCalled)) {
//            GlobalUtil.shortToast(getActivity(), "真实被叫不能为空");
//            return;
//        }
        String loginPhone = SpUtil.getString(getActivity(), GloableConstant.LOGINPHONE);
        OkGo.post(Api.CALLRECORD_QUERYNEW)
                .params("caller", realCaller)
                .params("called", realCalled)
                .params("proId",proId)
                .params("createStartTime", startTime)
                .params("createEndTime", endTime)
                .params("setTime", queryTypeNum)
                .params("telephoneStatus", callStatusNum)
                .params("attacheTrue", loginPhone)
                .params("pageNumber", pageNum)
                .params("pageSize", 15)
                .execute(new JsonCallback<BaseBean<CallRecordListBean>>() {
                    @Override
                    public void onSuccess(BaseBean<CallRecordListBean> baseBean, Call call, Response response) {
                        loadDialog.dismiss();
                        if (baseBean != null) {
                            if (baseBean.getResult() == 0) {
                                mHeaderView.setVisibility(View.VISIBLE);
                                int total = baseBean.getData().getTotal();
                                mCountText.setText(total + "");
                                List<CallRecordBean> data = baseBean.getData().getRows();


                                if (isPullToRef) {
                                    //处理一下数据给数据分类
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.setNewData(data);
                                    mAdapter.setEnableLoadMore(true);
                                } else {
                                    if (data.size() < 15) {
                                        mAdapter.loadMoreEnd(true);
                                    }
                                    mAdapter.addData(data);
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.loadMoreComplete();
                                    //adapter.setEnableLoadMore(false);
                                    //adapter.loadMoreEnd(true);

                                }


                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        loadDialog.dismiss();
                    }
                });
    }

    private void selectDate(final TextView textView) {


//        DateTimePicker picker = new DateTimePicker(getActivity(), DateTimePicker.HOUR_24);
//        picker.setDateRangeStart(2017, 2, 27);
//        picker.setDateRangeEnd(2050, 11, 11);
//        picker.setTimeRangeStart(0, 0);
//        picker.setTimeRangeEnd(23, 59);
//        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
//            @Override
//            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
//                //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
//                String time = year + "-" + month + "-" + day + " " + hour + ":" + minute+":00";
//                textView.setText(time);
//            }
//        });
//        picker.show();
        MDatePickerDialog dialog = new MDatePickerDialog.Builder(getContext())
                .setCanceledTouchOutside(true)
                .setGravity(Gravity.BOTTOM)
                .setSupportTime(true)
                .setTwelveHour(false)
                .setCanceledTouchOutside(false)
                .setOnDateResultListener(new MDatePickerDialog.OnDateResultListener() {
                    @Override
                    public void onDateResult(long date) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(date);
                        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                        dateFormat.applyPattern("yyyy-MM-dd HH:mm");
                        textView.setText(dateFormat.format(new Date(date)) + ":00");
//                        Toast.makeText(getContext(), dateFormat.format(new Date(date)), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        dialog.show();

    }


    private void setQueryType() {
        mlist.clear();
        mlist.add(new QueryTypeBean("", "默认"));
        mlist.add(new QueryTypeBean("0", "上班时间"));
        mlist.add(new QueryTypeBean("1", "下班时间"));
        QueryTypePop mPopUp = new QueryTypePop(getActivity(), mlist);
        mPopUp.setBackgroundColor(0x0);
        mPopUp.setOnQueryTypeSelectedListener(new QueryTypePop.OnQueryTypeSelectedListener() {
            @Override
            public void onItemClicked(QueryTypeBean bean) {
                mQueryType.setText(bean.getType());
                queryTypeNum = bean.getNum();
            }
        });
        mPopUp.showPopupWindow(mQueryType);
    }

    private void setCallStatus() {
        mlist.clear();
        mlist.add(new QueryTypeBean("", "默认"));
        mlist.add(new QueryTypeBean("1", "专员未接听"));
        mlist.add(new QueryTypeBean("2", "患者未接听"));
        mlist.add(new QueryTypeBean("3", "回拨成功"));
        mlist.add(new QueryTypeBean("4", "回拨不成功"));
        mlist.add(new QueryTypeBean("5", "外呼成功"));
        mlist.add(new QueryTypeBean("6", "呼入成功"));
        QueryTypePop mPopUp = new QueryTypePop(getActivity(), mlist);
        mPopUp.setOnQueryTypeSelectedListener(new QueryTypePop.OnQueryTypeSelectedListener() {
            @Override
            public void onItemClicked(QueryTypeBean bean) {
                mCallStatus.setText(bean.getType());
                callStatusNum = bean.getNum();
            }
        });
        mPopUp.showPopupWindow(mCallStatus);
    }


}
