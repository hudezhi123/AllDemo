package com.first.hdz.andpermission;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
    }

    private void initPermission() {
        AndPermission.with(this)
                .permission(
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.ACCESS_COARSE_LOCATION,
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.CAMERA)
                .rationale(new Rationale() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
//                        Toast.makeText(MainActivity.this, "rationale", Toast.LENGTH_SHORT).show();
                        executor.execute();
                    }
                })
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Toast.makeText(MainActivity.this, "onGranted", Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            // TODO: 2018/12/18   拒绝后不在提醒
                            Toast.makeText(MainActivity.this, "拒绝后不在提醒", Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO: 2018/12/18    拒绝后仍然可以弹窗提醒
                            Toast.makeText(MainActivity.this, "拒绝后仍然可以弹窗提醒", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .start();
    }
}
