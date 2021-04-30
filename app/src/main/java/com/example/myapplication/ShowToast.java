package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

/**
 * @创建时间 2021/4/30 11:02
 */
public class ShowToast {
    // 吐司小工具
    private Context context;
    public ShowToast(Context context) {
        this.context = context;
    }

    public void show(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
