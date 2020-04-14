package com.it.fan.mycall.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.BindBean;
import com.it.fan.mycall.bean.VirtualPhoneBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.view.dialog.SelectVirtualNumDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by fan on 2019/6/7.
 */

public class CallUtil {

    public static void call(Context context, String phoneNum,String attachVirtual,int configId) {
//        bindPhone(context,phoneNum,attachVirtual);
        boundNew(context,phoneNum,attachVirtual,configId);
    }

    private static void bindPhone(final Context context, final String phoneNum,final String attachVirtual) {
        String attachTure = SpUtil.getString(context, GloableConstant.ATTACHETRUE);
//        final String attachVirtual = SpUtil.getString(context, GloableConstant.ATTACHEVITRUAL);

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
                            context.startActivity(intent);
                        }
                    }
                });

    }

    public static void boundNew(final Context context, final String phoneNum,final String attachVirtual,int configId){
        String attachTure = SpUtil.getString(context, GloableConstant.ATTACHETRUE);
        OkGo.post(Api.NEWBIND_VIRTUAL_PHONE)
                .params("caller",attachTure)
                .params("called",phoneNum)
                .params("called_show",attachVirtual)
                .params("configId",configId)
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
                            context.startActivity(intent);
                        }
                    }
                });

    }

    public static void callDirectly(Context context, String phoneNum) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Toast.makeText(context,"请先允许拨号权限",Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(intent);
    }

    public static void showSelectVirtualDialog(final FragmentActivity context, final String phoneNum){
        String attachTure = SpUtil.getString(context, GloableConstant.ATTACHETRUE);
        OkGo.post(Api.VIRTUAL_PHONE_LIST)
                .params("attacheTrue",attachTure)
                .execute(new JsonCallback<BaseBean<List<VirtualPhoneBean>>>() {
                    @Override
                    public void onSuccess(BaseBean<List<VirtualPhoneBean>> listBaseBean, Call call, Response response) {
                        List<VirtualPhoneBean> datas = listBaseBean.getData();
                        if(datas != null && datas.size() > 0){
                            if(datas .size() == 1){
                                CallUtil.call(context,phoneNum,datas.get(0).getAttacheVitrual(),datas.get(0).getConfigId());
                            } else {
                                SelectVirtualNumDialog dialog = new SelectVirtualNumDialog();
                                Bundle bundle = new Bundle();
                                bundle.putString("phoneNum",phoneNum);
                                bundle.putSerializable("list", (Serializable) datas);
                                dialog.setArguments(bundle);
                                dialog.show(context.getSupportFragmentManager(),"d");
                            }
                        }
                    }
                });
    }


}
