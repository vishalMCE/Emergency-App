package com.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.emergencyapp.firstActivity.createAct.Create;
import com.emergencyapp.firstActivity.joinAct.Join;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SendSms extends AppCompatActivity {

    ImageButton send;
    EditText editText;
    DatabaseReference reference,reference1;
    String value1,value2,number,message;
    Join join = new Join();
    final ArrayList<String> listPhoneNumber = new ArrayList<>();
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        send = findViewById(R.id.sen);
        editText = findViewById(R.id.mes);


        final SharedPreferences soName = getSharedPreferences("societyName",MODE_PRIVATE);
        value1 = soName.getString("str1","");

        SharedPreferences ponumber = getSharedPreferences("phonenumber",MODE_PRIVATE);
        value2 = ponumber.getString("str4","");

        reference = FirebaseDatabase.getInstance().getReference().child(value1).child("Member");
        editText.setText(join.getPhone_Number());

        SharedPreferences getShared = getSharedPreferences("join",MODE_PRIVATE);
        value = getShared.getString("str","Save a note and it will show up here");

        if (value.compareTo("1") == 0) {
            reference1 = FirebaseDatabase.getInstance().getReference().child(value1).child("Secretary").child("Name");
            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Join join = dataSnapshot.getValue(Join.class);
                    String s = join.getPhone_Number();
                    listPhoneNumber.add(s);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Join join = snapshot.getValue(Join.class);
                    String s = join.getPhone_Number();
                    if (value2.compareTo(s)!=0)
                    listPhoneNumber.add(s);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

    }

    //Send SmS
    public void sendSms() {

        int permissionCheck = ContextCompat.checkSelfPermission(SendSms.this, Manifest.permission.SEND_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            message = editText.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(SendSms.this, "Enter Message", Toast.LENGTH_SHORT).show();
            }

            else {
                SmsManager smsManager = SmsManager.getDefault();
                for (int i = 0; i<listPhoneNumber.size(); ++i) {
                    number = listPhoneNumber.get(i);
                    smsManager.sendTextMessage(number, null, message, null, null);
                }
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            ActivityCompat.requestPermissions(SendSms.this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                } else {
                    Toast.makeText(this, "You don't have Required Permission to make this Action", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}