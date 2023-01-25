package com.matrimony.bd.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.matrimony.bd.Models.FilterRecyclerDecoration;
import com.matrimony.bd.Models.FilterResult;
import com.matrimony.bd.Models.FilterResultAdapter;
import com.matrimony.bd.Models.Request;
import com.matrimony.bd.Models.RequestAdapter;
import com.matrimony.bd.R;

public class NotificationActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerview;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference request;

    private RequestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        fab = findViewById(R.id.floating_action_button);

        recyclerview = findViewById(R.id.recyclerview);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerview.addItemDecoration(new FilterRecyclerDecoration(topPadding, bottomPadding));

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        request = db.collection("userDetails").document(userID).collection("pendingRequest");

        Query query = request.orderBy("timestamp", Query.Direction.ASCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<Request> options = new FirestorePagingOptions.Builder<Request>()
                .setQuery(query, config, Request.class)
                .build();

        adapter = new RequestAdapter(options);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerview.setAdapter(adapter);
        adapter.startListening();
    }
}