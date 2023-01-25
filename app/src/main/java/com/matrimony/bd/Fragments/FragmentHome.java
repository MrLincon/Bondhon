package com.matrimony.bd.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.matrimony.bd.Activity.BioDataNumberResultActivity;
import com.matrimony.bd.Activity.FilterResultActivity;
import com.matrimony.bd.BottomSheets.BottomSheetFilterDetails;
import com.matrimony.bd.BottomSheets.BottomSheetHomeDataDetails;
import com.matrimony.bd.Models.FilterRecyclerDecoration;
import com.matrimony.bd.Models.FilterResult;
import com.matrimony.bd.Models.FilterResultAdapter;
import com.matrimony.bd.Models.HomeData;
import com.matrimony.bd.Models.HomeDataAdapter;
import com.matrimony.bd.R;


public class FragmentHome extends Fragment {

    View view;

    CardView filter, expand, filterSearch;
    CardView bioDataType, maritalCondition, division, educationMedium, prayerPerDay;
    ImageView filterArrow, search;
    EditText bioDataNumber;

    TextView totalMale, totalFemale, totalData;

    TextView etBioDataType, etMaritalCondition, etDivision, etEducationMedium, etPrayer;
    RadioGroup rgBioDataType, rgMaritalCondition, rgDivision, rgEducationMedium, rgPrayer;

    Dialog popup;

    private RecyclerView recyclerview;
    public String item_id;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference homeData;

    private HomeDataAdapter adapter;


    public static final String EXTRA_BIO_DATA_TYPE = "com.matrimony.bd.EXTRA_BIO_DATA_TYPE";
    public static final String EXTRA_MARITAL_CONDITION = "com.matrimony.bd.EXTRA_MARITAL_CONDITION";
    public static final String EXTRA_DIVISION = "com.matrimony.bd.EXTRA_DIVISION";
    public static final String EXTRA_EDUCATION_MEDIUM = "com.matrimony.bd.EXTRA_EDUCATION_MEDIUM";
    public static final String EXTRA_PRAYER = "com.matrimony.bd.EXTRA_PRAYER";
    public static final String EXTRA_BIO_DATA_NUMBER = "com.matrimony.bd.EXTRA_BIO_DATA_NUMBER";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        filter = view.findViewById(R.id.filter);
        expand = view.findViewById(R.id.expand);
        filterSearch = view.findViewById(R.id.filter_search);
        filterArrow = view.findViewById(R.id.filter_arrow);

        etBioDataType = view.findViewById(R.id.etBioDataType);
        etMaritalCondition = view.findViewById(R.id.etMaritalCondition);
        etDivision = view.findViewById(R.id.etDivision);
        etEducationMedium = view.findViewById(R.id.etEducationMedium);
        etPrayer = view.findViewById(R.id.etPrayerPerDay);

        bioDataType = view.findViewById(R.id.bioDataType);
        maritalCondition = view.findViewById(R.id.maritalCondition);
        division = view.findViewById(R.id.division);
        educationMedium = view.findViewById(R.id.educationMedium);
        prayerPerDay = view.findViewById(R.id.prayerPerDay);

        totalMale = view.findViewById(R.id.total_male);
        totalFemale = view.findViewById(R.id.total_female);
        totalData = view.findViewById(R.id.total_biodata);

        bioDataNumber = view.findViewById(R.id.biodata_number);
        search = view.findViewById(R.id.search);

        recyclerview = view.findViewById(R.id.recyclerView);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        recyclerview.addItemDecoration(new FilterRecyclerDecoration(topPadding, bottomPadding));


