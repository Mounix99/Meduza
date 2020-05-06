package com.meduza.application.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.meduza.application.R;
import com.meduza.application.models.Dialog;

public class DialogAdapter extends FirestoreRecyclerAdapter <Dialog, DialogAdapter.DialogHolder> {
    private OnItemClickListener listenerDialog;

    public DialogAdapter(@NonNull FirestoreRecyclerOptions<Dialog> options) {
        super(options);
    }



    @NonNull
    @Override
    public DialogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dialog,
                parent,  false);

        return new DialogHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogHolder dialogHolder, int i, @NonNull Dialog dialog) {
        dialogHolder.users.setText(dialog.getUsers().toString());

    }

    class DialogHolder extends RecyclerView.ViewHolder {

        TextView users;

        DialogHolder(@NonNull View itemView) {
            super(itemView);

            users = itemView.findViewById(R.id.user_addressee);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listenerDialog != null){
                        listenerDialog.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(DialogAdapter.OnItemClickListener listener) {
        this.listenerDialog = (DialogAdapter.OnItemClickListener) listener;
    }
}
