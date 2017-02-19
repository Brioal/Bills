package com.brioal.bills.asset.presenter;

import com.brioal.bills.asset.contract.AssetContract;
import com.brioal.bills.asset.model.AssetModelImpl;
import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.interfaces.OnOperatorListener;
import com.brioal.bills.main.contract.MainContract;

import java.util.List;

/**
 * Created by Brioal on 2017/02/10
 */

public class AssetPresenterImpl implements AssetContract.Presenter {
    private AssetContract.View mView;
    private AssetContract.Model mModel;
    private android.os.Handler mHandler = new android.os.Handler();

    public AssetPresenterImpl(AssetContract.View view) {
        mView = view;
        mModel = new AssetModelImpl();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showLoading();
            }
        });
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadAllAssets(new MainContract.OnAssetLoadListener() {
            @Override
            public void success(final List<AssetBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadDone();
                        mView.showAssets(list);
                        float count = 0;
                        for (int i = 0; i < list.size(); i++) {
                            count += list.get(i).getMoney();
                        }
                        mView.showAllCount(count);
                    }
                });
            }

            @Override
            public void failed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLoadFailed(msg);
                    }
                });
            }
        });
    }

    @Override
    public void addAsset(AssetBean bean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showChanging();
            }
        });
        mModel.addAsset(bean, new OnOperatorListener() {
            @Override
            public void success() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showChangeSuccess();
                    }
                });
                start();
            }

            @Override
            public void failed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showChangeFailed(msg);
                    }
                });
            }
        });
    }

    @Override
    public void deleteAsset(AssetBean bean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showChanging();
            }
        });
        mModel.deleteAsset(bean, new OnOperatorListener() {
            @Override
            public void success() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showChangeSuccess();
                    }
                });
                start();
            }

            @Override
            public void failed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showChangeFailed(msg);
                    }
                });
            }
        });
    }

    @Override
    public void changeAsset(AssetBean bean) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showChanging();
            }
        });
        mModel.changeAsset(bean, new OnOperatorListener() {
            @Override
            public void success() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showChangeSuccess();
                    }
                });
                start();
            }

            @Override
            public void failed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showChangeFailed(msg);
                    }
                });
            }
        });
    }
}