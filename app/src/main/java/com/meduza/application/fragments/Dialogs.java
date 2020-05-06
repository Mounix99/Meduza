package com.meduza.application.fragments;

import android.annotation.SuppressLint;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.meduza.application.R;
import com.meduza.application.adapters.DialogAdapter;
import com.meduza.application.models.Dialog;
import com.meduza.application.models.Message;

import java.util.ArrayList;
import java.util.Objects;

public class Dialogs extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference messageRef = db.collection("DialogCollection");

    private DialogAdapter adapter;
    private View dialogView;

    private EditText edit_text_create_dialog;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogView = inflater.inflate(R.layout.fragment_dialog, container, false);

        FloatingActionButton add_dialog_btn = dialogView.findViewById(R.id.add_dialog_button);
        add_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateDialog();
            }
        });

        setUpDialogRecyclerView();

        return dialogView;
    }

    private void setUpDialogRecyclerView() {
        Query query = messageRef
                .whereArrayContains("users", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                ;

        FirestoreRecyclerOptions<Dialog> options = new FirestoreRecyclerOptions.Builder<Dialog>()
                .setQuery(query, Dialog.class)
                .build();

        adapter = new DialogAdapter(options);

        RecyclerView recyclerView = dialogView.findViewById(R.id.dialog_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String dialog = documentSnapshot.getId();
                Fragment newFragment = new Mess();
                final Bundle bundle = new Bundle();
                bundle.putString("dialog", dialog);
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = requireFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();

            }
        });
    }

    private void showCreateDialog(){
        AlertDialog.Builder dialogDialog;
        dialogDialog = new AlertDialog.Builder(requireActivity());
        dialogDialog.setCancelable(false);
        dialogDialog.setTitle("Add dialog");
        dialogDialog.setMessage("Fill the field");
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialog_window = inflater.inflate(R.layout.window_create_dialog, null);
        dialogDialog.setView(dialog_window);

        edit_text_create_dialog = dialog_window.findViewById(R.id.edit_text_create_dialog);


        dialogDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String second_user = edit_text_create_dialog.getText().toString();

                if (TextUtils.isEmpty(second_user)) {
                    Snackbar.make(dialogView, "Enter user Email", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<String> users = new ArrayList<>();
                users.add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                users.add(second_user);

                messageRef.add(new Dialog(users))
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
        });
        dialogDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialogDialog.show();
    }



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
