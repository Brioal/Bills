package com.brioal.bills;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.main.MainActivity;
import com.zhy.m.permission.MPermissions;

import cn.bmob.v3.Bmob;

public class LauncherActivity extends BaseActivity {

    EditText mEtPass;
    private String mAPPID = "a5a74024cbf7473ff29afeb5cb5a7224";
    private final int mPermissionCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.BillTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_launcher);
        mEtPass = (EditText) findViewById(R.id.launcher_et_pass);
        initView();
        initSDK();
        //MPermissions.requestPermissions(LauncherActivity.this, mPermissionCode,  Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    //初始化SDk
    private void initSDK() {
        Bmob.initialize(mContext, mAPPID);
    }


    private void initView() {
        mEtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String pass = v.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (pass.isEmpty()) {
                        showToast("密码不能为空");
                    } else if (pass.equals("0105")) {
                        MainActivity.enterMain(mContext);
                        finish();
                    } else {
                        showToast("密码输入错误，请重新输入");
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
