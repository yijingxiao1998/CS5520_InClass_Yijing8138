package com.example.cs5520_inclass_yijing8138.InClass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cs5520_inclass_yijing8138.MainActivity;
import com.example.cs5520_inclass_yijing8138.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

public class InClass06 extends AppCompatActivity {
    private String APIkey = "07d6c8016fd04198b2f8fb678aea8306";
    private ArrayAdapter<String> adapter;
    private String[] category = {"business", "entertainment", "general", "health", "science",
            "sports", "technology"};
    private String[] countries = {"ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co",
            "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it",
            "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt",
            "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za"};
    private final OkHttpClient client = new OkHttpClient();
    private ListView listView;
    private EditText editTextCountry;
    private ImageView image;
    private ProgressBar progressBar;
    private Button quitButton, previousButton, nextButton;
    private TextView title, author, publishedTime, description, loading, hint;
    private TextView titleResult, authorResult, publishedTimeResult, descriptionResult;
    private Article article;
    private List<Article> articlesList = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class06);
        setTitle("InClass06");

        listView = findViewById(R.id.listview);
        editTextCountry = findViewById(R.id.editTextForCountry);
        title = findViewById(R.id.textViewTitle);
        author = findViewById(R.id.textViewAuthor);
        publishedTime = findViewById(R.id.textViewPublishTime);
        description = findViewById(R.id.textViewDescription);
        titleResult = findViewById(R.id.textViewTitleResult);
        authorResult = findViewById(R.id.textViewAuthorResult);
        publishedTimeResult = findViewById(R.id.textViewPublishTimeResult);
        descriptionResult = findViewById(R.id.textViewDescriptionResult);
        loading = findViewById(R.id.textViewLoading);
        hint = findViewById(R.id.textViewHint);
        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.imageViewShowImage);
        previousButton = findViewById(R.id.buttonPrevious);
        nextButton = findViewById(R.id.buttonNext);
        quitButton = findViewById(R.id.quitButton);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, category);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isInternetAvailable()) {
                    String country = editTextCountry.getText().toString();
                    if (country.isEmpty() || !Arrays.asList(countries).contains(country)) {
                        Toast.makeText(InClass06.this, "Please input valid country code!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        createAnswer(editTextCountry.getText().toString(), category[position]);
                    }
                }
                else{
                    Toast.makeText(InClass06.this, "Internet is not connected!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(articlesList.isEmpty())
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass06.this, "No result found!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    if(index == 0)
                    {
                        index = articlesList.size() - 1;
                    }
                    else{
                        index -= 1;
                    }
                    Article articleResult = articlesList.get(index);
                    titleResult.setText(articleResult.getTitle());
                    authorResult.setText(articleResult.getAuthor());
                    descriptionResult.setText(articleResult.getDescription());
                    publishedTimeResult.setText(articleResult.getPublishedTime());
                    loadImage(articleResult.getImage());
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(articlesList.isEmpty())
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass06.this, "No result found!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    if(index == articlesList.size() - 1)
                    {
                        index = 0;
                    }
                    else{
                        index += 1;
                    }
                    Article articleResult = articlesList.get(index);
                    titleResult.setText(articleResult.getTitle());
                    authorResult.setText(articleResult.getAuthor());
                    descriptionResult.setText(articleResult.getDescription());
                    publishedTimeResult.setText(articleResult.getPublishedTime());
                    loadImage(articleResult.getImage());
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InClass06.this, MainActivity.class);
                startActivities(new Intent[]{intent});
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
     * Create a answer for the user request. If the response is successful, then do loading article;
     * else we make toast as warning.
     * @param country the user input which country's news they want to see.
     * @param category the user input which category they are interested.
     */
    private void createAnswer(String country, String category)
    {
        editTextCountry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        hint.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);

        HttpUrl url = HttpUrl.parse("https://newsapi.org/v2/top-headlines?" +
                        "country=" + country + "&category=" + category +
                        "&apiKey=" + APIkey)
                .newBuilder()
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
                if(response.isSuccessful()) {
                    String string = response.body().string();
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            saveResponseToClass(string);
                            if (!articlesList.isEmpty()) {
                                progressBar.setVisibility(View.GONE);
                                loading.setVisibility(View.GONE);

                                title.setVisibility(View.VISIBLE);
                                author.setVisibility(View.VISIBLE);
                                publishedTime.setVisibility(View.VISIBLE);
                                description.setVisibility(View.VISIBLE);
                                titleResult.setVisibility(View.VISIBLE);
                                authorResult.setVisibility(View.VISIBLE);
                                publishedTimeResult.setVisibility(View.VISIBLE);
                                descriptionResult.setVisibility(View.VISIBLE);
                                image.setVisibility(View.VISIBLE);
                                previousButton.setVisibility(View.VISIBLE);
                                nextButton.setVisibility(View.VISIBLE);
                                quitButton.setVisibility(View.VISIBLE);

                                Article articleResult = articlesList.get(0);
                                titleResult.setText(articleResult.getTitle());
                                authorResult.setText(articleResult.getAuthor());
                                descriptionResult.setText(articleResult.getDescription());
                                publishedTimeResult.setText(articleResult.getPublishedTime());
                                loadImage(article.getImage());
                            }
                        else{
                            Toast.makeText(InClass06.this, "No result found!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass06.this, "Response failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * save the articles from response to Article class.
     * @param string the response string.
     */
    private void saveResponseToClass(String string)
    {
        JSONObject rootJSON = null;
        try {
                rootJSON = new JSONObject(string);
                JSONArray arrayJSON = rootJSON.getJSONArray("articles");
                for (int i = 0; i < arrayJSON.length(); i++) {
                    JSONObject articleJSON = arrayJSON.getJSONObject(i);
                    article = new Article();
                    article.setTitle(articleJSON.getString("title"));
                    article.setAuthor(articleJSON.getString("author"));
                    article.setDescription(articleJSON.getString("description"));
                    article.setPublishedTime(articleJSON.getString("publishedAt"));
                    article.setImage(articleJSON.getString("urlToImage"));
                    articlesList.add(article);

                }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load image from the image url.
     */
    private void loadImage(String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(getApplicationContext())
                        .load(url)
                        .error(R.drawable.placeholder_portrait)
                        .into(image);
            }
        });
    }
}