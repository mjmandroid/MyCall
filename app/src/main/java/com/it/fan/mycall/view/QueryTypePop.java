package com.it.fan.mycall.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.QueryTypeBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by fan on 2019/6/7.
 */

public class QueryTypePop extends BasePopupWindow {

    private RecyclerView mRecyclerView;

    private String[] sortStrs = {"默认","上班时间","下班时间"};
    private Context mContext;
    private int mSortPosition;

    private MyAdapter mAdapter;
    private List<QueryTypeBean> mlist;

    public QueryTypePop(Context context,List<QueryTypeBean> mlist) {
        super(context);
        this.mlist = mlist;
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.popupwindow_case_sort_recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);

        //List<String> mlist = Arrays.asList(sortStrs);
        mAdapter = new MyAdapter(mlist);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<QueryTypeBean> data = mAdapter.getData();

                if (mListener !=null)
                    mListener.onItemClicked(data.get(position));
                dismiss();
            }
        });
        findViewById(R.id.view_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_case_sort);
    }


    private class MyAdapter extends BaseQuickAdapter<QueryTypeBean,BaseViewHolder> {
        public MyAdapter(List<QueryTypeBean> data) {
            super(R.layout.item_case_sort, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, QueryTypeBean item) {

            helper.setText(R.id.item_case_sort_textView,item.getType());
        }
    }


    public interface OnQueryTypeSelectedListener{
        void onItemClicked(QueryTypeBean bean);
    }

    private OnQueryTypeSelectedListener mListener;


    public void setOnQueryTypeSelectedListener(OnQueryTypeSelectedListener listener){
        mListener = listener;
    }
}
