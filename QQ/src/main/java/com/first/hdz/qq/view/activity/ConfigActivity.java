package com.first.hdz.qq.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.BaseJson;
import com.first.hdz.qq.utils.Constants;
import com.first.hdz.qq.utils.LogUtils;
import com.first.hdz.qq.utils.QQApi;
import com.first.hdz.qq.utils.QQService;
import com.first.hdz.qq.utils.SharePUtils;
import com.first.hdz.qq.utils.StringUtils;
import com.first.hdz.qq.view.base.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ConfigActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "ConfigActivity";

    private ImageView imgBack;
    private TextView textTitle;
    private TextView textContact;
    private Button btnConfirm;
    private EditText editRank1;
    private EditText editRank2;
    private EditText editRank3;
    private EditText editRank4;
    private String mIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        mIP = (String) SharePUtils.getValue(this, SharePUtils.IP, "");
        if (StringUtils.isEmpty(mIP)) {

        } else {

        }
        init();
    }


    private void init() {
        initView();
    }

    private void initTitle() {
        imgBack = findViewById(R.id.img_back_common_bar);
        textTitle = findViewById(R.id.text_title_common_bar);
        imgBack.setOnClickListener(this);
        textTitle.setText(getString(R.string.Ip_setting));
    }

    private void initView() {
        initTitle();
        editRank1 = findViewById(R.id.edit_rank1_ip);
        editRank2 = findViewById(R.id.edit_rank2_ip);
        editRank3 = findViewById(R.id.edit_rank3_ip);
        editRank4 = findViewById(R.id.edit_rank4_ip);
        textContact = findViewById(R.id.text_contact_try_ip);
        textContact.setOnClickListener(this);
        btnConfirm = findViewById(R.id.btn_confirm_ip);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_ip:

                break;
            case R.id.img_back_common_bar:

                break;
            case R.id.text_contact_try_ip:
                contactTry();
                break;
        }
    }

    private void contactTry() {
        String part1 = editRank1.getText().toString();
        String part2 = editRank2.getText().toString();
        String part3 = editRank3.getText().toString();
        String part4 = editRank4.getText().toString();
        if (!StringUtils.isEmpty(part1) && !StringUtils.isEmpty(part2) && !StringUtils.isEmpty(part3) && !StringUtils.isEmpty(part4)) {
            ipTest(part1 + "." + part2 + "." + part3 + "." + part4);
        } else {
            Toast.makeText(this, "input ip", Toast.LENGTH_SHORT).show();
        }
    }

    private void ipTest(String ip) {
        Constants.IP = ip;
        Observable<BaseJson<Boolean>> observable = QQService.Init(QQService.TYPE_BOOLEAN).getService(QQApi.class).IPTest();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseJson<Boolean> booleanBaseJson) {
                        Boolean isCan = booleanBaseJson.getData();
                        if (isCan) {
                            // TODO: 2018/10/31    能访问服务器
                            LogUtils.d(TAG, "success");
                        } else {
                            // TODO: 2018/10/31  不能访问服务器
                            LogUtils.d(TAG, "fail");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
