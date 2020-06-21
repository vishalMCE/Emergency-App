package com.emergencyapp.firstActivity.joinAct;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.emergencyapp.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.emergencyapp.R;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinSociety extends AppCompatActivity {

    EditText societyName, societyMemberName,houseNumber,phoneNumber;
    Button join;
    DatabaseReference reference;
    Join member;
    DatabaseReference reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_society);

        societyName = findViewById(R.id.societyName);
        societyMemberName = findViewById(R.id.societyMemberName);
        houseNumber = findViewById(R.id.houseNumber);
        phoneNumber = findViewById(R.id.phoneNumber);
        join = findViewById(R.id.join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String socname = societyName.getText().toString();
                reference1 = FirebaseDatabase.getInstance().getReference();

                reference1.orderByKey().equalTo(socname).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(JoinSociety.this);
                            if (!prefs.getBoolean("firstTime", false)) {
                                // <---- run your one time code here

                                fun();
                                // mark first time has ran.
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("firstTime", true);
                                editor.commit();
                            }
                        } else {
                            Toast.makeText(JoinSociety.this, "Society Does't Exits", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public void fun(){
        member = new Join();

                member.setSociety_Name(societyName.getText().toString().trim());
                member.setSociety_Member_Name(societyMemberName.getText().toString().trim());
                member.setHouse_Number(houseNumber.getText().toString().trim());
                member.setPhone_Number(phoneNumber.getText().toString().trim());

                String s = societyName.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference().child((s)).child("Member");

                reference.push().setValue(member);

                Toast.makeText(JoinSociety.this, "Joined Successfully", Toast.LENGTH_SHORT).show();


                SharedPreferences shrd = getSharedPreferences("join",MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("str","1");
                editor.apply();

                //Society Name save in rom
                SharedPreferences sociName = getSharedPreferences("societyName",MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sociName.edit();
                editor1.putString("str1",s);
                editor1.apply();

                String s1 = societyMemberName.getText().toString();
                SharedPreferences socimemName = getSharedPreferences("societyMemberName",MODE_PRIVATE);
                SharedPreferences.Editor editor2 = socimemName.edit();
                editor2.putString("str2",s1);
                editor2.apply();

                String s2 = houseNumber.getText().toString();
                SharedPreferences honumber = getSharedPreferences("houseNumber",MODE_PRIVATE);
                SharedPreferences.Editor editor3 = honumber.edit();
                editor3.putString("str3",s2);
                editor3.apply();

                String s3 = phoneNumber.getText().toString();
                SharedPreferences ponumber = getSharedPreferences("phonenumber",MODE_PRIVATE);
                SharedPreferences.Editor editor4 = ponumber.edit();
                editor4.putString("str4",s3);
                editor4.apply();

                Intent intent = new Intent(JoinSociety.this,MainActivity.class);
                startActivity(intent);
                finish();
    }
}