        popup = new Dialog(getActivity());


        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOptions();
            }
        });

        filterArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOptions();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bioDataCode = bioDataNumber.getText().toString();

               if (!bioDataCode.isEmpty()){
                   Intent intent = new Intent(getActivity(), BioDataNumberResultActivity.class);

                   intent.putExtra(EXTRA_BIO_DATA_NUMBER, bioDataCode);

                   startActivity(intent);
                   bioDataNumber.setText("");
               }
            }
        });


        bioDataType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_biodata_type);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgBioDataType = popup.findViewById(R.id.rg_bioDataType);

                popup.show();

                rgBioDataType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String BioDataType = radioButton.getText().toString();

                        etBioDataType.setText(BioDataType);
                        popup.dismiss();
                    }
                });
            }
        });

        maritalCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_marital_condition);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgMaritalCondition = popup.findViewById(R.id.rg_maritalCondition);

                popup.show();

                rgMaritalCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String MaritalCondition = radioButton.getText().toString();

                        etMaritalCondition.setText(MaritalCondition);
                        popup.dismiss();
                    }
                });
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_division);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgDivision = popup.findViewById(R.id.rg_Division);

                popup.show();

                rgDivision.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String Division = radioButton.getText().toString();

                        etDivision.setText(Division);
                        popup.dismiss();
                    }
                });
            }
        });

        educationMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_education_medium);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgEducationMedium = popup.findViewById(R.id.rg_educationMedium);

                popup.show();

                rgEducationMedium.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String EducationMedium = radioButton.getText().toString();

                        etEducationMedium.setText(EducationMedium);
                        popup.dismiss();
                    }
                });
            }
        });

        prayerPerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_yes_no);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgPrayer = popup.findViewById(R.id.rg_yes_no);

                popup.show();

                rgPrayer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String YesNo = radioButton.getText().toString();

                        etPrayer.setText(YesNo);
                        popup.dismiss();
                    }
                });
            }
        });

        filterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterResults();
            }
        });

        dataCount();

        homeData = db.collection("userDetails");

        Query query = homeData.orderBy("featured", Query.Direction.DESCENDING).whereEqualTo("status","approved");

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
                bundle.putString("data", item_id);
                bottomSheetHomeDataDetails.setArguments(bundle);
                bottomSheetHomeDataDetails.show(getActivity().getSupportFragmentManager(), "Home Data Details");
            }
        });


        return view;
    }

    private void dataCount() {

        db.collection("userDetails")
                .whereEqualTo("bioDataType","পাত্রের বায়োডাটা")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            totalMale.setText(String.valueOf(count)+"+");
                        }
                    }
                });

        db.collection("userDetails")
                .whereEqualTo("bioDataType","পাত্রীর বায়োডাটা")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            totalFemale.setText(String.valueOf(count)+"+");
                        }
                    }
                });

        db.collection("userDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            totalData.setText(String.valueOf(count)+"+");
                        }
                    }
                });
    }

    public String getItemID() {
        return item_id;
    }


    private void filterResults() {

        String BioDataType = etBioDataType.getText().toString();
        String MaritalCondition = etMaritalCondition.getText().toString();
        String Division = etDivision.getText().toString();
        String EducationMedium = etEducationMedium.getText().toString();
        String Prayer = etPrayer.getText().toString();

        if (!BioDataType.equals("নির্বাচন করুন") && !MaritalCondition.equals("নির্বাচন করুন") && !Division.equals("নির্বাচন করুন") &&
                !EducationMedium.equals("নির্বাচন করুন") && !Prayer.equals("নির্বাচন করুন")) {


            Intent intent = new Intent(getActivity(), FilterResultActivity.class);

            intent.putExtra(EXTRA_BIO_DATA_TYPE, BioDataType);
            intent.putExtra(EXTRA_MARITAL_CONDITION, MaritalCondition);
            intent.putExtra(EXTRA_DIVISION, Division);
            intent.putExtra(EXTRA_EDUCATION_MEDIUM, EducationMedium);
            intent.putExtra(EXTRA_PRAYER, Prayer);

            startActivity(intent);


            TransitionManager.beginDelayedTransition(filter, new AutoTransition());
            expand.setVisibility(View.GONE);
            filterArrow.setImageResource(R.drawable.ic_arrow_down);

        } else {
            Toast.makeText(getContext(), "সবগুলো পদ থেকে নির্বাচন করুন!", Toast.LENGTH_SHORT).show();
        }


    }

    private void filterOptions() {
        // If the CardView is already expanded, set its visibility
        //  to gone and change the expand less icon to expand more.
        if (expand.getVisibility() == View.VISIBLE) {

            // The transition of the hiddenView is carried out
            //  by the TransitionManager class.
            // Here we use an object of the AutoTransition
            // Class to create a default transition.
            TransitionManager.beginDelayedTransition(filter,
                    new AutoTransition());
            expand.setVisibility(View.GONE);
            filterArrow.setImageResource(R.drawable.ic_arrow_down);
        }

        // If the CardView is not expanded, set its visibility
        // to visible and change the expand more icon to expand less.
        else {

            TransitionManager.beginDelayedTransition(filter,
                    new AutoTransition());
            expand.setVisibility(View.VISIBLE);
            filterArrow.setImageResource(R.drawable.ic_arrow_up);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
