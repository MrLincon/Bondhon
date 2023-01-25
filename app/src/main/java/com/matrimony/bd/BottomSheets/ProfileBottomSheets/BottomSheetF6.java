package com.matrimony.bd.BottomSheets.ProfileBottomSheets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.matrimony.bd.Activity.UpdateProfileActivity;
import com.matrimony.bd.R;

public class BottomSheetF6 extends BottomSheetDialogFragment {


    TextView prayerPerDay, prayerEveryDay, mahramNonMahram, quranReading, mazhabFollowing,
            politicalSight, movieSeries, physicalMentalDisease, religiousAct, followingPir, sightAboutMajar,
            threeIslamicBook, threeFavouriteAlem, specialReligiousQualification, aboutYourself;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    public BottomSheetF6() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_f6, container, false);

        prayerPerDay = view.findViewById(R.id.prayerPerDay);
        prayerEveryDay = view.findViewById(R.id.prayerEveryDay);
        mahramNonMahram = view.findViewById(R.id.mahramNonMahram);
        quranReading = view.findViewById(R.id.quranReading);
        mazhabFollowing = view.findViewById(R.id.mazhabFollowing);
        politicalSight = view.findViewById(R.id.politicalSight);
        movieSeries = view.findViewById(R.id.movieSeries);
        physicalMentalDisease = view.findViewById(R.id.physicalMentalDisease);
        religiousAct = view.findViewById(R.id.religiousAct);
        followingPir = view.findViewById(R.id.followingPir);
        sightAboutMajar = view.findViewById(R.id.sightAboutMajar);
        threeIslamicBook = view.findViewById(R.id.threeIslamicBook);
        threeFavouriteAlem = view.findViewById(R.id.threeFavouriteAlem);
        specialReligiousQualification = view.findViewById(R.id.specialReligiousQualification);
        aboutYourself = view.findViewById(R.id.aboutYourself);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        document_reference = db.collection("userDetails").document(userID);

        loadData();

        return view;
    }

    private void loadData() {
        document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    String PrayerPerDay = documentSnapshot.getString("prayerPerDay");
                    String PrayerEveryDay = documentSnapshot.getString("prayerEveryDay");
                    String MahramNonMahram = documentSnapshot.getString("mahramNonMahram");
                    String QuranReading = documentSnapshot.getString("quranReading");
                    String MazhabFollowing = documentSnapshot.getString("mazhabFollowing");
                    String PoliticalSight = documentSnapshot.getString("politicalSight");
                    String MovieSeries = documentSnapshot.getString("movieSeries");
                    String PhysicalMentalDisease = documentSnapshot.getString("physicalMentalDisease");
                    String ReligiousAct = documentSnapshot.getString("religiousAct");
                    String FollowingPir = documentSnapshot.getString("followingPir");
                    String SightAboutMajar = documentSnapshot.getString("sightAboutMajar");
                    String ThreeIslamicBook = documentSnapshot.getString("threeIslamicBook");
                    String ThreeFavouriteAlem = documentSnapshot.getString("threeFavouriteAlem");
                    String SpecialReligiousQualification = documentSnapshot.getString("specialReligiousQualification");
                    String AboutYourself = documentSnapshot.getString("aboutYourself");

                    prayerPerDay.setText(PrayerPerDay);
                    prayerEveryDay.setText(PrayerEveryDay);
                    mahramNonMahram.setText(MahramNonMahram);
                    quranReading.setText(QuranReading);
                    mazhabFollowing.setText(MazhabFollowing);
                    politicalSight.setText(PoliticalSight);
                    movieSeries.setText(MovieSeries);
                    physicalMentalDisease.setText(PhysicalMentalDisease);
                    religiousAct.setText(ReligiousAct);
                    followingPir.setText(FollowingPir);
                    sightAboutMajar.setText(SightAboutMajar);
                    threeIslamicBook.setText(ThreeIslamicBook);
                    threeFavouriteAlem.setText(ThreeFavouriteAlem);
                    specialReligiousQualification.setText(SpecialReligiousQualification);
                    aboutYourself.setText(AboutYourself);

                } else {
                    Toast.makeText(getActivity(), "Something wrong!", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
