package com.brioal.bills.add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.add.contract.AddContract;
import com.brioal.bills.add.presenter.AddPresenterImpl;
import com.brioal.bills.base.BaseActivity;
import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.bean.ExchaType;
import com.brioal.bills.extype.ExTypeActivity;
import com.brioal.labelview.LabelView;
import com.brioal.labelview.entity.LabelEntity;
import com.brioal.labelview.interfaces.OnLabelSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExAddActivity extends BaseActivity implements AddContract.View {

    @BindView(R.id.ex_add_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ex_add_et_money)
    EditText mEtMoney;
    @BindView(R.id.ex_add_switch_in)
    Switch mSwitchIn;
    @BindView(R.id.ex_add_label_type)
    LabelView mLabelType;
    @BindView(R.id.ex_add_et_desb)
    EditText mEtDesc;
    @BindView(R.id.ex_add_label_asset)
    LabelView mLabelAsset;
    @BindView(R.id.ex_add_btn_commit)
    Button mBtnCommit;

    private List<AssetBean> mAssets;
    private ProgressDialog mProgressDialog;
    private AddContract.Presenter mPresenter;
    private ExchaType mSelectedType;
    private AssetBean mAssetBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ex_add);
        ButterKnife.bind(this);
        initData();
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new AddPresenterImpl(this);
        mPresenter.start();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSwitchIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPresenter.showInType();
                } else {
                    mPresenter.showOutType();
                }
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float money = Float.parseFloat(mEtMoney.getText().toString());
                String desc = mEtDesc.getText().toString();
                if (mSelectedType == null || mAssetBean == null || money < 0) {
                    showToast("请补全信息后重试");
                    return;
                }
                ExchaBean bean = new ExchaBean();
                bean.setMoney(money);
                bean.setExchaType(mSelectedType);
                bean.setAsset(mAssetBean);
                bean.setDesc(desc);
                mPresenter.addExt(bean);
            }
        });
        if (mAssets == null) {
            return;
        }
        List<LabelEntity> labels = new ArrayList<>();
        for (int i = 0; i < mAssets.size(); i++) {
            AssetBean bean = mAssets.get(i);
            labels.add(new LabelEntity(bean.getName() + ":" + bean.getMoney(), ""));
        }
        mLabelAsset.setColorBGNormal(getResources().getColor(R.color.colorLightWhite));
        mLabelAsset.setColorBGSelect(getResources().getColor(R.color.colorGreen));
        mLabelAsset.setColorTextNormal(getResources().getColor(R.color.colorPrimary));
        mLabelAsset.setColorTextSelect(getResources().getColor(R.color.colorPrimary));
        mLabelAsset.setLabels(labels);
        mLabelAsset.setListener(new OnLabelSelectedListener() {
            @Override
            public void selected(int i, String s) {
                mAssetBean = mAssets.get(i);
            }
        });
        List<LabelEntity> type = new ArrayList<>();
        type.add(new LabelEntity("+", ""));
        //是支出
        mLabelType.setColorBGNormal(getResources().getColor(R.color.colorLightWhite));
        mLabelType.setColorBGSelect(getResources().getColor(R.color.colorRed));
        mLabelType.setColorTextNormal(getResources().getColor(R.color.colorPrimary));
        mLabelType.setColorTextSelect(getResources().getColor(R.color.colorPrimary));
        mLabelType.setLabels(type);
    }

    private void initData() {
        mAssets = (List<AssetBean>) getIntent().getSerializableExtra("Asset");
    }

    //显示操作的类型
    @Override
    public void showExtype(final List<ExchaType> list) {
        mSelectedType = null;
        if (list.get(0).isOut()) {
            //是支出
            mLabelType.setColorBGNormal(getResources().getColor(R.color.colorLightWhite));
            mLabelType.setColorBGSelect(getResources().getColor(R.color.colorRed));
            mLabelType.setColorTextNormal(getResources().getColor(R.color.colorPrimary));
            mLabelType.setColorTextSelect(Color.WHITE);
        } else {
            //是收入
            mLabelType.setColorBGNormal(getResources().getColor(R.color.colorLightWhite));
            mLabelType.setColorBGSelect(getResources().getColor(R.color.colorGreen));
            mLabelType.setColorTextNormal(getResources().getColor(R.color.colorPrimary));
            mLabelType.setColorTextSelect(getResources().getColor(R.color.colorPrimary));
        }
        List<LabelEntity> labels = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            labels.add(new LabelEntity(list.get(i).getName(), ""));
        }
        labels.add(new LabelEntity("+", "+"));
        mLabelType.setLabels(labels);
        mLabelType.setListener(new OnLabelSelectedListener() {
            @Override
            public void selected(int i, String s) {
                for (int j = 0; j < mLabelType.getChildCount(); j++) {
                    TextView text = (TextView) mLabelType.getChildAt(i);
                    if (j == i) {
                        text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        text.setBackgroundResource(R.drawable.asset_add_btn_bg);
                    } else {
                        text.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                }
                if (i != mLabelType.getChildCount() - 1) {
                    mSelectedType = list.get(i);
                } else {
                    Intent intent = new Intent(mContext, ExTypeActivity.class);
                    startActivityForResult(intent, 0);

                }
            }
        });
    }

    //显示正在加载
    @Override
    public void showLoading(String msg) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("请稍等");
        mProgressDialog.setMessage("正在加载数据,请稍等");
        mProgressDialog.show();
    }

    @Override
    public void showLoadDone() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showLoadFailed(String errorMsg) {
        showLoadDone();
        showToast(errorMsg);
    }

    //显示添加成功
    @Override
    public void showAddDone() {
        showToast("记录添加成功");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(RESULT_OK);
                finish();
            }
        }, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            mPresenter.start();
        }
    }
}
