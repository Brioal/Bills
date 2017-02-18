package com.brioal.bills.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.main.contract.MainContract;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {
    private TextView mTvAll;
    private TextView mTvIn;
    private TextView mTvOut;
    private TextView mTvSum;
    private ImageButton mBtnDetail;
    private FloatingActionButton mBtnAdd;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        initView();
    }

    private void initView() {
        mTvAll = (TextView) findViewById(R.id.main_tv_all);
        mTvIn = (TextView) findViewById(R.id.main_tv_in);
        mTvOut = (TextView) findViewById(R.id.main_tv_out);
        mTvSum = (TextView) findViewById(R.id.main_tv_sum);
        mBtnDetail = (ImageButton) findViewById(R.id.main_btn_detail);
        mBtnAdd = (FloatingActionButton) findViewById(R.id.main_btn_add);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refreshlayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
    }


    public static void enterMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showLoading() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void loadDone() {
        if (mRefreshLayout.isRefreshing()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void loadFailed() {
        showToast("加载数据失败，请稍后重试");
    }

    @Override
    public void showAll(float allMoney) {
        mTvAll.setText(allMoney + "");
    }

    @Override
    public void showOut(float outMoney) {
        mTvOut.setText(outMoney+"");
    }

    @Override
    public void showIn(float inMoney) {
        mTvIn.setText("+"+inMoney+"");
    }

    @Override
    public void showSum(float sumMoney) {
        //显示盈亏
        StringBuffer buffer = new StringBuffer();
        if (sumMoney >= 0) {
            buffer.append("+");
        }
        buffer.append(sumMoney);
        mTvSum.setText(buffer.toString() + "");
    }

    @Override
    public void showDetail(List<ExchaBean> list) {
        //显示数据

    }

}
