package com.it.fan.mycall.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.it.fan.mycall.R;
import com.it.fan.mycall.activity.NewContactActivity;
import com.it.fan.mycall.adapter.ContactAdapter;
import com.it.fan.mycall.bean.ContactBean;
import com.it.fan.mycall.view.SuspensionBrandDecoration;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ContactsFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    private ContactAdapter adapter;
    private SuspensionBrandDecoration mDecoration;
    private List<ContactBean> mList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_contacts_layout;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        mDecoration = new SuspensionBrandDecoration(getContext(), mList);
//        mDecoration.setColorTitleBg(getResources().getColor(R.color.gray_F8));
//        mDecoration.setColorTitleFont(getResources().getColor(R.color.black_13));
        recyclerView.addItemDecoration(mDecoration);


        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
//                .setmSourceDatas(mlist)
                .setmLayoutManager(linearLayoutManager);//设置RecyclerView的LayoutManager
        try {
            Field field = indexBar.getClass().getDeclaredField("mPaint");
            field.setAccessible(true);
            Paint mPaint = (Paint) field.get(indexBar);
            mPaint.setColor(Color.parseColor("#1c87e5"));
            indexBar.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewContactActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }
}
