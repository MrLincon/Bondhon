package com.matrimony.bd.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.matrimony.bd.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.HashMap;
import java.util.Map;

public class ArticleDetailsAdapter extends FirestorePagingAdapter<Article, ArticleDetailsAdapter.ArticleHolder> {

    private OnItemClickListener listener;
    private Context mContext;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public ArticleDetailsAdapter(@NonNull FirestorePagingOptions<Article> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ArticleHolder holder, int position, @NonNull Article model) {
        final String post_id = getItem(position).getId();
        final String user_id = firebaseAuth.getCurrentUser().getUid();

        holder.Title.setText(model.getTitle());
        holder.description.setText(model.getDesc());

        holder.Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Add Likes
                firebaseFirestore.collection("Article").document(post_id).collection("Like").document(user_id).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (!task.getResult().exists()) {

                                    Map<String, Object> favourite = new HashMap<>();
                                    favourite.put("timestamp", FieldValue.serverTimestamp());

                                    firebaseFirestore.collection("Article/" + post_id + "/Like").document(user_id).set(favourite);
                                    firebaseFirestore.collection("Article").document(post_id).update("LikeList", FieldValue.arrayUnion(user_id));

                                } else {

                                    firebaseFirestore.collection("Article").document(post_id).collection("Like").document(user_id).delete();
                                    firebaseFirestore.collection("Article").document(post_id).update("LikeList", FieldValue.arrayRemove(user_id));

                                }
                            }
                        });
            }
        });


        //Update Likes icon
        firebaseFirestore.collection("Article").document(post_id).collection("Like").document(user_id)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                        if (documentSnapshot.exists()) {

                            holder.Like.setImageResource(R.drawable.ic_like_selected);

                        } else {

                            holder.Like.setImageResource(R.drawable.ic_like);

                        }

                    }
                });


        //Get Likes Count
        firebaseFirestore.collection("Article").document(post_id).collection("Like").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if(!documentSnapshots.isEmpty()){

                    String count = String.valueOf(documentSnapshots.size());
                    holder.Like_count.setText(count);
                } else {
                    holder.Like_count.setText("0");
                }
            }
        });

        //Get views Count
        firebaseFirestore.collection("Article").document(post_id).collection("View").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {
                    String count = String.valueOf(documentSnapshots.size());
                    holder.Views_count.setText(count);
                } else {
                    holder.Views_count.setText("0");
                }
            }
        });

        //Get comments Count
        firebaseFirestore.collection("Article").document(post_id).collection("Comment").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {
                    String count = String.valueOf(documentSnapshots.size());
                    holder.Comment_count.setText(count);
                } else {
                    holder.Comment_count.setText("0");
                }
            }
        });

    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card_layout,
                parent, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        return new ArticleHolder(view);
    }

    class ArticleHolder extends RecyclerView.ViewHolder {

        TextView Title, Like_count,Comment_count, Views_count;
        ImageView Like, Comment;
        TextView description;

        public ArticleHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            Like = itemView.findViewById(R.id.like);
            Comment = itemView.findViewById(R.id.comment);
            Like_count = itemView.findViewById(R.id.like_count);
            Comment_count = itemView.findViewById(R.id.comments_count);
            Views_count = itemView.findViewById(R.id.views_count);
            description = itemView.findViewById(R.id.description);

            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
