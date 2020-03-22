package com.it.fan.mycall.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.it.fan.mycall.bean.BindBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fan on 2019/6/7.
 */

public class CallUtil {

    public static void call(Context context, String phoneNum) {
        bindPhone(context,phoneNum);

    }

    private static void bindPhone(final Context context, final String phoneNum) {
        final String attachTure = SpUtil.getString(context, GloableConstant.ATTACHETRUE);
        final String attachVirtual = SpUtil.getString(context, GloableConstant.ATTACHEVITRUAL);

        OkGo.post(Api.BINDCALL)
                .params("caller",attachTure)
                .params("called",phoneNum)
                .params("called_show",attachVirtual)
                .execute(new JsonCallback<BindBean>() {
                    @Override
                    public void onSuccess(BindBean bindBean, Call call, Response response) {
                        if (bindBean.getCode()==0){
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+attachVirtual));
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                Toast.makeText(context,"请先允许拨号权限",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            LogUtils.loge("onSuccess",attachVirtual+"-"+attachTure+"-"+phoneNum );
                            context.startActivity(intent);
                        }
                    }
                });

    }
}
