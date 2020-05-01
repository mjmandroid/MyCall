package com.it.fan.mycall.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.KeyBoardAdapter;
import com.it.fan.mycall.bean.QueryTypeBean;
import com.it.fan.mycall.util.CallUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fan on 2019/6/1.
 */

public class VirtualKeyboardView extends RelativeLayout implements View.OnClickListener {

    private ArrayList<Map<String, String>> valueList;
    private GridView gridView;
    private Context context;
    private EditText mEditText;
    private RelativeLayout mDelete;
    private ImageView mBack;
    private ImageView mCall;
    private View view_top;

    public VirtualKeyboardView(Context context) {
        this(context,null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);
        valueList = new ArrayList<>();

        gridView = (GridView) view.findViewById(R.id.layout_virtual_keyboard_gridView);
        mEditText =(EditText)view.findViewById(R.id.layout_virtual_keyboard_editText);
        mDelete = view.findViewById(R.id.layout_virtual_keyboard_delete);
        mCall = view.findViewById(R.id.layout_virtual_keyboard_call);
        mBack = view.findViewById(R.id.layout_virtual_keyboard_back);
        view_top= view.findViewById(R.id.view_top);
        mDelete.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mCall.setOnClickListener(this);
        view_top.setOnClickListener(this);
        initValueList();

        setupView();

        addView(view);
        initNoWindowKey();
        initListener();

    }

    private void initNoWindowKey() {
        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            mEditText.setShowSoftInputOnFocus(false);
        } else {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mEditText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String amount = mEditText.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                mEditText.setText(amount);

                Editable ea = mEditText.getText();
                mEditText.setSelection(ea.length());
            }
        });

    }

    private void initValueList() {
        //初始化按钮上显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String,String> keyMap = new HashMap<>();
            if (i<10){
                keyMap.put("name",String.valueOf(i));
            }else if (i == 10) {
                keyMap.put("name", "*");
            } else if (i == 11) {
                keyMap.put("name", String.valueOf(0));
            } else if (i == 12) {
                keyMap.put("name", "#");
            }

            valueList.add(keyMap);
        }

    }


    private void setupView() {
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
    }

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    public GridView getGridView() {
        return gridView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_virtual_keyboard_delete:
                delete();
                break;
            case R.id.layout_virtual_keyboard_back:
                if (mListener!=null)mListener.onBack();
                break;

            case R.id.layout_virtual_keyboard_call:
                call();
                break;
            case R.id.view_top:
                if (mListener!=null)mListener.onBack();
                break;
        }
    }

    private void call() {
        String amount = mEditText.getText().toString().trim();
        if (amount.length() > 0) {
            CallUtil.showSelectVirtualDialog((FragmentActivity) getContext(),amount);
        }else {
            Toast.makeText(context,"请输入电话号码",Toast.LENGTH_SHORT).show();
        }
    }

    private void delete() {
        String amount = mEditText.getText().toString().trim();
        if (amount.length() > 0) {
            amount = amount.substring(0, amount.length() - 1);
            mEditText.setText(amount);

            Editable ea = mEditText.getText();
            mEditText.setSelection(ea.length());
        }
    }


    public interface OnVirtualKeyBoardListener{
        void onBack();
    }

    private OnVirtualKeyBoardListener mListener;


    public void setOnVirtualKeyBoardListener(OnVirtualKeyBoardListener listener){
        mListener = listener;
    }
}
