package com.brioal.bills;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.main.MainActivity;
import com.pro100svitlo.fingerprintAuthHelper.FahListener;
import com.pro100svitlo.fingerprintAuthHelper.FingerprintAuthHelper;

import org.jetbrains.annotations.Nullable;

import cn.bmob.v3.Bmob;

public class LauncherActivity extends BaseActivity implements FahListener {
    private FingerprintAuthHelper mFAH;
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
        mFAH = new FingerprintAuthHelper
                .Builder(this, this) //(Context inscance of Activity, FahListener)
                .build();

        if (mFAH.isHardwareEnable()) {
            //do some stuff here
        } else {
            //otherwise do
        }
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

    @Override
    public void onFingerprintStatus(boolean b, int i, @Nullable CharSequence charSequence) {
        if (b) {
            MainActivity.enterMain(mContext);
            finish();
        }
    }

    @Override
    public void onFingerprintListening(boolean b, long l) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFAH.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mFAH.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFAH.onDestroy();
    }

}
