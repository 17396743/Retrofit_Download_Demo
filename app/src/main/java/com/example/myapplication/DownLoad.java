package com.example.myapplication;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @创建时间 2021/4/30 9:59
 */
public class DownLoad extends MyApp {
    public static final String TAG = "DownLoad";

    String savaFile = "/storage/emulated/0/a.apk";
    @Override
    protected void initView() {
        retrofitDown();
    }

    private void retrofitDown() {
        new Retrofit.Builder()
                .baseUrl(ApiService.sUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .download()
                .subscribeOn(Schedulers.io())
                //.observeOn()//观察者运行的线程,如果不指定,直接使用被观察者的线程
                .subscribeWith(new ResourceSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        saveFile(responseBody.byteStream(),
                                responseBody.contentLength());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: "+t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveFile(InputStream inputStream, final long max) {
        ///2.写io流,FileOutputStream 往sd卡写
        //危险权限(9组): 联系人,电话,sd,日历,位置,体感传感器,麦克风,相机,短信
        //危险权限处理分为几步
        //1.清单文件配置权限
        //2.检测权限,有,该干干嘛,没有,第三步
        //3.申请权限
        //4.处理申请结果
        //java.io.FileNotFoundException: /storage/emulated/0/a.apk:
        // open failed: EACCES (Permission denied)
        File file = new File(savaFile);
        //file.exists() 判断文件是否存在,true,代表存在
        try {
            //如果文件不存在,创建

            if (!file.exists()) {
                file.createNewFile();
            }
            Log.d(TAG, "saveFile: "+file.exists());

            FileOutputStream fos = new FileOutputStream(savaFile);
            byte[] bytes = new byte[1024];
            int len;

            //当前读写了多少
            int count = 0;
            //将内容读到字节数组中
            while ((len = inputStream.read(bytes)) != -1) {
                //写,把字节数组中的内容写到文件中
                //从0开始,写len
                fos.write(bytes, 0, len);
                //count = count+len;
                count += len;
                Log.d(TAG, "saveFile: "+Thread.currentThread().getName()+","+count+",max:"+max);

//                //更新进度条
//                //子线程里能刷洗的ui:Progressbar,SurfaceView
//                final int finalCount = count;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //long :8个字节,int: 4字节
//                        //long类型强转为int类型有可能经度丢失
//                        mPb.setMax((int) max);
//                        mPb.setProgress(finalCount);
//
//                        mTv.setText(finalCount +"/"+max);
//                    }
//                });
            }
            Log.d(TAG, "saveFile: 下载完成，文件保存在"+savaFile+"目录下");
            //走到这里,说明读写完了
            //关流
            inputStream.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
