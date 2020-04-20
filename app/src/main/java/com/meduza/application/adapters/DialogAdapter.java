package com.meduza.application.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.meduza.application.R;
import com.meduza.application.models.Message;

public class DialogAdapter extends FirestoreRecyclerAdapter <Message, DialogAdapter.MessageHolder> {
    private OnItemClickListener listenerDialog;

    public DialogAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }


    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dialog,
                parent,  false);

        return new MessageHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder messageHolder, int i, @NonNull Message message) {
        messageHolder.user_addressee.setText(message.getUserAddressee());

    }

    class MessageHolder extends RecyclerView.ViewHolder {

        TextView user_addressee;

        MessageHolder(@NonNull View itemView) {
            super(itemView);

            user_addressee = itemView.findViewById(R.id.user_addressee);

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
