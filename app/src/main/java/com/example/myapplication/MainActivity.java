package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    private ShowToast showToast;
    private Pers pers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //我没用  findViewById()和黄油刀，点击监听是做在了xml布局里面了。

        // 吐司小工具
        showToast = new ShowToast(this);
        //发送，权限处理
        pers = new Pers(this);

    }
    /**
     * 按钮事件
     * @param view
     */
    public void download(View view) {
        //处理权限中，有了存储权限，才开始下载
        pers.initPers();
        Toast.makeText(this, "点击了下载按钮！", Toast.LENGTH_SHORT).show();
    }

    //权限处理
    ////4.处理申请结果,
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //有权限
                new DownLoad().initView();
            } else {
                //没有权限
                showToast.show("用户拒绝了");
                //重新提取权限！ (选填)
                pers.initPers();
            }
        }
    }

}