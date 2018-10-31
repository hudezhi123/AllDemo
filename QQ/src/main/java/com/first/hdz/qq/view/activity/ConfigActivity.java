package com.first.hdz.qq.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.BaseJson;
import com.first.hdz.qq.utils.Constants;
import com.first.hdz.qq.utils.QQApi;
import com.first.hdz.qq.utils.QQService;
import com.first.hdz.qq.utils.SharePUtils;
import com.first.hdz.qq.utils.StringUtils;
import com.first.hdz.qq.view.base.BaseActivity;

import io.reactivex.Observable;


public class ConfigActivity extends BaseActivity implements View.OnClickListener {


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
        if(StringUtils.isEmpty(mIP)){

        }else{

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

                break;
        }
    }

    private void contactTry() {
        String part1 = editRank1.getText().toString();
        String part2 = editRank2.getText().toString();
        String part3 = editRank3.getText().toString();
        String part4 = editRank4.getText().toString();
        if (!StringUtils.isEmpty(part1) && !StringUtils.isEmpty(part2) && !StringUtils.isEmpty(part3) && !StringUtils.isEmpty(part4)) {

        }
    }

    private void ipTest(String ip) {
        Constants.IP = ip;
        Observable<BaseJson<Boolean>> observable = QQService.Init(QQService.TYPE_BOOLEAN).getService(QQApi.class).IPTest();
    }
}
