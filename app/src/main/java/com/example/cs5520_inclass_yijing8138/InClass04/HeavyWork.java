package com.example.cs5520_inclass_yijing8138.InClass04;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HeavyWork implements Runnable {
    private final int n;
    private final Handler messageQueue;
    private final static int STATUS_START = 0x001;
    private final static int STATUS_PROGRESS = 0x002;
    private final static int STATUS_END = 0x003;
    private final static String KEY_PROCESS = "key_process";
    private final static String KEY_MAX = "key_max";
    private final static String KEY_MIN = "key_min";
    private final static String KEY_AVG = "key_avg";
    public HeavyWork(int n, Handler messageQueue) {
        this.n = n;
        this.messageQueue = messageQueue;
    }

    static final int COUNT = 9000000;
    public ArrayList<Double> getArrayNumbers(int n){
        ArrayList<Double> returnArray = new ArrayList<>();

        if(n == 0)
        {
            returnArray.add(0.0);
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_PROCESS, 100);
            Message message = new Message();
            message.what = STATUS_PROGRESS;
            message.setData(bundle);
            messageQueue.sendMessage(message);
            return returnArray;
        }

        for (int i=0; i<n; i++) {
            returnArray.add(getNumber());
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_PROCESS, (i + 1) * 100 / n);
            Message messageProgress = new Message();
            messageProgress.what = STATUS_PROGRESS;
            messageProgress.setData(bundle);
            messageQueue.sendMessage(messageProgress);
            Log.d("demo", "getArrayNumbers: " + i);
        }
        return returnArray;
    }

    public double getNumber(){
        double num = 0;
        Random ran = new Random();
        for(int i=0;i<COUNT; i++){
            num = num + (Math.random()*ran.nextDouble()*100+ran.nextInt(50))*1000;
        }
        return num / ((double) COUNT);
    }

    @Override
    public void run() {
        Message messageStart = new Message();
        messageStart.what = STATUS_START;
        messageQueue.sendMessage(messageStart);

        ArrayList<Double> returnArray = getArrayNumbers(this.n);

        double max = Collections.max(returnArray);
        double min = Collections.min(returnArray);
        double avg = returnArray.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);

        Message messageEnd = new Message();
        messageEnd.what = STATUS_END;

        Bundle bundle = new Bundle();
        bundle.putDouble(KEY_MAX, max);
        bundle.putDouble(KEY_MIN, min);
        bundle.putDouble(KEY_AVG, avg);

        messageEnd.setData(bundle);
        messageQueue.sendMessage(messageEnd);
    }
}