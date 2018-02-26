package com.example.david.taxipot.page;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.david.taxipot.R;
import com.example.david.taxipot.model.GroupModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterGroupPage extends AppCompatActivity {

    EditText r_title;
    EditText r_startingpoint;
    EditText r_destination;
    EditText r_expirationdate;
    EditText r_pax;
    EditText r_boardingtime;

    String title,startingpoint,destination,expirationdate,pax,boardingtime;

    Button register;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_group);

        r_title = findViewById(R.id.registergroup_title);
        r_startingpoint = findViewById(R.id.registergroup_startingpoint);
        r_destination = findViewById(R.id.registergroup_destination);
        r_expirationdate = findViewById(R.id.registergroup_expirationdate);
        r_pax = findViewById(R.id.registergroup_pax);
        r_boardingtime = findViewById(R.id.registergroup_boardingtime);

        register = findViewById(R.id.register_button);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = r_title.getText().toString();
                startingpoint = r_startingpoint.getText().toString();
                destination = r_destination.getText().toString();
                expirationdate = r_expirationdate.getText().toString();
                pax = r_pax.getText().toString();
                boardingtime = r_pax.getText().toString();

                GroupModel groupModel = new GroupModel();
                groupModel.boardingTime = boardingtime;
                groupModel.destination = destination;
                groupModel.expirationDate = expirationdate;
                groupModel.groupTitle = title;
                groupModel.pax = pax;
                groupModel.startingPoint = startingpoint;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid().toString();

                groupModel.groupID = uid;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("TaxiGroups").child(uid).setValue(groupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            finish();
                        }
                    }
                });



            }
        });




    }
}
