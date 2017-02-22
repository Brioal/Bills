package com.brioal.bills.extype.contract;

import com.brioal.bills.bean.ExchaType;
import com.brioal.bills.interfaces.OnExtypeLoadListener;
import com.brioal.bills.interfaces.OnNormalOperatListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/21.
 */

public class ExtypeContract {
    public interface View {
        //显示刷新加载
        void showRefreshing();

        //显示刷新完成
        void showRefreshDone();

        //显示刷新失败
        void showRefresshFailed(String errorMsg);

        //显示收ru入Type
        void showInType(List<ExchaType> list);

        //显示支出Type
        void showOutType(List<ExchaType> list);

        //返回当前的Ex状态
        int getCurrentExType();

        //显示加载
        void showLoading(String msg);

        //显示加载失败
        void showLoadFailed(String msg);

        //显示加载成功
        void showLoadDone();
    }

    public interface Presenter {
        void start();//默认的初始

        void showInType();//显示收入的类型

        void showOutType();//显示支出的类型

        void EditType(ExchaType type);//编辑Type

        void deleteType(ExchaType type);//删除Type

        void addType(ExchaType type);//添加Type


    }

    public interface Model {
        //加载收入的类型
        void loadInType(OnExtypeLoadListener loadListener);

        //加载支出的类型
        void loadOutTYpe(OnExtypeLoadListener loadListener);

        //编辑Type
        void edit(ExchaType type, OnNormalOperatListener listener);

        //删除Type
        void delete(ExchaType type, OnNormalOperatListener listener);

        //添加Type
        void add(ExchaType type, OnNormalOperatListener listener);
    }


}