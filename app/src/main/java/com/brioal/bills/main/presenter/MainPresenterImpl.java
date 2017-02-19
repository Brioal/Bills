package com.brioal.bills.main.presenter;

import android.os.Handler;

import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.interfaces.OnNormalOperatListener;
import com.brioal.bills.main.contract.MainContract;
import com.brioal.bills.main.model.MainModelImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brioal on 2017/02/10
 */

public class MainPresenterImpl implements MainContract.Presenter {
    private MainContract.View mView;
    private MainContract.Model mModel;
    private Handler mHandler = new Handler();
    private ArrayList<AssetBean> mAssetBeens;

    public MainPresenterImpl(MainContract.View view) {
        mView = view;
        mModel = new MainModelImpl();
    }

    @Override
    public void start() {
        //开始
        //显示加载
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        //加载资金-》显示到所有资金
        mModel.loadAsset(null, new MainContract.OnAssetLoadListener() {
            @Override
            public void success(List<AssetBean> list) {
                float all = 0;
                for (int i = 0; i < list.size(); i++) {
                    all += list.get(i).getMoney();
                }
                final float finalSum = all;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showAll(finalSum);
                    }
                });
                mAssetBeens = (ArrayList<AssetBean>) list;
            }

            @Override
            public void failed(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showAll(0.00f);
                    }
                });
            }
        });
        //加载所有的周转记录-》计算收入，计算支出，计算盈亏，->显示到记录
        mModel.loadExcha(null, 0, 1000, new MainContract.OnExchaBeanLoadListener() {
            @Override
            public void success(final List<ExchaBean> list) {
                float in = 0;//收入
                float out = 0;//支出
                float sum = 0;//盈亏
                //计算收入和支出
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getExchaType().isOut()) {
                        in += list.get(i).getMoney();
                    } else {
                        out += list.get(i).getMoney();
                    }
                }
                sum = in - out;
                final float finalIn = in;
                final float finalOut = out;
                final float finalSum = sum;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.loadDone();
                        mView.showIn(finalIn);
                        mView.showOut(finalOut);
                        mView.showSum(finalSum);
                        mView.showDetail(list);
                    }
                });
            }

            @Override
            public void failed(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.loadFailed();
                    }
                });
            }
        });
    }

    @Override
    public void refresh() {
        start();
    }

    @Override
    public void showMore() {
        // TODO: 2017/2/10  加载更多
    }

    @Override
    public void delete(ExchaBean bean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showDeleting();
            }
        });
        mModel.deleteExcha(bean, new OnNormalOperatListener() {
            @Override
            public void success(String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showDeleteDone();
                    }
                });
            }

            @Override
            public void failed(String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showDeleteFailed();
                    }
                });
            }
        });
    }

    public ArrayList<AssetBean> getAssets() {
        return mAssetBeens;
    }
}