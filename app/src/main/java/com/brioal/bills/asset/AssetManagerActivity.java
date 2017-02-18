package com.brioal.bills.asset;

import android.os.Bundle;

import com.brioal.bills.R;
import com.brioal.bills.asset.contract.AssetContract;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.AssetBean;

import java.util.List;

public class AssetManagerActivity extends BaseActivity implements AssetContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_asset_manager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadDone() {

    }

    @Override
    public void showLoadFailed() {

    }

    @Override
    public void showAssets(List<AssetBean> list) {

    }

    @Override
    public void showAllCount(float allCount) {

    }
}
