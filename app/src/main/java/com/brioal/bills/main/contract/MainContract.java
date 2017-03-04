package com.brioal.bills.main.contract;

import android.content.Context;

import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.bean.ExchaType;
import com.brioal.bills.interfaces.OnNormalOperatListener;

import java.util.List;

/**
 * email : brioal@foxmial.com
 * github :https://github.com/Brioal
 * Created by Brioal on 2017/2/10.
 */

public class MainContract {
    public interface View {
        //显示正在加载
        void showLoading();

        //加载完成
        void loadDone();

        //加载失败
        void loadFailed();

        //显示支出
        void showOut(float outMoney);

        //显示收入
        void showIn(float inMoney);

        //显示盈亏
        void showSum(float sumMoney);

        //显示详细
        void showDetail(List<ExchaBean> list);

        //显示正在删除
        void showDeleting();

        //显示删除成功
        void showDeleteDone();

        //显示删除失败
        void showDeleteFailed();


    }

    public interface Presenter {
        void start();//开始

        void refresh();//刷新


        void delete(ExchaBean bean);//删除


    }

    public interface Model {
        //加载所有资金记录
        void loadExcha(Context context, int startIndex, int countIndex, OnExchaBeanLoadListener loadListener);

        //删除记录
        void deleteExcha(ExchaBean bean, OnNormalOperatListener listener);

    }

    //添加资金流转记录的监听器
    public interface OnAddListener {
        void success();

        void failed();

    }


    //加载资金操作记录的监听器
    public interface OnExchaBeanLoadListener {
        void success(List<ExchaBean> list);

        void failed(String msg);
    }

    //加载资金流转类型的监听器
    public interface OnExchaTypeListener {
        void success(List<ExchaType> list);

        void failed(String msg);
    }

}