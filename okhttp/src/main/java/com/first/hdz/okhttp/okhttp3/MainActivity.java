package com.first.hdz.okhttp.okhttp3;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.first.hdz.okhttp.R;
import com.first.hdz.okhttp.okhttp3.Person;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class MainActivity extends AppCompatActivity {

    private static final String URL_PATH = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AsyncGet();
//        SyncGet();
//        SyncPost();
//        JsonPost();
//        FilePost();
//        Multipart();
    }

    private void setConfig() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, null, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)      //读超时
                .writeTimeout(5, TimeUnit.SECONDS)     //写超时
                .connectTimeout(5, TimeUnit.SECONDS)   //连接超时
                // 设置https配置，此处忽略了所有证书
                .sslSocketFactory(sslcontext.getSocketFactory(), null)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url();
                        return chain.proceed(request);
                    }
                })
                //验证服务器的证书域名。在https握手期间，如果URL的主机名和服务器的标识主机不相符则验证机制可以回调此链接
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return false;
                    }
                })
                .retryOnConnectionFailure(true)   //设置是否断线重连
                .build();
    }


    private void Header() {
        OkHttpClient client = new OkHttpClient();
        Header header = new Header("", "");
        Headers headers = new Headers.Builder()
                .add("", "")
                .add("", "")
                .add("", "")
                .build();
        Request request = new Request.Builder()
                .url("http://baidu.com")
                .post(new FormBody.Builder().build())
                .addHeader("", "")
                .header("", "")
                .headers(headers)
                .build();
    }

    private void Multipart() {
        File file = new File("path");
        OkHttpClient client = new OkHttpClient();
        MultipartBody multi = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "胡德志")
                .addFormDataPart("sex", "男")
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("File/*"), file))
                .build();
        final Request request = new Request.Builder()
                .url(URL_PATH)
                .post(multi)
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("Response-----", "exception: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Response-----", "Response.code()==" + response.code());
                Log.i("Response-----", "response.message()==" + response.message());
                Log.i("Response-----", "res==" + response.body().string());
            }
        });
    }

    private void FilePost() {
        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("File/*");
        File file = new File("path");
        RequestBody requestBody = RequestBody.create(type, file);
        Request request = new Request.Builder()
                .url(URL_PATH)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Response-----", "exception: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Response-----", "Response.code()==" + response.code());
                Log.i("Response-----", "response.message()==" + response.message());
                Log.i("Response-----", "res==" + response.body().string());
            }
        });
    }

    /**
     * 参数传递Json对象
     */
    private void JsonPost() {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        String jsonStr = new Gson().toJson(new Person("胡德志", "男"));
        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(URL_PATH)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Response-----", "Response.code()==" + response.code());
                Log.i("Response-----", "response.message()==" + response.message());
                Log.i("Response-----", "res==" + response.body().string());
            }
        });
    }

    /**
     * 同步请求
     */
    private void AsyncGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(URL_PATH)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i("Response-----", "Response.code()==" + response.code());
                        Log.i("Response-----", "response.message()==" + response.message());
                        Log.i("Response-----", "res==" + response.body().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 异步请求
     */
    private void SyncGet() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_PATH)
                .build();
        client.newCall(request).enqueue(new Callback() {

            //以下方法在子线程中实现
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Response-----", "Response.code()==" + response.code());
                Log.i("Response-----", "response.message()==" + response.message());
                Log.i("Response-----", "res==" + response.body().string());
            }
        });
    }

    /**
     * 同步Post请求
     */
    private void SyncPost() {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("username", "刘德华");
        Request request = new Request.Builder()
                .url(URL_PATH)
                .post(formBody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Response-----", "Response.code()==" + response.code());
                Log.i("Response-----", "response.message()==" + response.message());
                Log.i("Response-----", "res==" + response.body().string());
            }
        });
    }


}
