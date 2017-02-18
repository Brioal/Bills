package com.brioal.bills.main.model;

import android.content.Context;

import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.main.contract.MainContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Brioal on 2017/02/10
 */

public class MainModelImpl implements MainContract.Model {

    @Override
    public void loadAsset(Context context, final MainContract.OnAssetLoadListener loadListener) {
        //加载所有的资产记录
        BmobQuery<AssetBean> query = new BmobQuery<>();
        query.findObjects(new FindListener<AssetBean>() {
            @Override
            public void done(List<AssetBean> list, BmobException e) {
                if (loadListener == null) {
                    return;
                }
                if (e == null) {
                    //加载成功
                    if (list == null) {
                        loadListener.failed("找不到数据");
                        return;
                    }
                    if (list.size() == 0) {
                        loadListener.failed("找不到数据");
                        return;
                    }
                    loadListener.success(list);
                } else {
                    //加载失败
                    loadListener.failed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void loadExcha(Context context, int startIndex, int countIndex, final MainContract.OnExchaBeanLoadListener loadListener) {
        //根据下标查找资金流转记录
        BmobQuery<ExchaBean> query = new BmobQuery<>();
        query.setSkip(startIndex);
        query.setLimit(countIndex);
        query.order("createdAt");
        query.findObjects(new FindListener<ExchaBean>() {
            @Override
            public void done(List<ExchaBean> list, BmobException e) {
                if (loadListener == null) {
                    return;
                }
                if (e != null) {
                    loadListener.failed(e.getMessage());
                    return;
                }
                if (list == null) {
                    loadListener.failed("找不到数据");
                    return;
                }
                if (list.size() == 0) {
                    loadListener.failed("找不到数据");
                    return;
                }
                loadListener.success(list);
            }
        });
    }

}