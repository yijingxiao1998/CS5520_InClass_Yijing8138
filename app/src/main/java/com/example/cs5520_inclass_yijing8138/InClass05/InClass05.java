package com.example.cs5520_inclass_yijing8138.InClass05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cs5520_inclass_yijing8138.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InClass05 extends AppCompatActivity {
    private EditText editText;
    private Button buttonGo;
    private ImageView imageView;
    private TextView loadingText;
    private ProgressBar progressBar;
    private ImageButton nextButton, previousButton;
    private final OkHttpClient client = new OkHttpClient();
    private String[] imageUrlList = {};

    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);
        setTitle("Image Search");

        editText = findViewById(R.id.editTextForSearchKeyword);
        buttonGo= findViewById(R.id.buttonGo);
        imageView = findViewById(R.id.imageViewForDifferentImage);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
        loadingText = findViewById(R.id.loadingText);
        progressBar = findViewById(R.id.progressBarWhileLoading);


        loadingText.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
                loadingText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                if(isInternetAvailable()) {
                    if(editText.getText().toString().length() > 0) {
                        createAnswer(editText.getText().toString());
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Input cannot be empty!",
                                Toast.LENGTH_LONG).show();
                        imageView.setImageResource(R.drawable.placeholder_portrait);
                        loadingText.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "There is no internet connection!",
                            Toast.LENGTH_LONG).show();
                    imageView.setImageResource(R.drawable.placeholder_portrait);
                    loadingText.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUrlList.length > 1)
                {
                    if(index == 0)
                    {
                        index = imageUrlList.length - 1;
                    }
                    else {
                        index -= 1;
                    }
                    imageView.setVisibility(View.INVISIBLE);
                    loadingText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    loadImage();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUrlList.length > 1)
                {
                    if(index == imageUrlList.length - 1)
                    {
                        index = 0;
                    }
                    else {
                        index += 1;
                    }
                    imageView.setVisibility(View.INVISIBLE);
                    loadingText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    loadImage();
                }
            }
        });
    }

    /**
     * Create a answer for the user request. If the response is successful, then do loading image;
     * else we set image to place holder and set progress bar and loading text invisible
     * .
     * @param keyword the user input a keyword for searching images.
     */
    private void createAnswer(String keyword) {
        HttpUrl url = HttpUrl.parse("http://ec2-54-164-201-39.compute-1.amazonaws.com/" +
                        "apis/images/retrieve")
                .newBuilder()
                .addQueryParameter("keyword", keyword)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    imageUrlList = response.body().string().trim().split("\n");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadImage();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Input keyword is invalid!",
                                    Toast.LENGTH_LONG).show();
                            imageView.setImageResource(R.drawable.placeholder_portrait);
                            loadingText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    /**
     * Check the internet status: if there is an established internet connection, the application
     * will download the requested photo. If there is no internet connection, it will return false
     * and display a Toast message.
     *
     * Source provided by professor from: https://stackoverflow.com/a/34320813
     *
     * @return true or false.
     */
    private boolean isInternetAvailable() {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(1000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
        }
        return inetAddress!=null && !inetAddress.equals("");
    }

    /**
     * Load image from the url list. After loading, set the progress bar and loading text invisible.
     */
    private void loadImage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(getApplicationContext())
                        .load(imageUrlList[index])
                        .error(R.drawable.placeholder_portrait)
                        .into(imageView);

                loadingText.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }
}
