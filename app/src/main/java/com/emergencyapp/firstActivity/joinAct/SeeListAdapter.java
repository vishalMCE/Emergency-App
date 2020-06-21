package com.emergencyapp.firstActivity.joinAct;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.emergencyapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SeeListAdapter extends FirebaseRecyclerAdapter<Join, SeeListAdapter.SeeViewHolder> {

    CardView cardView;

    public SeeListAdapter(@NonNull FirebaseRecyclerOptions<Join> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SeeViewHolder holder, int position, @NonNull Join model) {
        holder.society_MemberName.setText(model.getSociety_Member_Name());
        holder.phone_Number.setText(model.getPhone_Number());
        holder.house_Number.setText(model.getHouse_Number());
    }

    @NonNull
    @Override
    public SeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member, parent, false);

        return new SeeViewHolder(view);
    }

    class SeeViewHolder extends RecyclerView.ViewHolder{

        TextView society_MemberName,phone_Number,house_Number;

        public SeeViewHolder(@NonNull View itemView) {
            super(itemView);
            society_MemberName = itemView.findViewById(R.id.societyMemberName);
            phone_Number = itemView.findViewById(R.id.phoneNumber);
            house_Number = itemView.findViewById(R.id.houseNumber);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
