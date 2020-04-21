package com.meduza.application.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meduza.application.R;
import com.meduza.application.models.User;

public class Profile extends Fragment {

    private FirebaseDatabase db;
    private DatabaseReference users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);



        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        final TextView name_surname = profileView.findViewById(R.id.name_sername_profile);
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name_surname.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView email_text = profileView.findViewById(R.id.email_profile);
        email_text.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


        setProfileData();

        return profileView;
    }

    private void setProfileData() {



    }
}
