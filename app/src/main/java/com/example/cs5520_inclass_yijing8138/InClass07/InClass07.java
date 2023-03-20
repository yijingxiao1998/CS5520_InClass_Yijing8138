package com.example.cs5520_inclass_yijing8138.InClass07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs5520_inclass_yijing8138.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InClass07 extends AppCompatActivity implements RegisterFragment.register,
        LoginFragment.login, MeFragment.meFragmentShowing, NoteAdapter.delete {
    private TextView hintTextView;
    private Button buttonClickedToRegister, buttonClickedToLogin;
    private ConstraintLayout constraintLayoutPanel, userConstraintPanel;
    private final OkHttpClient client = new OkHttpClient();
    private final static String userBasedURL = "http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/auth";
    private final static String noteBasedURL = "http://ec2-54-164-201-39.compute-1.amazonaws.com:3000/api/note";
    private String token = null;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> notes = new ArrayList<>();
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class07);
        setTitle("InClass07");

        sharedPref = getSharedPreferences("com.example.cs5520_inclass_yijing8138", 0);
        editor = sharedPref.edit();

        constraintLayoutPanel = findViewById(R.id.rootConstraintPanel);
        userConstraintPanel = findViewById(R.id.userConstraintPanel);
        recyclerView = findViewById(R.id.recyclerView);
        buttonClickedToRegister = findViewById(R.id.buttonClickedToRegister);
        buttonClickedToLogin = findViewById(R.id.buttonClickedToLogin);
        hintTextView = findViewById(R.id.hintTextView);

        token = sharedPref.getString("login", null);
        if(token != null)
        {
            buttonClickedToLogin.setVisibility(View.INVISIBLE);
            buttonClickedToRegister.setVisibility(View.INVISIBLE);
            hintTextView.setVisibility(View.INVISIBLE);
            constraintLayoutPanel.setVisibility(View.INVISIBLE);
            userConstraintPanel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            requestInfoFromServe(token);
        }
        else {
            buttonClickedToRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constraintLayoutPanel.setVisibility(View.VISIBLE);
                    buttonClickedToLogin.setVisibility(View.INVISIBLE);
                    buttonClickedToRegister.setVisibility(View.INVISIBLE);
                    hintTextView.setVisibility(View.INVISIBLE);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.rootConstraintPanel, RegisterFragment.newInstance(), "registerFragment")
                            .commit();
                }
            });

            buttonClickedToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constraintLayoutPanel.setVisibility(View.VISIBLE);
                    buttonClickedToLogin.setVisibility(View.INVISIBLE);
                    buttonClickedToRegister.setVisibility(View.INVISIBLE);
                    hintTextView.setVisibility(View.INVISIBLE);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.rootConstraintPanel, LoginFragment.newInstance(), "loginFragment")
                            .commit();
                }
            });
        }
    }

    @Override
    public void registerSuccessfully(String name, String email, String password) {
        HttpUrl url = HttpUrl.parse(userBasedURL + "/register")
                .newBuilder()
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String string = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        token = jsonObject.getString("token");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InClass07.this, "Register successfully, "
                                        + "please go to login!", Toast.LENGTH_SHORT).show();
                                cancelRegister();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass07.this, "The user with this name and"
                                            + "/or email is already existed, please try to login!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void cancelRegister() {
        constraintLayoutPanel.setVisibility(View.INVISIBLE);
        buttonClickedToLogin.setVisibility(View.VISIBLE);
        buttonClickedToRegister.setVisibility(View.VISIBLE);
        hintTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginSuccessfully(String email, String password) {
        HttpUrl url = HttpUrl.parse(userBasedURL + "/login")
                .newBuilder()
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String string = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        token = jsonObject.getString("token");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(InClass07.this,
                                                "Login successfully!", Toast.LENGTH_SHORT)
                                                .show();

                                        editor.putString("login", token);
                                        editor.commit();

                                        constraintLayoutPanel.setVisibility(View.INVISIBLE);
                                        userConstraintPanel.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        requestInfoFromServe(token);
                                    }
                                });
                            }
                        });
                    } catch (JSONException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InClass07.this,
                                                "Wrong password, try again!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass07.this, "You are not registered yet,"
                                            + " please go to register first!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void cancelLogin() {
        constraintLayoutPanel.setVisibility(View.INVISIBLE);
        buttonClickedToLogin.setVisibility(View.VISIBLE);
        buttonClickedToRegister.setVisibility(View.VISIBLE);
        hintTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestInfoFromServe(String token) {
        HttpUrl url = HttpUrl.parse(userBasedURL + "/me")
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-access-token", token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String string = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        String id = jsonObject.getString("_id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");

                        getNotesFromServe();

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.userConstraintPanel,
                                        MeFragment.newInstance(id, name, email),
                                        "meFragment")
                                .commit();

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    public void requestLogOut() {
        token = null;
        constraintLayoutPanel.setVisibility(View.INVISIBLE);
        userConstraintPanel.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        notes = new ArrayList<>();
        editor.remove("login");
        editor.commit();
        buttonClickedToLogin.setVisibility(View.VISIBLE);
        buttonClickedToRegister.setVisibility(View.VISIBLE);
        hintTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void postButtonClicked(String note) {
        HttpUrl url = HttpUrl.parse(noteBasedURL + "/post")
                .newBuilder()
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("text", note)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-access-token", token)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String noteID = null;
                            try {
                                noteID = response.body().string();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Note newNote = new Note(note, noteID);
                            notes.add(newNote);
                            noteAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass07.this,
                                    "Post new note failed, please try again!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void getNotesFromServe()
    {
        HttpUrl url = HttpUrl.parse(noteBasedURL + "/getall")
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-access-token", token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String string = response.body().string();
                    try {
                        JSONObject rootObject = new JSONObject(string);
                        JSONArray jsonArray = rootObject.getJSONArray("notes");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            String noteText = jsonArray.getJSONObject(i).getString("text");
                            String noteID = jsonArray.getJSONObject(i).getString("_id");
                            Note noteFromArray = new Note(noteText, noteID);
                            notes.add(noteFromArray);
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layoutManager = new LinearLayoutManager(InClass07.this);
                        recyclerView.setLayoutManager(layoutManager);
                        noteAdapter = new NoteAdapter(notes, InClass07.this);
                        recyclerView.setAdapter(noteAdapter);
                    }
                });
            }
        });
    }

    @Override
    public void deleteNote(Note note) {
        HttpUrl url = HttpUrl.parse(noteBasedURL + "/delete")
                .newBuilder()
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("id", Objects.requireNonNull(note.getNoteID()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-access-token", token)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InClass07.this, "Delete note successfully!",
                                    Toast.LENGTH_SHORT).show();
                            notes.remove(note);
                            noteAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}