package com.matrimony.bd.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.matrimony.bd.Activity.MainActivity;
import com.matrimony.bd.Activity.NotificationActivity;
import com.matrimony.bd.Activity.UpdateProfileActivity;
import com.matrimony.bd.BottomSheets.BottomSheetFilterDetails;
import com.matrimony.bd.BottomSheets.BottomSheetFriendDetails;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF1;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF10;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF11;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF2;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF3;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF4;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF5;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF6;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF7;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF8;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF9;
import com.matrimony.bd.Models.FilterRecyclerDecoration;
import com.matrimony.bd.Models.Friend;
import com.matrimony.bd.Models.FriendAdapter;
import com.matrimony.bd.Models.Request;
import com.matrimony.bd.Models.RequestAdapter;
import com.matrimony.bd.R;


public class FragmentFriendList extends Fragment {

    View view;

    private RecyclerView recyclerview;
    SwipeRefreshLayout swipeRefreshLayout;


    String item_id;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference friend;

    private FriendAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerview.addItemDecoration(new FilterRecyclerDecoration(topPadding, bottomPadding));
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        friend = db.collection("userDetails").document(userID).collection("acceptedRequest");

        Query query = friend.orderBy("timestamp", Query.Direction.ASCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<Friend> options = new FirestorePagingOptions.Builder<Friend>()
                .setQuery(query, config, Friend.class)
                .build();

        adapter = new FriendAdapter(options, swipeRefreshLayout);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
        adapter.startListening();

        adapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                item_id = documentSnapshot.getId();

                Bundle bundle = new Bundle();
                bundle.putString("item_id", item_id);

                BottomSheetFriendDetails bottomSheetFriendDetails = new BottomSheetFriendDetails();
                bottomSheetFriendDetails.setArguments(bundle);
                bottomSheetFriendDetails.show(getFragmentManager(), "Friend Details");
            }
        });

        return view;
    }
}
