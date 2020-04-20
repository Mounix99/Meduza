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
import com.meduza.application.models.Note;

import io.github.ponnamkarthik.richlinkpreview.RichLinkView;
import io.github.ponnamkarthik.richlinkpreview.ViewListener;

public class DeskAdapter extends FirestoreRecyclerAdapter <Note, DeskAdapter.NoteHolder> {
    private OnItemClickListener listener;


    public DeskAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_note,
               parent,  false);

        return new NoteHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder noteHolder, int position, @NonNull Note note) {
        noteHolder.text_view_description.setText(note.getDescription());
        noteHolder.richLinkView.setLink(note.getTitle(), new ViewListener() {
            @Override
            public void onSuccess(boolean status) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    class NoteHolder extends RecyclerView.ViewHolder{

        RichLinkView richLinkView;
        TextView text_view_description;

        private NoteHolder(@NonNull View itemView) {
            super(itemView);
            text_view_description = itemView.findViewById(R.id.text_view_description);
            richLinkView = itemView.findViewById(R.id.text_view_richLinkView);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = (OnItemClickListener) listener;
    }
}
