package com.first.hdz.qq.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.first.hdz.qq.R;

/**
 * created by hdz
 * on 2018/9/28
 */
public class DialogManager {

    private Context context;
    private static DialogManager mInstance;
    private View mContentView;
    private Dialog mDialog;

    private DialogManager() {

    }

    public interface OnActionCallBack {
        public void callBack(View contentView);
    }

    public interface OnDialogItemClick {
        public void onCancel();

        public void onConfirm();
    }


    public static DialogManager create(Context context) {
        if (mInstance == null) {
            mInstance = new DialogManager();
        }
        mInstance.context = context;
        mInstance.init();
        return mInstance;
    }

    private void init() {
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setCancelable(false);
    }

    public void setContentView(int layoutId, OnActionCallBack callBack) throws NullPointerException {
        mContentView = LayoutInflater.from(context).inflate(layoutId, null);
        if (callBack != null) {
            callBack.callBack(mContentView);
            mDialog.setContentView(mContentView);
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            int[] size = DisplayUtils.getScreenSize(context);
            params.width = size[0] * 8 / 10;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        } else {
            throw new NullPointerException("OnActionCallBack==null");
        }
    }


    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void onPause() {
        hide();
    }

    public void onDestroy() {
        hide();
        mDialog = null;
    }

    public void hide() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }
}
