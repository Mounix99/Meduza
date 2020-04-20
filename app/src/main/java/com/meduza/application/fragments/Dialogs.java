package com.meduza.application.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.meduza.application.R;
import com.meduza.application.adapters.DialogAdapter;
import com.meduza.application.models.Message;

import java.util.Objects;


public class Dialogs extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference messageRef = db.collection("MessageCollection");

    private DialogAdapter adapter;
    private View dialogView;

    private String Addressee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogView = inflater.inflate(R.layout.fragment_dialog, container, false);

        FloatingActionButton add_dialog_btn = dialogView.findViewById(R.id.add_dialog_button);
        add_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setUpDialogRecyclerView();

        return dialogView;
    }

    private void setUpDialogRecyclerView() {
        Query query = messageRef
                .whereEqualTo("userName", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        adapter = new DialogAdapter(options);

        RecyclerView recyclerView = dialogView.findViewById(R.id.dialog_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Addressee = (String) documentSnapshot.get("userAddressee");
                Fragment newFragment = new Mess();
                final Bundle bundle = new Bundle();
                bundle.putString("Addressee", Addressee);
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = requireFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
            }
        });
    }

    /*private void showDialog(){
        //adapterMess.startListening();
        @SuppressLint("UseRequireInsteadOfGet")
        AlertDialog.Builder dialogDialog;
        dialogDialog = new AlertDialog.Builder(requireActivity());
        dialogDialog.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View dialog_window = inflater.inflate(R.layout.fragment_mess, null);
        dialogDialog.setView(dialog_window);

        //RelativeLayout activity_massage_start = dialogView.findViewById(R.id.activity_massage_start);

        dialogDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialogDialog.show();
    }*/



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
