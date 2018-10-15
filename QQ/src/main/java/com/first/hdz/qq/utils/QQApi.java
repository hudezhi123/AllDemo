package com.first.hdz.qq.utils;

import com.first.hdz.qq.bean.BaseJson;
import com.first.hdz.qq.bean.LoginInfo;
import com.first.hdz.qq.bean.Protocol;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;


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
    public Observable<String> Login(@FieldMap HashMap<String, Object> params, @Field("userName") String userName, @Field("password") String password);

    public Observable<BaseJson<Boolean>> ForgotPassword(@Field("userName") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.PROTOCOL)
    public Observable<String> GetProtocol(@Field("type") int type);


    @FormUrlEncoded
    @POST("apk/{apkName}")

    @Streaming
    public Observable<ResponseBody> DownloadApk(@Path("apkName") String apkName, @Header("Range") String range);

    public void getMessageList();
}
