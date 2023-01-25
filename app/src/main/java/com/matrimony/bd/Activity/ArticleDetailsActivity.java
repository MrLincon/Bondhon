package com.matrimony.bd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.matrimony.bd.Fragments.FragmentArticle;
import com.matrimony.bd.Models.ArticleDetailsAdapter;
import com.matrimony.bd.Models.Comment;
import com.matrimony.bd.Models.CommentAdapter;
import com.matrimony.bd.R;

import java.util.HashMap;
import java.util.Map;

public class ArticleDetailsActivity extends AppCompatActivity {

    TextView Title, Description, Like_count,Comment_count, Views_count;
    ImageView Like, Send;
    EditText Comment;
    LinearLayout Control;

    String title, description;

    private RecyclerView recyclerView;

    public String item_id;

    Dialog editPost;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference document_reference, document_ref, doc_ref;

    private CommentAdapter adapter;

    public static final String EXTRA_ID = "com.matrimony.bd.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        Title = findViewById(R.id.title);
        Like = findViewById(R.id.like);
        Like_count = findViewById(R.id.like_count);
        Comment_count = findViewById(R.id.comments_count);
        Views_count = findViewById(R.id.views_count);
        Description = findViewById(R.id.description);
        Comment = findViewById(R.id.comment);
        Send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.recyclerView);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();
        document_ref = db.collection("userDetails").document(userID);
        doc_ref = db.collection("Comments").document();
        firebaseFirestore = FirebaseFirestore.getInstance();

        final Intent intent = getIntent();
        item_id = intent.getStringExtra(FragmentArticle.EXTRA_ID);

        document_reference = db.collection("Article").document(item_id);

        editPost = new Dialog(this);

        loadData();
        loadComments();
        likeFeatures();
        commentFeatures();
        viewFeatures();

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userComment();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorGreenLite));
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void loadData() {
        document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    title = documentSnapshot.getString("title");
                    description = documentSnapshot.getString("desc");

//                    if (User_ID.equals(userID)) {
//                        delete.setVisibility(View.VISIBLE);
//                        edit.setVisibility(View.VISIBLE);
//                    } else {
//                        delete.setVisibility(View.GONE);
//                        edit.setVisibility(View.GONE);
//                    }

                    Title.setText(title);
                    Description.setText(description);

                } else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ArticleDetailsActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userComment() {

        hideKeyboard(ArticleDetailsActivity.this);

        final String id = document_ref.getId();
        final String comment = Comment.getText().toString().trim();



        document_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {


                    String name = documentSnapshot.getString("name");


                    Map<String, Object> userMap = new HashMap<>();

                    userMap.put("name", name);
                    userMap.put("comment", comment);
                    userMap.put("user_id", userID);
                    userMap.put("post_id", item_id);
                    userMap.put("timestamp", FieldValue.serverTimestamp());


                    DocumentReference ref =  db.collection("Comments").document();

                    String doc_id = ref.getId();

                    ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Comment.setText("");
                            Toast.makeText(ArticleDetailsActivity.this, "Adding..", Toast.LENGTH_LONG).show();

                            firebaseFirestore.collection("Article").document(item_id).collection("Comment").document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {



                                    Map<String, Object> comments = new HashMap<>();
                                    comments.put("id", doc_id);
                                    comments.put("timestamp", FieldValue.serverTimestamp());

                                    firebaseFirestore.collection("Article").document(item_id).collection("Comment").document().set(comments);

                                    adapter.refresh();

                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ArticleDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ArticleDetailsActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
            }
        });
//        Intent restartActivity = new Intent(getApplication(), ArticleDetailsActivity.class);
//        restartActivity.putExtra(EXTRA_ID, item_id);
//        startActivity(restartActivity);
//        finish();
    }

    private void loadComments() {
        CollectionReference comments = db.collection("Comments");

        Query query = comments.whereEqualTo("post_id", item_id).orderBy("timestamp", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<Comment> options = new FirestorePagingOptions.Builder<Comment>()
                .setQuery(query, config, Comment.class)
                .build();

        adapter = new CommentAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void viewFeatures() {
        //View features

        firebaseFirestore.collection("Article").document(item_id).collection("View").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> views = new HashMap<>();
                views.put("timestamp", FieldValue.serverTimestamp());

                firebaseFirestore.collection("Article").document(item_id).collection("View").document(userID).set(views);

            }
        });

        //Get views Count
        firebaseFirestore.collection("Article").document(item_id).collection("View").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {
                    String count = String.valueOf(documentSnapshots.size());
                    Views_count.setText(count);
                } else {
                    Views_count.setText("0");
                }
            }
        });
    }

    private void likeFeatures() {
        //Like features
        Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Article").document(item_id).collection("Like").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {

                            Map<String, Object> likes = new HashMap<>();
                            likes.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Article").document(item_id).collection("Like").document(userID).set(likes);

                        } else {

                            firebaseFirestore.collection("Article").document(item_id).collection("Like").document(userID).delete();

                        }

                    }
                });

            }
        });

        //Update like icon
        firebaseFirestore.collection("Article").document(item_id).collection("Like").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {

                    Like.setImageResource(R.drawable.ic_like_selected);

                } else {

                    Like.setImageResource(R.drawable.ic_like);

                }
            }
        });

        //Get like Count
        firebaseFirestore.collection("Article").document(item_id).collection("Like").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {
                    String count = String.valueOf(documentSnapshots.size());
                    Like_count.setText(count);
                } else {
                    Like_count.setText("0");
                }
            }
        });
    }

    private void commentFeatures() {
        //Get comments Count
        firebaseFirestore.collection("Article").document(item_id).collection("Comment").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {
                    String count = String.valueOf(documentSnapshots.size());
                    Comment_count.setText(count);
                } else {
                    Comment_count.setText("0");
                }
            }
        });
    }

    public String getItemID() {
        return item_id;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
