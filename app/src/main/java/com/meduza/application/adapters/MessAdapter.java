package com.meduza.application.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.library.bubbleview.BubbleTextView;
import com.meduza.application.R;
import com.meduza.application.models.Message;

public class MessAdapter extends FirestoreRecyclerAdapter <Message, MessAdapter.MessHolder> {

    public MessAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    @NonNull
    @Override
    public MessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mess,
                parent,  false);

        return new MessAdapter.MessHolder(v2);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessHolder messHolder, int i, @NonNull Message message) {
        messHolder.userName.setText(message.getUserName());
        messHolder.textMess.setText(message.getTextMessage());

    }

    public void setOnItemClickListener(DialogAdapter.OnItemClickListener onItemClickListener) {

    }

    static class MessHolder extends RecyclerView.ViewHolder {

        TextView userName;
        BubbleTextView textMess;

        MessHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.massage_user);
            textMess = itemView.findViewById(R.id.massage_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete
                }
            });
        }

    }
}
