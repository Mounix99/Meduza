package com.meduza.application.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.meduza.application.adapters.DeskAdapter;
import com.meduza.application.models.Note;


public class Desk extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("DeskBook");

    private DeskAdapter adapter;
    private View rootView;

    private EditText edit_text_title;
    private EditText edit_text_description;
    private CheckBox access_check;


    private LinearLayout add_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_desk, container, false);


        FloatingActionButton add_btn = rootView.findViewById(R.id.add_button);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddWindow();
            }
        });

        setUpRecyclerView();

        return rootView;
    }

    private void ShowAddWindow(){
        @SuppressLint("UseRequireInsteadOfGet") AlertDialog.Builder dialogAdd;
        dialogAdd = new AlertDialog.Builder(requireActivity());
        dialogAdd.setTitle("Add your element");
        dialogAdd.setMessage("Fill the fields");

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View new_note_window = inflater.inflate(R.layout.new_note_window, null);
        dialogAdd.setView(new_note_window);

        add_layout = rootView.findViewById(R.id.add_layout);

        edit_text_title = new_note_window.findViewById(R.id.edit_text_title);
        edit_text_description = new_note_window.findViewById(R.id.edit_text_description);
        access_check = new_note_window.findViewById(R.id.access_check);




        dialogAdd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialogAdd.setPositiveButton("Add", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                final String title = edit_text_title.getText().toString();
                final String description = edit_text_description.getText().toString();
                final String userName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                boolean access = false;
                if (access_check.isChecked()) access = true;


                if (TextUtils.isEmpty(title)) {
                    Snackbar.make(rootView, "Enter your Title", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    Snackbar.make(rootView, "Enter Description", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //Adding element
                notebookRef.add(new Note(title, description, userName, access))
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar.make(rootView, "Note has been added", Snackbar.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootView, "Note has NOT been added", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialogAdd.show();
    }


    private void setUpRecyclerView() {
        Query query = notebookRef.whereEqualTo("userName", FirebaseAuth.getInstance().getCurrentUser().getEmail());

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        adapter = new DeskAdapter(options);

        RecyclerView recyclerView = rootView.findViewById(R.id.note_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new DeskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                Snackbar.make(rootView, "Position:" + position + " ID:" + id, Snackbar.LENGTH_SHORT).show();
            }
        });
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
