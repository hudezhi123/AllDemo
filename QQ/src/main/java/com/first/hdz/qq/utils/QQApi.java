package com.first.hdz.qq.utils;

import com.first.hdz.qq.bean.BaseJson;
import com.first.hdz.qq.bean.LoginInfo;
import com.first.hdz.qq.bean.Protocol;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * created by hdz
 * on 2018/9/25
 */
public interface QQApi {

    @FormUrlEncoded
    @POST(Constants.REGISTER)
    public Observable<BaseJson<Boolean>> Register(@Field("userName") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.LOGIN)
    public Observable<BaseJson<LoginInfo>> Login(@FieldMap HashMap<String, Object> params, @Field("userName") String userName, @Field("password") String password);

    public Observable<BaseJson<Boolean>> ForgotPassword(@Field("userName") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.PROTOCOL)
    public Observable<BaseJson<Protocol>> GetProtocol(@Field("type") int type);

    public void getMessageList();
}
