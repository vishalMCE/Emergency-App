package com.emergencyapp.firstActivity.createAct;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.emergencyapp.R;
import com.emergencyapp.firstActivity.joinAct.Join;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class SeeListAdapterCR extends FirebaseRecyclerAdapter<Join, SeeListAdapterCR.SeeViewHolder> {

    CardView cardView;

    public SeeListAdapterCR(@NonNull FirebaseRecyclerOptions<Join> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SeeViewHolder holder, final int position, @NonNull final Join model) {
        holder.society_MemberName.setText(model.getSociety_Member_Name());
        holder.phone_Number.setText(model.getPhone_Number());
        holder.house_Number.setText(model.getHouse_Number());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=model.getSociety_Name();
                FirebaseDatabase.getInstance().getReference().child(s).child("Member")
                        .child(getRef(position).getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(SeeList, "Member is Deleter", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public SeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.secretary, parent, false);

        return new SeeViewHolder(view);
    }

    class SeeViewHolder extends RecyclerView.ViewHolder{

        TextView society_MemberName,phone_Number,house_Number;
        ImageView delete;

        public SeeViewHolder(@NonNull View itemView) {
            super(itemView);
            society_MemberName = itemView.findViewById(R.id.societyMemberName);
            phone_Number = itemView.findViewById(R.id.phoneNumber);
            house_Number = itemView.findViewById(R.id.houseNumber);
            cardView = itemView.findViewById(R.id.cardView);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
