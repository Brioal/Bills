package com.brioal.bills.bean;

import cn.bmob.v3.BmobObject;

/**
 * 资金流转记录实体类
 * email : brioal@foxmial.com
 * github :https://github.com/Brioal
 * Created by Brioal on 2017/2/10.
 */

public class ExchaBean extends BmobObject {
    private ExchaType mExchaType;//操作类型
    private float mMoney;//金额
    private String mDesc;//备注
    private long mTime;//产生时间
    private AssetBean mAsset;//影响的资产类型

    public AssetBean getAsset() {
        return mAsset;
    }

    public ExchaBean setAsset(AssetBean asset) {
        mAsset = asset;
        return this;
    }

    public ExchaType getExchaType() {
        return mExchaType;
    }

    public ExchaBean setExchaType(ExchaType exchaType) {
        mExchaType = exchaType;
        return this;
    }

    public float getMoney() {
        return mMoney;
    }

    public ExchaBean setMoney(float money) {
        mMoney = money;
        return this;
    }

    public String getDesc() {
        return mDesc;
    }

    public ExchaBean setDesc(String desc) {
        mDesc = desc;
        return this;
    }

    public long getTime() {
        return mTime;
    }

    public ExchaBean setTime(long time) {
        mTime = time;
        return this;
    }
}
