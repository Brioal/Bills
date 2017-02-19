package com.brioal.bills.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.asset.AssetManagerActivity;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.main.contract.MainContract;
import com.brioal.bills.main.presenter.MainPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {


    @BindView(R.id.main_tv_all)
    TextView mTvAll;
    @BindView(R.id.main_btn_detail)
    ImageButton mBtnDetail;
    @BindView(R.id.main_tv_in)
    TextView mTvIn;
    @BindView(R.id.main_tv_out)
    TextView mTvOut;
    @BindView(R.id.main_tv_sum)
    TextView mTvSum;
    @BindView(R.id.main_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.main_refreshlayout)
    SwipeRefreshLayout mRefreshlayout;
    @BindView(R.id.main_btn_add)
    FloatingActionButton mBtnAdd;

    private Handler mHandler = new Handler();
    private MainPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new MainPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //跳转资金详情
        mBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetManagerActivity.enterAssetManager(mContext);
            }
        });
        //初始化RefreshLayout
        mRefreshlayout.setColorSchemeColors(Color.BLACK, Color.GREEN, Color.BLUE, Color.RED);
        mRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/2/19 添加资金流转
            }
        });
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
                mRefreshlayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void loadDone() {
        if (mRefreshlayout.isRefreshing()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshlayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void loadFailed() {
        if (mRefreshlayout.isRefreshing()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshlayout.setRefreshing(false);
                }
            });
        }
        showToast("加载数据失败，请稍后重试");
    }

    @Override
    public void showAll(float allMoney) {
        mTvAll.setText(allMoney + "");
    }

    @Override
    public void showOut(float outMoney) {
        mTvOut.setText(outMoney + "");
    }

    @Override
    public void showIn(float inMoney) {
        mTvIn.setText("+" + inMoney + "");
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
