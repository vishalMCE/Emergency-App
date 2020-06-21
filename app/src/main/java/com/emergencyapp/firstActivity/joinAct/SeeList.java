package com.emergencyapp.firstActivity.joinAct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;

import com.emergencyapp.R;
import com.emergencyapp.firstActivity.createAct.SeeListAdapterCR;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SeeList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeeListAdapter adapter;
    private SeeListAdapterCR adapterCR;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_list);
        setTitle("Society Members");

        SharedPreferences soName = getSharedPreferences("societyName",MODE_PRIVATE);
        String value1 = soName.getString("str1","");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Join> options =
                new FirebaseRecyclerOptions.Builder<Join>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(value1).child("Member"), Join.class)
                        .build();

        SharedPreferences getShared = getSharedPreferences("join",MODE_PRIVATE);
        value = getShared.getString("str","Save a note and it will show up here");

        if (value.compareTo("1") == 0) {
            adapter = new SeeListAdapter(options);
            recyclerView.setAdapter(adapter);
        }
        else if (value.compareTo("2") == 0) {
            adapterCR = new SeeListAdapterCR(options);
            recyclerView.setAdapter(adapterCR);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (value.compareTo("1") == 0) {
            adapter.startListening();
        }
        else if (value.compareTo("2") == 0){
            adapterCR.startListening();;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (value.compareTo("1") == 0) {
            adapter.stopListening();
        }
        else if (value.compareTo("2") == 0){
            adapterCR.stopListening();
        }
    }
}