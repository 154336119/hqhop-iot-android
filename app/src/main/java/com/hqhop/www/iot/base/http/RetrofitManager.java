package com.hqhop.www.iot.base.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.util.NetWorkUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by allen on 2017/7/5.
 */

public class RetrofitManager {

//    private volatile RetrofitManager mInstance;

    private OkHttpClient mClient;

    private Retrofit retrofit;

    private static final int NET_MAX = 30; //30秒  有网超时时间

    private static final int NO_NET_MAX = 60 * 60 * 24 * 7; //7天 无网超时时间

    private RetrofitManager(final Context context) {
        //应用程序拦截器
        Interceptor mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.e("TAG", "拦截 网络 缓存");
                Request request = chain.request();
                if (!NetWorkUtils.networkIsAvailable(context)) {//判断网络状态 无网络时
                    Log.e("TAG", "无网~~ 缓存");
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .removeHeader("Pragma")
//                            .header("Cache-Control", "private, only-if-cached, max-stale=" + NO_NET_MAX)
                            .build();
                } else {//有网状态
                    Log.e("TAG", "有网~~ 缓存");
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .removeHeader("Pragma")
//                            .header("Cache-Control", "private, max-age=" + NET_MAX)//添加缓存请求头
                            .build();
                }
                return chain.proceed(request);
            }
        };

        //网络拦截器
        Interceptor mNetInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.e("TAG", "拦截 应用 缓存");

                Request request = chain.request();
                if (!NetWorkUtils.networkIsAvailable(context)) {//判断网络状态 无网络时
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "private, only-if-cached, max-stale=" + NO_NET_MAX)
                            .addHeader("Authorization", "Basic ZW1jczplbWNzc2VjcmV0")
                            .build();
                } else {
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "private, max-age=" + NET_MAX)//添加缓存请求头
                            .addHeader("Authorization", "Basic ZW1jczplbWNzc2VjcmV0")
                            .build();
                }
                return chain.proceed(request);
            }
        };

        Interceptor basicParam = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                HttpUrl httpUrl = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
//                        .addQueryParameter("clientId", "emcs")
                        .addQueryParameter("client_id", "fbed1d1b4b1449daa4bc49397cbe2350")
//                        .addQueryParameter("client_secret", "cmessecret")
                        .addQueryParameter("client_secret", "fbed1d1b4b1449daa4bc49397cbe2350")
                        .build();
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(httpUrl)
                        .build();
                return chain.proceed(newRequest);
            }
        };

        //默认参数拦截器
        Interceptor mParamInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                HttpUrl httpUrl = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
                        .addQueryParameter("access_token", App.token)
                        .build();
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(httpUrl)
                        .build();
                return chain.proceed(newRequest);
            }
        };

        // Log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File mFile = new File(context.getCacheDir() + "http_cache");//储存目录
        long maxSize = 10 * 1024 * 1024; // 10 MB 最大缓存数
        Cache mCache = new Cache(mFile, maxSize);

        if (TextUtils.isEmpty(App.token)) {
            mClient = new OkHttpClient.Builder()
//                    .addInterceptor(mInterceptor)//应用程序拦截器
                    .addInterceptor(loggingInterceptor)//Log拦截器
                    .addInterceptor(basicParam)//默认参数拦截器
                    .addNetworkInterceptor(mNetInterceptor)//网络拦截器
//                    .addNetworkInterceptor(new StethoInterceptor())// 添加stetho拦截器
//                    .cache(mCache)//添加缓存
                    .build();
        } else {
            mClient = new OkHttpClient.Builder()
//                    .addInterceptor(mInterceptor)//应用程序拦截器
                    .addInterceptor(loggingInterceptor)//Log拦截器
                    .addInterceptor(mParamInterceptor)//默认参数拦截器
                    .addInterceptor(basicParam)//默认参数拦截器
                    .addNetworkInterceptor(mNetInterceptor)//网络拦截器
//                    .addNetworkInterceptor(new StethoInterceptor())// 添加stetho拦截器
//                    .cache(mCache)//添加缓存
                    .build();
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Common.BASE_URL)
                    .client(mClient)//添加OK
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    public static RetrofitManager getInstance(Context context) {
        SharedPreferences sp = context.getSharedPreferences("url_config", Context.MODE_PRIVATE);
        Common.BASE_URL = sp.getString("BASE_URL", Common.BASE_URL);
        RetrofitManager mInstance;
//        if (mInstance == null) {
        synchronized (RetrofitManager.class) {
//            if (mInstance == null)
            mInstance = new RetrofitManager(context);
        }
//        }
        return mInstance;
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }
}
