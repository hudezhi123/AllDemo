package com.first.hdz.qq.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.BaseJson;
import com.first.hdz.qq.bean.Protocol;
import com.first.hdz.qq.utils.Constants;
import com.first.hdz.qq.utils.QQApi;
import com.first.hdz.qq.utils.QQService;
import com.first.hdz.qq.utils.StringUtils;
import com.first.hdz.qq.view.base.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProtocolActivity extends BaseActivity implements View.OnClickListener {

    private int type;
    private ImageView imgBack;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        type = getIntent().getIntExtra(Constants.DATA_KEY, 0);
        init();
        getProtocol();
    }

    private void init() {
        initView();
    }

    private void initView() {
        imgBack = findViewById(R.id.img_protocol_back);
        mWebView = findViewById(R.id.webview_protocol);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        imgBack.setOnClickListener(this);
    }

    private void getProtocol() {
        Observable<String> observable = QQService.Init(QQService.TYPE_STRING).getService(QQApi.class).GetProtocol(1);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String jsonResult) {
                        BaseJson<Protocol> baseJson = JSON.parseObject(jsonResult, new TypeReference<BaseJson<Protocol>>() {
                        });
                        if (baseJson != null) {
                            Protocol protocol = baseJson.getData();
                            if (!StringUtils.isEmpty(protocol.getUrl())) {
                                mWebView.loadUrl(protocol.getUrl());
                            } else {
                                mWebView.loadUrl(Constants.PROTOCOL_URL);
                            }
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        mWebView.loadUrl(Constants.PROTOCOL_URL);
                        Log.e("---", e.getMessage() + "--");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("---", "---");
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_protocol_back:
                this.finish();
                break;
        }
    }
}
