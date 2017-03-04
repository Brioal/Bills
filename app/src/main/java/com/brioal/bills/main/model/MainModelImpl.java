package com.brioal.bills.main.model;

import android.content.Context;

import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.interfaces.OnNormalOperatListener;
import com.brioal.bills.main.contract.MainContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Brioal on 2017/02/10
 */

public class MainModelImpl implements MainContract.Model {


    @Override
    public void loadExcha(Context context, int startIndex, int countIndex, final MainContract.OnExchaBeanLoadListener loadListener) {
        //根据下标查找资金流转记录
        BmobQuery<ExchaBean> query = new BmobQuery<>();
        query.setSkip(startIndex);
        query.setLimit(countIndex);
        query.order("createdAt");
        query.include("mExchaType,mAsset");// 希望在查询帖子信息的同时也把发布人的信息查询出来
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

    @Override
    public void deleteExcha(final ExchaBean bean, final OnNormalOperatListener listener) {
        bean.delete(bean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    if (listener != null) {
                        listener.success("");
                        return;
                    }
                    listener.failed("");
                }
            }
        });
    }

}