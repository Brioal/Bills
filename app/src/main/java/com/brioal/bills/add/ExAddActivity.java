package com.brioal.bills.add;

import android.os.Bundle;

import com.brioal.bills.R;
import com.brioal.bills.add.contract.AddContract;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.ExchaType;

import java.util.List;

public class ExAddActivity extends BaseActivity implements AddContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ex_add);
    }

    @Override
    public void showExtype(List<ExchaType> list) {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showLoadDone() {

    }

    @Override
    public void showLoadFailed(String errorMsg) {

    }

    @Override
    public void showAddDone() {

    }
}
