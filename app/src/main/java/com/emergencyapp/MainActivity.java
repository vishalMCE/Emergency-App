package com.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emergencyapp.firstActivity.FirstActivity;
import com.emergencyapp.firstActivity.joinAct.Join;
import com.emergencyapp.firstActivity.joinAct.JoinSociety;
import com.emergencyapp.firstActivity.joinAct.SeeList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {

    TextView txtsocietyName;
    TextView txtSocietyMemberName;
    TextView txthouseNumber;
    String value,value1,value2,value3,value4;
    FloatingActionButton seelist;
    ImageButton button;


    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the Value of shared preference back
        SharedPreferences getShared = getSharedPreferences("join",MODE_PRIVATE);
        value = getShared.getString("str","Save a note and it will show up here");
        if(value.length()>2)
        value = "0";

        seelist = findViewById(R.id.floatingActionButton);
        txtsocietyName = findViewById(R.id.societyName);
        txtSocietyMemberName = findViewById(R.id.societyMemberName);
        txthouseNumber = findViewById(R.id.houseNumber);

        //Showing data in MainActivity
        SharedPreferences soName = getSharedPreferences("societyName",MODE_PRIVATE);
        value1 = soName.getString("str1","");
        txtsocietyName.setText(value1);
        SharedPreferences socimemName = getSharedPreferences("societyMemberName",MODE_PRIVATE);
        value2 = socimemName.getString("str2","");
        txtSocietyMemberName.setText(value2);
        final SharedPreferences honumber = getSharedPreferences("houseNumber",MODE_PRIVATE);
        value3 = honumber.getString("str3","");
        txthouseNumber.setText(value3);
        SharedPreferences ponumber = getSharedPreferences("phonenumber",MODE_PRIVATE);
        value4 = ponumber.getString("str4","");



//        if (value.compareTo("1") == 0) {
//            final ArrayList<String> listPhoneNumber = new ArrayList<>();
//            DatabaseReference reference;
//            reference = FirebaseDatabase.getInstance().getReference().child(value1).child("Member");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Join join = snapshot.getValue(Join.class);
//                        String s = join.getPhone_Number();
//                        listPhoneNumber.add(s);
//                        System.out.println(s);
//                    }
//
//                    int check = 0;
//                    for (int i=0; i<listPhoneNumber.size(); i++){
//                        String number = listPhoneNumber.get(i);
//                        if(number.compareTo(value4) != 0){
//                            check = 1;
//                        }
//                        else {
//                            check = 0;
//                            break;
//                        }
//                    }
//                    if (check == 1){
//                        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
//                        startActivity(intent);
//                        finish();
//                        check = 0;
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        }


        if(Integer.parseInt(value) == 0){
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
            finish();
        }



        seelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeeList.class);
                startActivity(intent);
            }
        });


        button = findViewById(R.id.btnbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SendSms.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (value.compareTo("1")==0) {
            if (item.getItemId() == R.id.action_logout) {

                Intent intent = new Intent(this, FirstActivity.class);
                startActivity(intent);
                finish();

                SharedPreferences shrd = getSharedPreferences("join", MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("str", "Save a note and it will show up here");
                editor.apply();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = reference.child(value1).child("Member").orderByChild("phone_Number").equalTo(value4);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
        else if (value.compareTo("2")==0){
            if (item.getItemId() == R.id.action_logout) {
                Intent intent = new Intent(this, FirstActivity.class);
                startActivity(intent);
                finish();



                SharedPreferences shrd = getSharedPreferences("join", MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("str", "Save a note and it will show up here");
                editor.apply();


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = reference.child(value1);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_logout);
        if (value.compareTo("2")==0){
            item.setTitle("Delete Society");
        }
        return super.onPrepareOptionsMenu(menu);
    }
}