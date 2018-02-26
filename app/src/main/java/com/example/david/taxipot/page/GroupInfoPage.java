package com.example.david.taxipot.page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.david.taxipot.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class GroupInfoPage extends AppCompatActivity {

    TextView title,stp,dtn,pax,edate,btime;
    String groupID;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        title = findViewById(R.id.group_info_title);
        stp = findViewById(R.id.group_info_stp);
        dtn = findViewById(R.id.group_info_pax);
        pax = findViewById(R.id.group_info_pax);
        edate = findViewById(R.id.group_info_epd);
        btime = findViewById(R.id.group_info_bdt);

        groupID = getIntent().getStringExtra("groupID");













    }
}
