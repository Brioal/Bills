package com.brioal.bills.extype;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.brioal.bills.R;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.ExchaType;
import com.brioal.bills.extype.contract.ExtypeContract;
import com.brioal.bills.extype.presenter.ExtypePresenterImpl;
import com.brioal.bills.interfaces.OnItemSwipeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobObject;

public class ExTypeActivity extends BaseActivity implements ExtypeContract.View {

    @BindView(R.id.type_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.type_switch)
    Switch mSwitch;
    @BindView(R.id.type_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.type_btn_add)
    FloatingActionButton mBtnAdd;

    private Handler mHandler = new Handler();
    private int mCurrentExType = 0;//默认支出
    private ProgressDialog mProgressDialog;
    private ExTypeAdapter mExTypeAdapter;
    private ExtypePresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.act_ex_type);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new ExtypePresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        //开关
        mSwitch.setChecked(mCurrentExType == 1);
        //ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //点击添加
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        //类型切换
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCurrentExType = 1;
                    mPresenter.showInType();
                } else {
                    mCurrentExType = 0;
                    mPresenter.showOutType();
                }
            }
        });
        //下拉刷新
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCurrentExType == 0) {
                    mPresenter.showOutType();
                } else {
                    mPresenter.showInType();
                }
            }
        });
    }

    private void initData() {
        mCurrentExType = getIntent().getIntExtra("ExType", 0);
    }

    @Override
    public void showRefreshing() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void showRefreshDone() {
        if (!mRefreshLayout.isRefreshing()) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showRefresshFailed(String errorMsg) {
        if (mRefreshLayout.isRefreshing()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(false);
                }
            });
        }
        showToast(errorMsg);
    }

    @Override
    public void showInType(List<ExchaType> list) {
        mExTypeAdapter = new ExTypeAdapter(mContext);
        mExTypeAdapter.showList(list);
        mExTypeAdapter.setSwipeListener(new OnItemSwipeListener() {
            @Override
            public void change(BmobObject bean, int position) {
                showEditDialog((ExchaType) bean);
            }

            @Override
            public void delete(BmobObject bean, int position) {
                showDeleteNotice((ExchaType) bean);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mExTypeAdapter);
    }

    @Override
    public void showOutType(List<ExchaType> list) {
        mExTypeAdapter = new ExTypeAdapter(mContext);
        mExTypeAdapter.showList(list);
        mExTypeAdapter.setSwipeListener(new OnItemSwipeListener() {
            @Override
            public void change(BmobObject bean, int position) {
                showEditDialog((ExchaType) bean);
            }

            @Override
            public void delete(BmobObject bean, int position) {
                showDeleteNotice((ExchaType) bean);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mExTypeAdapter);
    }

    @Override
    public int getCurrentExType() {
        return mCurrentExType;
    }

    @Override
    public void showLoading(String msg) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showLoadFailed(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showToast(msg);
    }

    @Override
    public void showLoadDone() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        showToast("更改完成");
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
        window.setContentView(R.layout.layout_edit_extype);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        addDialog.setCancelable(false);
        //组件初始化
        ImageButton btnClose = (ImageButton) window.findViewById(R.id.layout_edit_type_btn_close);
        final EditText etName = (EditText) window.findViewById(R.id.layout_edit_type_et_name);
        View btnAdd = window.findViewById(R.id.layout_edit_type_btn_add);
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
                String name = etName.getText().toString();
                if (!name.isEmpty()) {
                    //添加类型
                    ExchaType bean = new ExchaType();
                    bean.setName(name);
                    bean.setOut(mCurrentExType == 0);
                    mPresenter.addType(bean);
                    addDialog.dismiss();
                    return;
                }
                showToast("金额和名称不能为空");
            }
        });
    }

    //显示修改的Dialog
    private void showEditDialog(final ExchaType bean) {
        final AlertDialog addDialog = new AlertDialog.Builder(mContext).create();
        addDialog.show();
        Window window = addDialog.getWindow();
        window.setContentView(R.layout.layout_edit_extype);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        addDialog.setCancelable(false);
        //组件初始化
        ImageButton btnClose = (ImageButton) window.findViewById(R.id.layout_edit_type_btn_close);
        final EditText etName = (EditText) window.findViewById(R.id.layout_edit_type_et_name);
        View btnAdd = window.findViewById(R.id.layout_edit_type_btn_add);
        //组件点击事件
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });
        etName.setText(bean.getName());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                if (!name.isEmpty()) {
                    //添加资产
                    bean.setName(name);
                    mPresenter.EditType(bean);
                    addDialog.dismiss();
                    return;
                }
                showToast("金额和名称不能为空");
            }
        });
    }

    //显示删除的确认框
    private void showDeleteNotice(final ExchaType bean) {
        AlertDialog.Builder configDialog = new AlertDialog.Builder(mContext);
        configDialog.setTitle("警告");
        configDialog.setMessage("删除资产会删除与资产相关的所有记录,是否删除");
        configDialog.setCancelable(false);
        configDialog.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteType(bean);
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

}
