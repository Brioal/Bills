package com.brioal.bills.interfaces;

import cn.bmob.v3.BmobObject;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/19.
 */

public interface OnItemSwipeListener {
    void change(BmobObject bean, int position);

    void delete(BmobObject bean, int position);
}
