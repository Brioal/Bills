package com.brioal.bills.asset;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.asset.contract.AssetContract;
import com.brioal.bills.asset.presenter.AssetPresenterImpl;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.interfaces.OnItemSwipeListener;
import com.brioal.bills.util.MoneyFormatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobObject;

public class AssetManagerActivity extends BaseActivity implements AssetContract.View {

    @BindView(R.id.asset_man_tv_count)
    TextView mTvCount;
    @BindView(R.id.asset_man_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.asset_man_refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.asset_man_floatingbutton)
    FloatingActionButton mBtnAdd;

    @BindView(R.id.asset_man_toolbar)
    Toolbar mToolBar;

    private AssetContract.Presenter mPresenter;
    private Handler mHandler = new Handler();
    private ProgressDialog mProgressDialog;
    private AssetAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_asset_manager);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new AssetPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        mRefreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    //显示正在加载
    @Override
    public void showLoading() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    //显示加载完成
    @Override
    public void showLoadDone() {
        if (mRefreshLayout.isRefreshing()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    //显示加载失败
    @Override
    public void showLoadFailed(String errorMsg) {
        showLoadDone();
        showToast(errorMsg);
    }

    //显示资产
    @Override
    public void showAssets(List<AssetBean> list) {
        mAdapter = new AssetAdapter(mContext);
        mAdapter.showList(list);
        mAdapter.setItemListener(new OnItemSwipeListener() {

            @Override
            public void change(BmobObject bean, int position) {
                showEditDialog((AssetBean) bean);
            }

            @Override
            public void delete(BmobObject bean, int position) {
                showDeleteNotice((AssetBean) bean);
            }
        });
        mRecyclerView.addItemDecoration(new WhiteDividerDecoration(mContext));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }


    //显示资产总数
    @Override
    public void showAllCount(float allCount) {
        mTvCount.setText(MoneyFormatUtil.getFormatMoney(allCount) + "");
    }

    @Override
    public void showChanging() {
        showProgressDialog("请稍候", "正在保存更改....");
    }

    //显示更改成功
    @Override
    public void showChangeSuccess() {
        hideProgressDialog();
    }

    @Override
    public void showChangeFailed(String error) {
        hideProgressDialog();
        showToast(error);
    }

    private void showProgressDialog(String title, String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //显示添加的Dialog
    private void showAddDialog() {
        final AlertDialog addDialog = new AlertDialog.Builder(mContext).create();
        addDialog.show();
        Window window = addDialog.getWindow();
        window.setContentView(R.layout.layout_add_asset);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        addDialog.setCancelable(false);
        //组件初始化
        ImageButton btnClose = (ImageButton) window.findViewById(R.id.layout_add_set_btn_close);
        final EditText etMoney = (EditText) window.findViewById(R.id.layout_add_set_et_money);
        final EditText etName = (EditText) window.findViewById(R.id.layout_add_set_et_name);
        View btnAdd = window.findViewById(R.id.layout_add_set_btn_add);
        //组件点击事件
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float money = Float.parseFloat(etMoney.getText().toString());
                String name = etName.getText().toString();
                if (money != -1 && !name.isEmpty()) {
                    //添加资产
                    AssetBean bean = new AssetBean(name, money);
                    mPresenter.addAsset(bean);
                    addDialog.dismiss();
                    return;
                }
                showToast("金额和名称不能为空");
            }
        });
    }

    //显示修改的Dialog
    private void showEditDialog(final AssetBean bean) {
        final AlertDialog addDialog = new AlertDialog.Builder(mContext).create();
        addDialog.show();
        Window window = addDialog.getWindow();
        window.setContentView(R.layout.layout_edit_asset);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        addDialog.setCancelable(false);
        //组件初始化
        ImageButton btnClose = (ImageButton) window.findViewById(R.id.layout_edit_set_btn_close);
        final EditText etMoney = (EditText) window.findViewById(R.id.layout_edit_set_et_money);
        final EditText etName = (EditText) window.findViewById(R.id.layout_edit_set_et_name);
        View btnAdd = window.findViewById(R.id.layout_edit_set_btn_add);
        //组件点击事件
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });
        etMoney.setText(bean.getMoney() + "");
        etName.setText(bean.getName());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float money = Float.parseFloat(etMoney.getText().toString());
                String name = etName.getText().toString();
                if (money != -1 && !name.isEmpty()) {
                    //添加资产
                    bean.setMoney(money);
                    bean.setName(name);
                    mPresenter.changeAsset(bean);
                    addDialog.dismiss();
                    return;
                }
                showToast("金额和名称不能为空");
            }
        });
    }

    //显示删除的确认框
    private void showDeleteNotice(final AssetBean bean) {
        AlertDialog.Builder configDialog = new AlertDialog.Builder(mContext);
        configDialog.setTitle("警告");
        configDialog.setMessage("删除资产会删除与资产相关的所有记录,是否删除");
        configDialog.setCancelable(false);
        configDialog.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteAsset(bean);
            }
        }).setNegativeButton("不删除了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = configDialog.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
