package com.example.david.taxipot;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.david.taxipot.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class SignupActivity extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 10;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private EditText name, email, password;
    private Button signup;
    private String intro_background;
    private StorageReference mStorageRef;
    private ImageView profile;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mStorageRef = FirebaseStorage.getInstance().getReference(); // storage 프로필사진 저장소

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        intro_background = mFirebaseRemoteConfig.getString("intro_background"); // Firebase Remote Config 원격 배경색 관리

        getWindow().setStatusBarColor(Color.parseColor(intro_background)); // 상태바 색상

        profile = findViewById(R.id.SignupActivity_imangeview_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        name = findViewById(R.id.SignupActivity_edittext_name);
        email = findViewById(R.id.SignupActivity_edittext_id);
        password = findViewById(R.id.SignupActivity_edittext_password);
        signup = findViewById(R.id.SignupActivity_button_signup);
        signup.setBackgroundColor(Color.parseColor(intro_background));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString() == null || name.getText().toString() == null || password.getText().toString() == null) {
                    return;
                }

                if (imageUri == null) {
                    Toast.makeText(getApplicationContext(), "프로필 이미지를 설정해주세요",
                            Toast.LENGTH_LONG).show();

                } else {
                    FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    final String uid = task.getResult().getUser().getUid();

                                    FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            String imageUrl = task.getResult().getDownloadUrl().toString();

                                            UserModel userModel = new UserModel();
                                            userModel.userName = name.getText().toString();
                                            userModel.profileImageUrl = imageUrl;

                                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel,new DatabaseReference.CompletionListener(){

                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                    finish();
                                                }
                                            }); //db 유저 정보

                                        }
                                    }); // db 프로필 사진 저장


                                }
                            });
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            profile.setImageURI(data.getData()); // 회원가입 창의 가운데 이미지 뷰를 바꿈
            imageUri = data.getData(); // 원본 이미지 경로
        }
    }
}
