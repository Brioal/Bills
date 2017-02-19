package com.brioal.bills.add.contract;

import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.bean.ExchaType;
import com.brioal.bills.interfaces.OnExtypeLoadListener;
import com.brioal.bills.interfaces.OnNormalOperatListener;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/19.
 */

public class AddContract {
    public interface View {
        //显示操作的类型
        void showExtype(List<ExchaType> list);

        //显示正在添加
        void showLoading(String msg);

        //显示加载成功
        void showLoadDone();

        //显示加载失败
        void showLoadFailed(String errorMsg);

        //显示添加成功,返回
        void showAddDone();

    }

    public interface Presenter {
        void start();//开始

        void showInType();//显示收入的类型

        void showOutType();//显示支出的类型

        void addExt(ExchaBean bean);//添加资金流转
    }

    public interface Model {
        //加载收入的类型
        void loadInType(OnExtypeLoadListener loadListener);

        //加载支出的类型
        void loadOutType(OnExtypeLoadListener loadListener);

        //添加资金流转
        void addExBean(ExchaBean bean, OnNormalOperatListener listener);
    }


}