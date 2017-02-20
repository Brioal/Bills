package com.brioal.bills.util;

import java.text.DecimalFormat;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/20.
 */

public class MoneyFormatUtil {
    public static String getFormatMoney(float count) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(count);
        return distanceString;
    }
}
