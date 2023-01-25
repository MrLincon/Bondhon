package com.matrimony.bd.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.matrimony.bd.BottomSheets.BottomSheetFilterDetails;
import com.matrimony.bd.Fragments.FragmentHome;
import com.matrimony.bd.Models.FilterRecyclerDecoration;
import com.matrimony.bd.Models.FilterResult;
import com.matrimony.bd.Models.FilterResultAdapter;
import com.matrimony.bd.R;

public class FilterResultActivity extends AppCompatActivity {

    ImageView back;
    TextView title;

    private RecyclerView recyclerview;

    String bioDataType, maritalCondition, division, educationMedium, prayerPerDay;

    String item_id;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference filter;

    private FilterResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);

        back = findViewById(R.id.back);
        title = findViewById(R.id.title);

        recyclerview = findViewById(R.id.recyclerview);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerview.addItemDecoration(new FilterRecyclerDecoration(topPadding, bottomPadding));

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();


        final Intent intent = getIntent();
        bioDataType = intent.getStringExtra(FragmentHome.EXTRA_BIO_DATA_TYPE);
        maritalCondition = intent.getStringExtra(FragmentHome.EXTRA_MARITAL_CONDITION);
        division = intent.getStringExtra(FragmentHome.EXTRA_DIVISION);
        educationMedium = intent.getStringExtra(FragmentHome.EXTRA_EDUCATION_MEDIUM);
        prayerPerDay = intent.getStringExtra(FragmentHome.EXTRA_PRAYER);

        title.setText(bioDataType);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        filter = db.collection("userDetails");


        if (division.equals("সকল বিভাগ")) {
            Query query = filter.whereEqualTo("bioDataType", bioDataType)
                    .whereEqualTo("maritalCondition", maritalCondition)
                    .whereEqualTo("educationMedium", educationMedium)
                    .whereEqualTo("prayerPerDay", prayerPerDay)
                    .whereEqualTo("status", "approved")
                    .orderBy("timestamp", Query.Direction.ASCENDING);

            PagedList.Config config = new PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(15)
                    .build();

            FirestorePagingOptions<FilterResult> options = new FirestorePagingOptions.Builder<FilterResult>()
                    .setQuery(query, config, FilterResult.class)
                    .build();

            adapter = new FilterResultAdapter(options);
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerview.setAdapter(adapter);
            adapter.startListening();

        } else {
            Query query = filter.whereEqualTo("bioDataType", bioDataType)
                    .whereEqualTo("maritalCondition", maritalCondition)
                    .whereEqualTo("permanentDivision", division)
                    .whereEqualTo("educationMedium", educationMedium)
                    .whereEqualTo("prayerPerDay", prayerPerDay)
                    .whereEqualTo("status", "approved")
                    .orderBy("timestamp", Query.Direction.ASCENDING);

            PagedList.Config config = new PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(15)
                    .build();

            FirestorePagingOptions<FilterResult> options = new FirestorePagingOptions.Builder<FilterResult>()
                    .setQuery(query, config, FilterResult.class)
                    .build();

            adapter = new FilterResultAdapter(options);
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerview.setAdapter(adapter);
            adapter.startListening();
        }

        adapter.setOnItemClickListener(new FilterResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                item_id = documentSnapshot.getId();


                BottomSheetFilterDetails bottomSheetFilterDetails = new BottomSheetFilterDetails();
                bottomSheetFilterDetails.show(getSupportFragmentManager(), "Filter Details");
            }
        });

    }

    public String getItemID() {
        return item_id;
    }
}