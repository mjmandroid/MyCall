package com.it.fan.mycall.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.it.fan.mycall.bean.MessageCountBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.BrageUtil;
import com.it.fan.mycall.util.SpUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fan on 2019/6/7.
 */

public class MessageCountService extends Service {

    private Timer mMessageTimer;
    private MessageTimerTask mMessageTimerTask;
    private String phoneNum;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /*mMessageTimer = new Timer(true);
        mMessageTimerTask = new MessageTimerTask();
        mMessageTimer.schedule(mMessageTimerTask, 1000, 1000*60);

        phoneNum = SpUtil.getString(this, GloableConstant.ATTACHETRUE);*/
    }



    /**
     * 记时器
     */
     class MessageTimerTask extends TimerTask {
        public void run() {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("attachePhone",phoneNum)
                    .build();
            Request request = new Request.Builder()
                    .url(Api.MISSCOUNT)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    MessageCountBean messageCountBean = gson.fromJson(json, MessageCountBean.class);
                    if (messageCountBean !=null){
                        if (messageCountBean.getResult()==0){
                            int count = messageCountBean.getData();

                            BrageUtil.sendToXiaoMi(MessageCountService.this,count);
                        }
                    }
                }
            });
        }
    }
}
