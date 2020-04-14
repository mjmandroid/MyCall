package com.it.fan.mycall.view;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.tencent.bugly.crashreport.CrashReport;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by fan on 2019/6/4.
 */

public class MyApp extends Application {

    private static MyApp mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
           /*配置okgo*/
        OkGo.init(this);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);

        CrashReport.initCrashReport(getApplicationContext(), "49c70f5aca", false,strategy);
        //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
        OkGo.getInstance()
                //如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)

                //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(3)

                //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//                .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore());        //cookie持久化存储，如果cookie不过期，则一直有效
//
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkGo.getInstance().getOkHttpClientBuilder().addInterceptor(loggingInterceptor);

    }

    public static MyApp getInstance(){
        return mInstance;
    }
}
