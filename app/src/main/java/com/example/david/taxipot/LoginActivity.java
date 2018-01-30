package com.example.david.taxipot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private Button signup;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        String intro_background = mFirebaseRemoteConfig.getString("intro_background"); // Firebase Remote Config 원격 배경색 관리

        getWindow().setStatusBarColor(Color.parseColor(intro_background)); // 상태바 색상

        login = (Button)findViewById(R.id.LoginActivity_button_login);
        signup = (Button)findViewById(R.id.LoginActivity_button_signup);

        login.setBackgroundColor(Color.parseColor(intro_background));
        signup.setBackgroundColor(Color.parseColor(intro_background));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });




    }
}
