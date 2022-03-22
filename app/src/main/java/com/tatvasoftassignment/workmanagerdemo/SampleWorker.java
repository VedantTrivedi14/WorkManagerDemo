package com.tatvasoftassignment.workmanagerdemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.tatvasoftassignment.workmanagerdemo.Utils.Constant;

public class SampleWorker extends Worker {

    public SampleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data inputData = getInputData();
        int number = inputData.getInt(Constant.number,-1);
        Log.i("SampleWorker","doWork Number : "+ number);
        for(int i=number;i>0;i--){
            Log.i("SampleWorker","doWork : i was "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.failure();
            }
        }
        Data outputData = new Data.Builder()
                .putInt(Constant.number,15)
                .build();
        //here out data will work as input data for next request
        return Result.success(outputData);


    }
}
