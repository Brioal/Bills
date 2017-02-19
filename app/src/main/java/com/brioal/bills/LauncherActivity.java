package com.brioal.bills;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.main.MainActivity;

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
        mEtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass = s.toString();
                if (pass.equals("0105")) {
                    MainActivity.enterMain(mContext);
                    finish();
                }
            }
        });
    }
}
