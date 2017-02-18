package com.brioal.bills.bean;

import cn.bmob.v3.BmobObject;

/**
 * 资金操作类型实体类
 * email : brioal@foxmial.com
 * github :https://github.com/Brioal
 * Created by Brioal on 2017/2/10.
 */

public class ExchaType extends BmobObject {
    private String mName;//类型名称
    private boolean isOut = true;//是否是支出的类型

    public boolean isOut() {
        return isOut;
    }

    public ExchaType setOut(boolean out) {
        isOut = out;
        return this;
    }

    public String getName() {
        return mName;
    }

    public ExchaType setName(String name) {
        mName = name;
        return this;
    }
}
