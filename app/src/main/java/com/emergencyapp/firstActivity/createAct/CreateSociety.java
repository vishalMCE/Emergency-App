package com.emergencyapp.firstActivity.createAct;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emergencyapp.MainActivity;
import com.emergencyapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateSociety extends AppCompatActivity {

    EditText societyName, secretaryName,houseNumber,phoneNumber;
    Button create;
    DatabaseReference reference;
    Create member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_society);

        societyName = findViewById(R.id.societyName);
        secretaryName = findViewById(R.id.secretaryName);
        houseNumber = findViewById(R.id.houseNumber);
        phoneNumber = findViewById(R.id.phoneNumber);
        create = findViewById(R.id.Create);

        member = new Create();
        reference = FirebaseDatabase.getInstance().getReference();


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setSociety_Name(societyName.getText().toString().trim());
                member.setSecretary_Name(secretaryName.getText().toString().trim());
                member.setHouse_Number(houseNumber.getText().toString().trim());
                member.setPhone_Number(phoneNumber.getText().toString().trim());

                String s = societyName.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference().child((s)).child("Secretary");

                reference.child("Name").setValue(member);

                Toast.makeText(CreateSociety.this, "Create Successfully", Toast.LENGTH_SHORT).show();

                SharedPreferences shrd = getSharedPreferences("join",MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("str","2");
                editor.apply();

                //Society Name save in rom
                SharedPreferences sociName = getSharedPreferences("societyName",MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sociName.edit();
                editor1.putString("str1",s);
                editor1.apply();

                String s1 = secretaryName.getText().toString();
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

                Intent intent = new Intent(CreateSociety.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}