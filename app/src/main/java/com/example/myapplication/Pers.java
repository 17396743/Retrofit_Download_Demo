package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

/**
 * @创建时间 2021/4/30 11:08
 */
public class Pers {
    private Activity context;

    public Pers(Activity context) {
        this.context = context;
    }

    //权限处理
    public void initPers() {
        //危险权限处理分为几步
        //1.清单文件配置权限
        //2.检测权限,有,该干干嘛,没有,第三步
        int result = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //有权限
        //    public static final int PERMISSION_GRANTED = 0;
        //没有权限
        //    public static final int PERMISSION_DENIED = -1;
        if (result == PackageManager.PERMISSION_GRANTED) {
            //有权限，开始下载文件
            new DownLoad().initView();
        } else {
            //没有权限
            //3.申请权限
            String[] pers = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(context, pers, 100);
        }
        //4.处理申请结果,onRequestPermissionsResult()

    }
}
