package com.first.hdz.qq.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.BaseJson;
import com.first.hdz.qq.bean.LoginInfo;
import com.first.hdz.qq.utils.AppUtils;
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


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView textForgetPass;
    private TextView textRegister;
    private Button btnLogin;
    private TextView textProtocol;
    private EditText editAccount;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login_ativity);
        init();
    }


    private void init() {
        initView();
    }

    private void initView() {
        textForgetPass = findViewById(R.id.text_forget_pass_login);
        textRegister = findViewById(R.id.text_register_login);
        btnLogin = findViewById(R.id.btn_login);
        textProtocol = findViewById(R.id.text_protocol);
        editPassword = findViewById(R.id.edit_password_login);
        editAccount = findViewById(R.id.edit_account_login);
        textForgetPass.setOnClickListener(this);
        textRegister.setOnClickListener(this);
        textProtocol.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ConfigActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_forget_pass_login:
                returnToActivity(ForgetActivity.class);
                break;
            case R.id.text_register_login:
                returnToActivity(RegisterActivity.class);
                break;
            case R.id.text_protocol:
                turnToActivity(ProtocolActivity.class);
                break;
            case R.id.btn_login:
                inputIsEmpty();
                break;
        }
    }

    private void inputIsEmpty() {
        String account = editAccount.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        if (StringUtils.isEmpty(account)) {
            Toast.makeText(this, getString(R.string.user_name_no_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (account.length() < 6) {
            Toast.makeText(this, getString(R.string.user_name_min_length), Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.password__no_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, getString(R.string.password_min_length), Toast.LENGTH_SHORT).show();
            return;
        }
        Observable<String> observable = QQService.Init(QQService.TYPE_STRING).getService(QQApi.class).Login(AppUtils.getLoginParam(this), account, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String resultJson) {
                        BaseJson<LoginInfo> baseJson = JSON.parseObject(resultJson, new TypeReference<BaseJson<LoginInfo>>() {
                        });
                        if (baseJson != null) {
                            LoginInfo info = baseJson.getData();
                            if (info != null) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(Constants.DATA_KEY, info);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "" + baseJson.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.i("---", "失败");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("---", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
