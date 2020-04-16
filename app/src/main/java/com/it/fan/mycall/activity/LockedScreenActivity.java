package com.it.fan.mycall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.it.fan.mycall.R;

public class LockedScreenActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private TextView tv_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_locked_screen_layout);
        tv_content = findViewById(R.id.content);
        String content = getIntent().getStringExtra("content");
        if(!TextUtils.isEmpty(content)){
            tv_content.setText(content);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
        findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                finish();
            }
        });
    }

}
