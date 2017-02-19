package com.brioal.bills.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.add.ExAddActivity;
import com.brioal.bills.asset.AssetManagerActivity;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.interfaces.OnExLongClickListener;
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
    private MainAdapter mMainAdapter;
    private ProgressDialog mProgressDialog;
    private int mSelectIndex = -1;

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
                Intent intent = new Intent(mContext, AssetManagerActivity.class);
                startActivityForResult(intent, 0);
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
                Intent intent = new Intent(mContext, ExAddActivity.class);
                intent.putExtra("Asset", mPresenter.getAssets());
                startActivityForResult(intent, 0);
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
        mTvOut.setText("-" + outMoney);
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
    public void showDetail(final List<ExchaBean> list) {
        //显示数据
        mMainAdapter = new MainAdapter(mContext);
        mMainAdapter.showList(list);
        mMainAdapter.setLongClickListener(new OnExLongClickListener() {
            @Override
            public void longClick(ExchaBean bean, int position) {
                mSelectIndex = position;
                showDeleteNotice(bean);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layout.setReverseLayout(false);//列表翻转
        mRecyclerview.setLayoutManager(layout);
        mRecyclerview.setAdapter(mMainAdapter);
    }

    @Override
    public void showDeleting() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在删除,请稍等");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showDeleteDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mPresenter.refresh();
    }

    @Override
    public void showDeleteFailed() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showToast("数据删除失败,请稍候重试");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            mPresenter.refresh();
        }
    }

    private void showDeleteNotice(final ExchaBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("警告").setMessage("删除记录会产生数据回滚,是否删除?").setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.delete(bean);
                dialog.dismiss();
            }
        }).setNegativeButton("不删除了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

    }

}
