package com.brioal.bills.interfaces;

/**
 * 通用的事物处理监听器
 * email : brioal@foxmial.com
 * github :https://github.com/Brioal
 * Created by Brioal on 2017/2/10.
 */

public interface OnOperatorListener {
    void success();

    void failed(String msg);
}
