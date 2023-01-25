package com.matrimony.bd.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.matrimony.bd.Activity.ArticleDetailsActivity;
import com.matrimony.bd.Models.Article;
import com.matrimony.bd.Models.ArticleAdapter;
import com.matrimony.bd.Models.FilterRecyclerDecoration;
import com.matrimony.bd.R;


public class FragmentArticle extends Fragment {

    private RecyclerView recyclerview;
    SwipeRefreshLayout swipeRefreshLayout;
    public String item_id;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference article;

    private ArticleAdapter adapter;

    public static final String EXTRA_ID = " com.matrimony.bd.EXTRA_ID";

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerview.addItemDecoration(new FilterRecyclerDecoration(topPadding, bottomPadding));
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        article = db.collection("Article");

        Query query = article.orderBy("title", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<Article> options = new FirestorePagingOptions.Builder<Article>()
                .setQuery(query, config, Article.class)
                .build();

        adapter = new ArticleAdapter(options, swipeRefreshLayout);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
        adapter.startListening();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
            }
        });

        adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                item_id = documentSnapshot.getId();

                Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
                intent.putExtra(EXTRA_ID, item_id);
                startActivity(intent);
            }
        });

        return view;
    }

    public String getItemID() {
        return item_id;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
