package com.meduza.application.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.meduza.application.R;
import com.meduza.application.adapters.MessAdapter;
import com.meduza.application.models.Message;

import java.util.Objects;

public class Mess extends Fragment {
    private FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
    private CollectionReference messageRef = db.collection("MessageCollection");

    private View messView;

    private MessAdapter adapterMess;
    private FloatingActionButton sendBtn;

    private String Addressee;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        messView= inflater.inflate(R.layout.fragment_mess, container, false);

        Bundle args = getArguments();
        if (args  != null && args.containsKey("Addressee"))
            Addressee = args.getString("Addressee");

        displayAllMessages();

        final EditText text_field = messView.findViewById(R.id.massage_field);

        sendBtn = messView.findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textMassage = text_field.getText().toString();
                String userName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
                String userAddressee = Addressee;
                if(text_field.getText().toString().equals("")){
                    return;
                }
                else {
                    messageRef.add(new Message(userName,userAddressee,textMassage))
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                }
            }
        });
        return messView;
    }

    private void displayAllMessages() {

        Query query = messageRef.whereEqualTo("userName",FirebaseAuth.getInstance().getCurrentUser().getEmail()).whereEqualTo("userAddressee",Addressee);
        Query query1 = messageRef.whereEqualTo("userName",Addressee).whereEqualTo("userAddressee",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                //.whereIn("userName", Arrays.asList(Addressee,FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                //.whereIn("userAddressee",Arrays.asList(FirebaseAuth.getInstance().getCurrentUser().getEmail(),Addressee))
                //.whereEqualTo("userAddressee",FirebaseAuth.getInstance().getCurrentUser().getEmail())
                //.whereEqualTo("userName", Addressee
                //.whereEqualTo("userAddressee",Addressee)
                ;

        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setQuery(query1, Message.class)
                .build();

        adapterMess = new MessAdapter(options);


        RecyclerView recyclerView1 = messView.findViewById(R.id.message_recycle_view);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(adapterMess);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterMess.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterMess.stopListening();
    }
}
