package com.brioal.bills.asset.model;

import com.brioal.bills.asset.contract.AssetContract;
import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.interfaces.OnOperatorListener;
import com.brioal.bills.main.contract.MainContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Brioal on 2017/02/10
 */

public class AssetModelImpl implements AssetContract.Model {

    @Override
    public void loadAllAssets(final MainContract.OnAssetLoadListener loadListener) {
        //加载所有的资产
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
    public void addAsset(AssetBean bean, final OnOperatorListener listener) {
        //添加资产
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                    return;
                }
                listener.success();
            }
        });
    }

    @Override
    public void deleteAsset(AssetBean bean, final OnOperatorListener listener) {
        //删除资产
        bean.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                }
                listener.success();
            }
        });
    }

    @Override
    public void changeAsset(AssetBean bean, final OnOperatorListener listener) {
        //修改资产
        bean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (listener == null) {
                    return;
                }
                if (e != null) {
                    listener.failed(e.getMessage());
                }
                listener.success();
            }
        });
    }
}