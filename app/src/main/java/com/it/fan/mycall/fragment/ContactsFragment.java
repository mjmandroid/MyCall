package com.it.fan.mycall.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.it.fan.mycall.R;
import com.it.fan.mycall.activity.NewContactActivity;
import com.it.fan.mycall.adapter.ContactAdapter;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.ConfigBean;
import com.it.fan.mycall.bean.ContactBean;
import com.it.fan.mycall.bean.ContactBeanWrapper;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.view.SuspensionBrandDecoration;
import com.lzy.okgo.OkGo;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class ContactsFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.et_search)
    EditText et_search;
    private ContactAdapter adapter;
    private SuspensionBrandDecoration mDecoration;
    private List<ContactBean> mList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayout() {
        return R.layout.fragment_contacts_layout;
    }

    @Override
    protected void initView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        mDecoration = new SuspensionBrandDecoration(getContext(), mList);
        mDecoration.setColorTitleBg(Color.parseColor("#ffebebeb"));
        mDecoration.setColorTitleFont(Color.parseColor("#333333"));
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

    public void selectItem(ConfigBean data){
        if(data.getId() == -1){
            mDecoration.setmDatas(mList);
            adapter.setNewData(mList);
        } else {
            List<ContactBean> tempList = new ArrayList<>();
            for (ContactBean bean : mList) {
                if(!TextUtils.isEmpty(bean.getProId())){
                    if(bean.getProId().equals(data.getId()+"")){
                        tempList.add(bean);
                    }
                }
            }
            indexBar.setmSourceDatas(tempList)
                    .invalidate();
            mDecoration.setmDatas(tempList);
            adapter.setNewData(tempList);
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
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search_str = et_search.getText().toString().trim();
                if(!TextUtils.isEmpty(search_str)){
                    List<ContactBean> data = adapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        ContactBean bean = data.get(i);
                        String content = bean.getUserName()+bean.getUserLabel()+bean.getUserRemark()+bean.getProName();
                        if(content.contains(search_str)){
                            linearLayoutManager.scrollToPositionWithOffset(i,0);
                            break;
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mList.clear();
        OkGo.post(Api.CONTACT_LIST)
                .params("attacheTrue", SpUtil.getString(getContext(), GloableConstant.ATTACHETRUE))
                .execute(new JsonCallback<BaseBean<ContactBeanWrapper>>() {
                    @Override
                    public void onSuccess(BaseBean<ContactBeanWrapper> contactBeanWrapper, Call call, Response response) {
                        if(contactBeanWrapper.getResult() == 0){
                            List<ContactBean> rows = contactBeanWrapper.getData().getRows();

//                            String[] arr = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
//
//                            for (int i = 20; i > 0 ; i--) {
//                                ContactBean bean = new ContactBean();
//                                bean.setUserName(arr[i]+"哈哈哈");
//                                rows.add(bean);
//                            }
                            if(rows != null && rows.size() > 0){

                                mList.addAll(rows);
                                indexBar.setmSourceDatas(mList)
                                        .invalidate();
                                mDecoration.setmDatas(mList);
                                adapter.setNewData(mList);
                            }
                        }
                    }
                });
    }

    @Override
    protected void initData() {

    }
}
