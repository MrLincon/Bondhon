package com.matrimony.bd.Models;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.matrimony.bd.Activity.ArticleDetailsActivity;
import com.matrimony.bd.R;

import java.util.HashMap;
import java.util.Map;

public class CommentAdapter extends FirestorePagingAdapter<Comment, CommentAdapter.CommentHolder> {

    private OnItemClickListener listener;
    private Context mContext;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public CommentAdapter(@NonNull FirestorePagingOptions<Comment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final CommentHolder holder, int position, @NonNull Comment model) {
        final String post_id = getItem(position).getId();
        final String user_id = firebaseAuth.getCurrentUser().getUid();

        Dialog popup = new Dialog(mContext);

        holder.Name.setText(model.getName());
        holder.Comment.setText(model.getComment());

        holder.Control.setVisibility(View.GONE);

        //Like Features
        holder.Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("post_id", post_id);
                Log.d("user_id", user_id);
                firebaseFirestore.collection("Comments").document(post_id).collection("Like").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {

                            Map<String, Object> likes = new HashMap<>();
                            likes.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Comments").document(post_id).collection("Like").document(user_id).set(likes);

                        } else {

                            firebaseFirestore.collection("Comments").document(post_id).collection("Like").document(user_id).delete();

                        }
                    }
                });

            }
        });

        //Update Like icon
        firebaseFirestore.collection("Comments").document(post_id).collection("Like").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    if (documentSnapshot.exists()) {

                        holder.Like.setImageResource(R.drawable.ic_like_selected);

                    } else {

                        holder.Like.setImageResource(R.drawable.ic_like);

                    }
                }


            }
        });

        //Get Likes Count
        firebaseFirestore.collection("Comments").document(post_id).collection("Like").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    String count = String.valueOf(documentSnapshots.size());
                    holder.Like_count.setText(count);
                } else {
                    holder.Like_count.setText("0");
                }
            }
        });

        //Show controls
        firebaseFirestore.collection("Comments").document(post_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String ID = documentSnapshot.getString("user_id");

                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String currentUserID = firebaseAuth.getCurrentUser().getUid();

                Log.d("Control", "onSuccess: "+currentUserID);
                Log.d("Control", "onSuccess: "+ID);

                if (currentUser != null) {
                    if (documentSnapshot.exists()) {

                        if (ID.equals(currentUserID)){
                            holder.Control.setVisibility(View.VISIBLE);
                        }else {
                            holder.Control.setVisibility(View.GONE);
                        }
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_edit_post);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EditText comment = popup.findViewById(R.id.et_details);
                LinearLayout save = popup.findViewById(R.id.save);
                LinearLayout delete = popup.findViewById(R.id.delete);


                firebaseFirestore.collection("Comments").document(post_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            String Comment = documentSnapshot.getString("comment");
                            comment.setText(Comment);


                        } else {
                            Toast.makeText(mContext, "Something wrong!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Comment = comment.getText().toString();


                        if (!Comment.isEmpty()) {

                            Map<String, Object> userMap = new HashMap<>();

                            userMap.put("comment", Comment);
                            firebaseFirestore.collection("Comments").document(post_id).update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(mContext, "Adding...", Toast.LENGTH_SHORT).show();
                                    popup.dismiss();
                                    refresh();
                                }
                            });
                        } else {
                            Toast.makeText(mContext, "Field can not be empty!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        firebaseFirestore.collection("Comments").document(post_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if (documentSnapshot.exists()) {

                                    String PostId = documentSnapshot.getString("post_id");

                                    firebaseFirestore.collection("Comments").document(post_id).delete();

                                    firebaseFirestore.collection("Comments").document(post_id).collection("Like").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                                                        firebaseFirestore.collection("Comments").document(post_id)
                                                                .collection("Like").document(snapshot.getId()).delete();
                                                    }
                                                }
                                            });

                                    CollectionReference itemsRef = firebaseFirestore.collection("Article").document(PostId).collection("Comment");
                                    Query query = itemsRef.whereEqualTo("id", post_id);
                                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot document : task.getResult()) {
                                                    itemsRef.document(document.getId()).delete();
                                                }
                                            }
                                        }
                                    });

                                    Toast.makeText(mContext, "Deleting...", Toast.LENGTH_SHORT).show();
                                    popup.dismiss();
                                    refresh();

                                } else {
                                    Toast.makeText(mContext, "Something wrong!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }
                });

                popup.show();

            }
        });

    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,
                parent, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        return new CommentHolder(view);
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        TextView Name, Comment, Like_count;
        ImageView Like, More;
        LinearLayout Control;

        public CommentHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Comment = itemView.findViewById(R.id.comment);
            Like = itemView.findViewById(R.id.like);
            Like_count = itemView.findViewById(R.id.like_count);
            More = itemView.findViewById(R.id.more);
            Control = itemView.findViewById(R.id.control);


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
