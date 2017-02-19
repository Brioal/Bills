package com.brioal.bills.asset.contract;

import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.interfaces.OnOperatorListener;
import com.brioal.bills.main.contract.MainContract;

import java.util.List;

/**
 * email : brioal@foxmial.com
 * github :https://github.com/Brioal
 * Created by Brioal on 2017/2/10.
 */

public class AssetContract {
    public interface View {
        void showLoading();//显示正在加载

        void showLoadDone();//显示加载完成

        void showLoadFailed(String errorMsg);//显示加载失败

        void showAssets(List<AssetBean> list);//显示资产

        void showAllCount(float allCount);//显示所有资产总和

        void showChanging();//显示正在更改

        void showChangeSuccess();//显示更改成功

        void showChangeFailed(String error);//显示更改失败
    }

    public interface Presenter {
        void start();//开始

        void refresh();//刷新

        void addAsset(AssetBean bean);//添加资产

        void deleteAsset(AssetBean bean);//删除资产

        void changeAsset(AssetBean bean);//修改Asset

    }

    public interface Model {
        //加载所有的资产
        void loadAllAssets(MainContract.OnAssetLoadListener loadListener);

        //添加资产
        void addAsset(AssetBean bean, OnOperatorListener listener);

        //删除资产
        void deleteAsset(AssetBean bean, OnOperatorListener listener);

        //修改资产
        void changeAsset(AssetBean bean, OnOperatorListener listener);

    }


}