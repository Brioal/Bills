package com.brioal.bills.interfaces;

import com.brioal.bills.bean.ExchaType;

import java.util.List;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/19.
 */

public interface OnExtypeLoadListener {
    void success(List<ExchaType> list);

    void failed(String errorMsg);
}
