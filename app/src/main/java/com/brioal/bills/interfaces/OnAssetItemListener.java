package com.brioal.bills.interfaces;

import com.brioal.bills.bean.AssetBean;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/19.
 */

public interface OnAssetItemListener {
    void change(AssetBean bean, int position);

    void delete(AssetBean bean, int position);
}
