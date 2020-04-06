package com.it.fan.mycall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.it.fan.mycall.R;
import com.it.fan.mycall.activity.CallDetailActivity;
import com.it.fan.mycall.adapter.MyAllCallAdapter;
import com.it.fan.mycall.base.BaseDialog;
import com.it.fan.mycall.bean.AllCallBean;
import com.it.fan.mycall.bean.BaseAllCallBean;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.CallUtil;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.util.Utility;
import com.it.fan.mycall.view.VirtualKeyBoardPop;
import com.it.fan.mycall.view.dialog.SelectVirtualNumDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static android.R.attr.data;

/**
 * Created by fan on 2019/6/2.
 */

public class AllCallFragment extends BaseFragment {
    @BindView(R.id.fragment_all_call_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_all_call_swipeLayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_all_call_showNum)
    ImageView mShowNum;

    private boolean isPullToRef = true;
    private int pageNum =1;
    private int loadMorepageNum =1;

    private List<AllCallBean> mlist = new ArrayList<>();
    private MyAllCallAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_all_call;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isPullToRef = true;

                mAdapter.setEnableLoadMore(false);
                getData();
            }
        });
        //取消动画效果，防止画面闪烁
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mAdapter = new MyAllCallAdapter(mlist);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);


        //上拉加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        isPullToRef = false;
                        mSwipeRefreshLayout.setEnabled(false);
                        //adapter.loadMoreEnd(true);//true is gone,false is visible

                        getData();


                    }
                });

            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<AllCallBean> data = mAdapter.getData();
                if (data!=null){
                    AllCallBean allCallBean = data.get(position);
                    CallUtil.call(getActivity(),allCallBean.getPatientPhone());
                }
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AllCallBean allCallBean = mAdapter.getData().get(position);
                String patientPhone = allCallBean.getPatientPhone();
                if (view.getId() == R.id.item_all_call_detail){
                    Intent intent = new Intent(getActivity(), CallDetailActivity.class);
                    intent.putExtra(GloableConstant.PATIENTPHONE,patientPhone);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void getData() {
        if (isPullToRef){
            pageNum = 1;
            loadMorepageNum=1;
        }else {
            loadMorepageNum++;
            pageNum = loadMorepageNum;
        }
        String loginPhone = SpUtil.getString(getActivity(), GloableConstant.LOGINPHONE);
        //loginPhone = "18310676445";
        OkGo.post(Api.ALLCALL)
                .params("attachePhone",loginPhone)
                .params("pageNumber",pageNum)
                .params("pageSize",15)
                .execute(new JsonCallback<BaseBean<BaseAllCallBean>>() {
                    @Override
                    public void onSuccess(BaseBean<BaseAllCallBean> baseBean, Call call, Response response) {
                        if (baseBean !=null){
                            if (baseBean.getResult() == 0){
                                mlist = baseBean.getData().getRows();
                                //List<AllCallBean> data = baseBean.getRows();
                                if (mAdapter != null && mSwipeRefreshLayout !=null){
                                    if (isPullToRef){
                                        mAdapter.getData().clear();
                                        //处理一下数据给数据分类
                                        mAdapter.setNewData(mlist);
                                        mAdapter.notifyDataSetChanged();
                                        mAdapter.setEnableLoadMore(true);
                                    }else {
                                        if (mlist.size() < 15) {
                                            mAdapter.loadMoreEnd(true);
                                        }
                                        mAdapter.addData(mlist);
                                        mAdapter.notifyDataSetChanged();
                                        mAdapter.loadMoreComplete();
                                        //adapter.setEnableLoadMore(false);
                                        //adapter.loadMoreEnd(true);
                                        mSwipeRefreshLayout.setEnabled(true);
                                    }

                                }
                            }
                        }
                        mSwipeRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mSwipeRefreshLayout.finishRefresh();
                    }
                });
    }

    @OnClick(R.id.fragment_all_call_showNum)
    public void onViewClicked() {
        VirtualKeyBoardPop pop = new VirtualKeyBoardPop(getActivity());
        pop.showPopupWindow();
    }


    @Override
    public void onResume() {
        super.onResume();
        isPullToRef = true;
        mAdapter.setEnableLoadMore(false);
        getData();

    }
}
