package com.brioal.bills.bean;

import cn.bmob.v3.BmobObject;

/**
 * 资产实体类
 * email : brioal@foxmial.com
 * github :https://github.com/Brioal
 * Created by Brioal on 2017/2/10.
 */

public class AssetBean extends BmobObject {
    private String mName;//名称
    private float mMoney;//金额

    public AssetBean() {
    }

    public AssetBean(String name, float money) {
        mName = name;
        mMoney = money;
    }

    public String getName() {
        return mName;
    }

    public AssetBean setName(String name) {
        mName = name;
        return this;
    }

    public float getMoney() {
        return mMoney;
    }

    public AssetBean setMoney(float money) {
        mMoney = money;
        return this;
    }
}
