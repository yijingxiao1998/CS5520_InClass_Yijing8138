package com.example.cs5520_inclass_yijing8138.InClass04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.cs5520_inclass_yijing8138.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InClass04 extends AppCompatActivity {
    private TextView selectComplexityNumber;
    private TextView minimumResult, maximumResult, averageResult;
    private SeekBar seekBar;
    private ProgressBar progressBar;
    private ExecutorService threadPool;
    private Handler messageQueue;
    private Button generateNumberButton;
    private int complexity = 8;
    private final static int STATUS_START = 0x001;
    private final static int STATUS_PROGRESS = 0x002;
    private final static int STATUS_END = 0x003;
    private final static String KEY_PROCESS = "key_process";
    private final static String KEY_MAX = "key_max";
    private final static String KEY_MIN = "key_min";
    private final static String KEY_AVG = "key_avg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class04);
        setTitle("Number Generator");

        selectComplexityNumber = findViewById(R.id.textViewSelectComplexityNumber);
        seekBar = findViewById(R.id.seekBarForComplexity);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress)
                {
                    case 0:
                        selectComplexityNumber.setText("0 times");
                        complexity = 0;
                        break;
                    case 1:
                        selectComplexityNumber.setText("1 times");
                        complexity = 1;
                        break;
                    case 2:
                        selectComplexityNumber.setText("2 times");
                        complexity = 2;
                        break;
                    case 3:
                        selectComplexityNumber.setText("3 times");
                        complexity = 3;
                        break;
                    case 4:
                        selectComplexityNumber.setText("4 times");
                        complexity = 4;
                        break;
                    case 5:
                        selectComplexityNumber.setText("5 times");
                        complexity = 5;
                        break;
                    case 6:
                        selectComplexityNumber.setText("6 times");
                        complexity = 6;
                        break;
                    case 7:
                        selectComplexityNumber.setText("7 times");
                        complexity = 7;
                        break;
                    case 8:
                        selectComplexityNumber.setText("8 times");
                        complexity = 8;
                        break;
                    case 9:
                        selectComplexityNumber.setText("9 times");
                        complexity = 9;
                        break;
                    case 10:
                        selectComplexityNumber.setText("10 times");
                        complexity = 10;
                        break;
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        maximumResult = findViewById(R.id.textViewMaximumResult);
        minimumResult = findViewById(R.id.textViewMinimumResult);
        averageResult = findViewById(R.id.textViewAverageResult);
        generateNumberButton = findViewById(R.id.generateNumberButton);
        progressBar = findViewById(R.id.GeneralteNumberProgressBar);

        threadPool = Executors.newFixedThreadPool(complexity);
        messageQueue = new Handler(new Handler.Callback() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bundle bundle;
                switch (msg.what) {
                    case STATUS_START:
                        break;
                    case STATUS_PROGRESS:
                        bundle = msg.getData();
                        int donePercent = bundle.getInt(KEY_PROCESS);
                        progressBar.setProgress(donePercent);
                        break;
                    case STATUS_END:
                        bundle = msg.getData();
                        double max = bundle.getDouble(KEY_MAX);
                        double min = bundle.getDouble(KEY_MIN);
                        double avg = bundle.getDouble(KEY_AVG);
                        maximumResult.setText(Double.toString(max));
                        minimumResult.setText(Double.toString(min));
                        averageResult.setText(Double.toString(avg));
                }
                return false;
            }
        });

        generateNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadPool.execute(new HeavyWork(complexity, messageQueue));
            }
        });
    }
}