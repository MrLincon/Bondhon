package com.matrimony.bd.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.matrimony.bd.BottomSheets.BottomSheetHomeDataDetails;
import com.matrimony.bd.Models.FilterRecyclerDecoration;
import com.matrimony.bd.Models.HomeData;
import com.matrimony.bd.Models.HomeDataAdapter;
import com.matrimony.bd.R;


public class FragmentFavourite extends Fragment {

    private RecyclerView recyclerview;
    public String item_id;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference homeData;

    private HomeDataAdapter adapter;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerview.addItemDecoration(new FilterRecyclerDecoration(topPadding, bottomPadding));

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        homeData = db.collection("userDetails");

        Query query = homeData.whereArrayContains("favouriteList",userID);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<HomeData> options = new FirestorePagingOptions.Builder<HomeData>()
                .setQuery(query, config, HomeData.class)
                .build();

        adapter = new HomeDataAdapter(options);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
        adapter.startListening();

        adapter.setOnItemClickListener(new HomeDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                item_id = documentSnapshot.getId();

                BottomSheetHomeDataDetails bottomSheetHomeDataDetails = new BottomSheetHomeDataDetails();
                Bundle bundle = new Bundle();
                bundle.putString("data", item_id );
                bottomSheetHomeDataDetails.setArguments(bundle);
                bottomSheetHomeDataDetails.show(getActivity().getSupportFragmentManager(), "Home Data Details");
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
