package com.first.hdz.qq.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.BaseJson;
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

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText editUserName;
    private EditText editPassword;
    private EditText editRepassword;
    private Button btnRegister;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        editPassword = findViewById(R.id.edit_password_register);
        editUserName = findViewById(R.id.edit_account_register);
        editRepassword = findViewById(R.id.edit_repassword_register);
        imgBack = findViewById(R.id.img_register_back);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                inputConfirm();
                break;
            case R.id.img_register_back:
                turnBack();
                break;
        }
    }

    private void inputConfirm() {
        String userName = editUserName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String rePassword = editRepassword.getText().toString().trim();
        if (StringUtils.isEmpty(userName)) {
            Toast.makeText(this, getString(R.string.user_name_no_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.password__no_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(rePassword)) {
            Toast.makeText(this, getString(R.string.repassword__no_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this, getString(R.string.password_not_same), Toast.LENGTH_SHORT).show();
            return;
        }
        register(userName, password);
    }

    private void register(String userName, String password) {
        Observable<BaseJson<Boolean>> observable = QQService.Init(QQService.TYPE_BOOLEAN).getService(QQApi.class).Register(userName, password);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson<Boolean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseJson<Boolean> baseJson) {
                        Toast.makeText(RegisterActivity.this, baseJson.getMsg(), Toast.LENGTH_SHORT).show();
                        boolean isSuccess = baseJson.getData();
                        if (isSuccess) {
                            turnBack();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void turnBack() {
        setResult(Constants.NORMAL_RESULT_CODE, new Intent(RegisterActivity.this, LoginActivity.class));
        RegisterActivity.this.finish();
    }
}
