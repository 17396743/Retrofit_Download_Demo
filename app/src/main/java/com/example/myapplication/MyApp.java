package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

public abstract class MyApp extends AppCompatActivity {
    //这里是后台的生命周期，一般是调用，不需要，布局页面的功能，才使用。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected abstract void initView();
}