package com.it.fan.mycall.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.QueryTypeBean;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.StringUtil;
import com.lzy.okgo.OkGo;

import java.util.Arrays;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by fan on 2019/6/7.
 */

public class ApplyForPop extends BasePopupWindow {

    private RecyclerView mRecyclerView;

    private Context mContext;
    private int mSortPosition;

    private MyAdapter mAdapter;
    private List<QueryTypeBean> mlist;

    public ApplyForPop(Context context) {
        super(context);
        this.mlist = mlist;
        initRecyclerView();


    }



    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.popupwindow_case_sort_recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);

        List<String> mlist = Arrays.asList(StringUtil.sortStrs);
        mAdapter = new MyAdapter(mlist);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //List<String> data = mAdapter.getData();

                if (mListener !=null)
                    mListener.onItemClicked(StringUtil.sortStrs[position],position);
                dismiss();
            }
        });
    }




    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_case_sort);
    }


    private class MyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
        public MyAdapter(List<String> data) {
            super(R.layout.item_case_sort, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

            helper.setText(R.id.item_case_sort_textView,item);
        }
    }


    public interface OnQueryTypeSelectedListener{
        void onItemClicked(String bean,int position);
    }

    private OnQueryTypeSelectedListener mListener;


    public void setOnQueryTypeSelectedListener(OnQueryTypeSelectedListener listener){
        mListener = listener;
    }
}
