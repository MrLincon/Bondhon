package com.matrimony.bd.Fragments.ProfileFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.matrimony.bd.Activity.UpdateProfileActivity;
import com.matrimony.bd.R;

import java.util.HashMap;
import java.util.Map;


public class Fragment6 extends Fragment {

    View view;

    TextInputEditText prayerPerDay, prayerEveryDay, mahramNonMahram, quranReading, mazhabFollowing,
            politicalSight, movieSeries, physicalMentalDisease, religiousAct, followingPir, sightAboutMajar,
            threeIslamicBook, threeFavouriteAlem, specialReligiousQualification, aboutYourself;

    RadioGroup rgPrayerPerDay;

    CardView save;

    Dialog popup;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_6, container, false);

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
        save = view.findViewById(R.id.save);

        popup = new Dialog(getActivity());

        ((UpdateProfileActivity) getActivity()).next.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        document_reference = db.collection("userDetails").document(userID);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        prayerPerDay.setFocusable(false);

        prayerPerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_yes_no);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgPrayerPerDay = popup.findViewById(R.id.rg_yes_no);

                popup.show();

                rgPrayerPerDay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String PrayerPerDay = radioButton.getText().toString();

                        prayerPerDay.setText(PrayerPerDay);
                        popup.dismiss();
                    }
                });
            }
        });

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

    private void saveData() {

        String PrayerPerDay = prayerPerDay.getText().toString();
        String PrayerEveryDay = prayerEveryDay.getText().toString();
        String MahramNonMahram = mahramNonMahram.getText().toString();
        String QuranReading = quranReading.getText().toString();
        String MazhabFollowing = mazhabFollowing.getText().toString();
        String PoliticalSight = politicalSight.getText().toString();
        String MovieSeries = movieSeries.getText().toString();
        String PhysicalMentalDisease = physicalMentalDisease.getText().toString();
        String ReligiousAct = religiousAct.getText().toString();
        String FollowingPir = followingPir.getText().toString();
        String SightAboutMajar = sightAboutMajar.getText().toString();
        String ThreeIslamicBook = threeIslamicBook.getText().toString();
        String ThreeFavouriteAlem = threeFavouriteAlem.getText().toString();
        String SpecialReligiousQualification = specialReligiousQualification.getText().toString();
        String AboutYourself = aboutYourself.getText().toString();

        if (!PrayerPerDay.isEmpty() && !PrayerEveryDay.isEmpty() && !MahramNonMahram.isEmpty() &&
                !QuranReading.isEmpty() && !MazhabFollowing.isEmpty() && !PoliticalSight.isEmpty() &&
                !MovieSeries.isEmpty() && !PhysicalMentalDisease.isEmpty() && !ReligiousAct.isEmpty() &&
                !FollowingPir.isEmpty() && !SightAboutMajar.isEmpty() && !ThreeIslamicBook.isEmpty() &&
                !ThreeFavouriteAlem.isEmpty() && !AboutYourself.isEmpty()) {

            document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    popup.setContentView(R.layout.popup_saving);
                    popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popup.show();

                    if (!documentSnapshot.exists()) {

                        final String id = document_reference.getId();
                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("prayerPerDay", PrayerPerDay);
                        userMap.put("prayerEveryDay", PrayerEveryDay);
                        userMap.put("mahramNonMahram", MahramNonMahram);
                        userMap.put("quranReading", QuranReading);
                        userMap.put("mazhabFollowing", MazhabFollowing);
                        userMap.put("politicalSight", PoliticalSight);
                        userMap.put("movieSeries", MovieSeries);
                        userMap.put("physicalMentalDisease", PhysicalMentalDisease);
                        userMap.put("religiousAct", ReligiousAct);
                        userMap.put("followingPir", FollowingPir);
                        userMap.put("sightAboutMajar", SightAboutMajar);
                        userMap.put("threeIslamicBook", ThreeIslamicBook);
                        userMap.put("threeFavouriteAlem", ThreeFavouriteAlem);
                        userMap.put("specialReligiousQualification", SpecialReligiousQualification);
                        userMap.put("aboutYourself", AboutYourself);
                        userMap.put("user_id", userID);
                        userMap.put("id", id);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                prayerPerDay.setEnabled(false);
                                prayerEveryDay.setEnabled(false);
                                mahramNonMahram.setEnabled(false);
                                quranReading.setEnabled(false);
                                mazhabFollowing.setEnabled(false);
                                politicalSight.setEnabled(false);
                                movieSeries.setEnabled(false);
                                physicalMentalDisease.setEnabled(false);
                                religiousAct.setEnabled(false);
                                followingPir.setEnabled(false);
                                sightAboutMajar.setEnabled(false);
                                threeIslamicBook.setEnabled(false);
                                threeFavouriteAlem.setEnabled(false);
                                specialReligiousQualification.setEnabled(false);
                                aboutYourself.setEnabled(false);

                                ((UpdateProfileActivity) getActivity()).next.setVisibility(View.VISIBLE);
                                popup.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {

                        final String id = document_reference.getId();
                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("prayerPerDay", PrayerPerDay);
                        userMap.put("prayerEveryDay", PrayerEveryDay);
                        userMap.put("mahramNonMahram", MahramNonMahram);
                        userMap.put("quranReading", QuranReading);
                        userMap.put("mazhabFollowing", MazhabFollowing);
                        userMap.put("politicalSight", PoliticalSight);
                        userMap.put("movieSeries", MovieSeries);
                        userMap.put("physicalMentalDisease", PhysicalMentalDisease);
                        userMap.put("religiousAct", ReligiousAct);
                        userMap.put("followingPir", FollowingPir);
                        userMap.put("sightAboutMajar", SightAboutMajar);
                        userMap.put("threeIslamicBook", ThreeIslamicBook);
                        userMap.put("threeFavouriteAlem", ThreeFavouriteAlem);
                        userMap.put("specialReligiousQualification", SpecialReligiousQualification);
                        userMap.put("aboutYourself", AboutYourself);
                        userMap.put("user_id", userID);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                prayerPerDay.setEnabled(false);
                                prayerEveryDay.setEnabled(false);
                                mahramNonMahram.setEnabled(false);
                                quranReading.setEnabled(false);
                                mazhabFollowing.setEnabled(false);
                                politicalSight.setEnabled(false);
                                movieSeries.setEnabled(false);
                                physicalMentalDisease.setEnabled(false);
                                religiousAct.setEnabled(false);
                                followingPir.setEnabled(false);
                                sightAboutMajar.setEnabled(false);
                                threeIslamicBook.setEnabled(false);
                                threeFavouriteAlem.setEnabled(false);
                                specialReligiousQualification.setEnabled(false);
                                aboutYourself.setEnabled(false);

                                ((UpdateProfileActivity) getActivity()).next.setVisibility(View.VISIBLE);
                                popup.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "এই (*) চিহ্ন দেওয়া প্রশ্ন অবশই উত্তর দিতে হবে!", Toast.LENGTH_SHORT).show();
        }
    }

}
