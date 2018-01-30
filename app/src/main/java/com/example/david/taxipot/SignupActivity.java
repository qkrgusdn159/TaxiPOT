package com.example.david.taxipot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.david.taxipot.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SignupActivity extends AppCompatActivity {

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private TextInputEditText name,email,password;
    private Button signup;
    String intro_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        intro_background = mFirebaseRemoteConfig.getString("intro_background"); // Firebase Remote Config 원격 배경색 관리

        getWindow().setStatusBarColor(Color.parseColor(intro_background)); // 상태바 색상

        name = (TextInputEditText) findViewById(R.id.SignupActivity_edittext_name);
        email = (TextInputEditText) findViewById(R.id.SignupActivity_edittext_id);
        password = (TextInputEditText) findViewById(R.id.SignupActivity_edittext_password);
        signup = (Button) findViewById(R.id.SignupActivity_button_signup);
        signup.setBackgroundColor(Color.parseColor(intro_background));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString() == null || name.getText().toString() == null || password.getText().toString() == null){
                    return;
                }

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                UserModel userModel = new UserModel();
                                userModel.userName = name.getText().toString();

                                String uid = task.getResult().getUser().getUid();
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);

                            }
                        });
            }
        });
    }
}
