package com.first.hdz.qq.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.first.hdz.qq.bean.BaseData;
import com.first.hdz.qq.bean.BaseJson;
import com.first.hdz.qq.bean.LoginInfo;
import com.first.hdz.qq.bean.Protocol;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * created by hdz
 * on 2018/9/25
 */
public class QQService {

    private static final String TAG = "QQService";

    private static QQService mService;

    private static final int READ_TIMEOUT = 30;
    private static final int CONNECT_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    public static final String TYPE_STRING = "String";
    public static final String TYPE_BOOLEAN = "Boolean";
    public static final String TYPE_STREAM = "Stream";

    private OkHttpClient mOkClient;

    private Retrofit mRetrofit;

    private Retrofit.Builder mRetrofitBuilder;

    private QQService() {
        mOkClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)   //错误重连
                .build();
    }


    public static QQService Init(String type) {
        if (mService == null) {
            synchronized (QQService.class) {
                if (mService == null) {
                    mService = new QQService();
                }
            }
        }
        mService.setConvertType(type);
        return mService;
    }

    private void setConvertType(String type) {
        mRetrofitBuilder = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        switch (type) {
            case TYPE_BOOLEAN:
                mRetrofitBuilder.addConverterFactory(new CustomConvertFactory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new BooleanResponseBodyConverter<BaseJson>() {

                            @Override
                            public BaseJson convert(ResponseBody value) throws IOException {
                                String result = value.string();
                                return JSON.parseObject(result, new TypeReference<BaseJson<Boolean>>() {
                                });
                            }
                        };
                    }
                });
                break;
            case TYPE_STREAM:
                mRetrofitBuilder.addConverterFactory(new CustomConvertFactory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new StreamResponseBodyConverter<InputStream>() {
                            @Override
                            public InputStream convert(ResponseBody value) throws IOException {
                                return value.byteStream();
                            }
                        };
                    }
                });
                break;
            case TYPE_STRING:
                mRetrofitBuilder.addConverterFactory(new CustomConvertFactory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new StringResponseBodyConverter<String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
//                                JSONObject dataObj = JSON.parseObject("data");
//                                dataObj.toJSONString();
                                return value.string();
                            }
                        };
                    }
                });
                break;
        }
        mRetrofit = mRetrofitBuilder.client(mOkClient).build();
    }

    public <T> T getService(Class<T> service) {
        return mRetrofit.create(service);
    }
}
