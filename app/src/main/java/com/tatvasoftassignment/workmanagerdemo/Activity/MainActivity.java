package com.tatvasoftassignment.workmanagerdemo.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.tatvasoftassignment.workmanagerdemo.R;
import com.tatvasoftassignment.workmanagerdemo.SampleWorker;
import com.tatvasoftassignment.workmanagerdemo.Utils.Constant;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder().putInt(Constant.number,10).build();

        OneTimeWorkRequest countRequest = new OneTimeWorkRequest.Builder(SampleWorker.class)
                .setInputData(data)
                .addTag("count")
                .build();
        WorkManager.getInstance(this).enqueue(countRequest);

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(SampleWorker.class,7, TimeUnit.DAYS)
                .setInputData(data)
                .addTag("periodic")
                .build();

//        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
//
//        WorkManager.getInstance(this).getWorkInfosByTagLiveData("count").observe(this,
//
//                workInfos -> {
//                    for (WorkInfo info : workInfos) {
//                        Log.i("main ","onChange:Work Status : "+info.getState());
//                    }
//
//                }
//
//        );
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(countRequest.getId()).observe(this,
                workInfo -> Log.i("main","onChange Work Status: "+workInfo.getState()));

//        WorkManager.getInstance(this).cancelWorkById(countRequest.getId());

        WorkManager.getInstance(this).beginWith(countRequest)
                .then(countRequest)
                .then(countRequest)
                .enqueue();
    }
}