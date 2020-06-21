package com.emergencyapp.firstActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.emergencyapp.R;
import com.emergencyapp.firstActivity.createAct.CreateSociety;
import com.emergencyapp.firstActivity.joinAct.JoinSociety;

public class FirstActivity extends AppCompatActivity {

    Button create;
    Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        create = findViewById(R.id.Create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, CreateSociety.class);
                startActivity(intent);

            }
        });

        join = findViewById(R.id.Join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, JoinSociety.class);
                startActivity(intent);
            }
        });


    }
}
