package com.taoQlegoupeisongduanandroid.delivery.utils.util;

import android.content.Context;
import android.widget.Toast;

import com.taoQlegoupeisongduanandroid.delivery.app.App;

/**
 * Created by changxing on 2016/7/26.
 */
public class TS {
    public static void show(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

}
