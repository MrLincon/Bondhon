package com.matrimony.bd.BottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.matrimony.bd.Activity.FilterResultActivity;
import com.matrimony.bd.Fragments.FragmentFriendList;
import com.matrimony.bd.R;

import java.util.HashMap;
import java.util.Map;

public class BottomSheetFriendDetails extends BottomSheetDialogFragment {


    TextView name, maritalCondition, permanentAddress, permanentDistrict, currentAddress, currentDistrict,
            birthDate, colorOfBody, height, weight, bloodGroup, occupation, monthlyIncome, grewUp,
            educationMedium, otherEducationalQualifications, fathersName, mothersName, fathersOccupation,
            mothersOccupation, parents, brothers, sisters, familyMemberOccupation, familyCondition, prayerPerDay,
            prayerEveryDay, mahramNonMahram, quranReading, mazhabFollowing, politicalSight, movieSeries, physicalMentalDisease,
            religiousAct, followingPir, sightAboutMajar, threeIslamicBook, threeFavouriteAlem, specialReligiousQualification,
            aboutYourself, parentsApproval, reasonForMarriage, yourAsk, expectedAge, expectedColor, expectedHeight,
            expectedMinimumEducation, expectedDistrict, expectedMaritalCondition, expectedOccupation, expectedFinancialBackground,
            expectedFamilyCondition, expectedLifePartnerQualities, yourContact, yourEmail;

    private String userID;
    private String ItemID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference, doc_ref;


    public BottomSheetFriendDetails() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_friend_details, container, false);


        name = view.findViewById(R.id.name);
        maritalCondition = view.findViewById(R.id.maritalCondition);
        permanentAddress = view.findViewById(R.id.permanentAddress);
        permanentDistrict = view.findViewById(R.id.permanentDistrict);
        currentAddress = view.findViewById(R.id.currentAddress);
        currentDistrict = view.findViewById(R.id.currentDistrict);
        birthDate = view.findViewById(R.id.birthDate);
        colorOfBody = view.findViewById(R.id.colorOfBody);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        bloodGroup = view.findViewById(R.id.bloodGroup);
        occupation = view.findViewById(R.id.occupation);
        monthlyIncome = view.findViewById(R.id.monthlyIncome);
        grewUp = view.findViewById(R.id.grewUp);
        educationMedium = view.findViewById(R.id.educationMedium);
        otherEducationalQualifications = view.findViewById(R.id.otherEducationalQualifications);
        fathersName = view.findViewById(R.id.fathersName);
        mothersName = view.findViewById(R.id.mothersName);
        fathersOccupation = view.findViewById(R.id.fathersOccupation);
        mothersOccupation = view.findViewById(R.id.mothersOccupation);
        parents = view.findViewById(R.id.parents);
        brothers = view.findViewById(R.id.brothers);
        sisters = view.findViewById(R.id.sisters);
        familyMemberOccupation = view.findViewById(R.id.familyMemberOccupation);
        familyCondition = view.findViewById(R.id.familyCondition);
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
        parentsApproval = view.findViewById(R.id.parentsApproval);
        reasonForMarriage = view.findViewById(R.id.reasonForMarriage);
        yourAsk = view.findViewById(R.id.yourAsk);
        expectedAge = view.findViewById(R.id.expectedAge);
        expectedColor = view.findViewById(R.id.expectedColor);
        expectedHeight = view.findViewById(R.id.expectedHeight);
        expectedMinimumEducation = view.findViewById(R.id.expectedMinimumEducation);
        expectedDistrict = view.findViewById(R.id.expectedDistrict);
        expectedMaritalCondition = view.findViewById(R.id.expectedMaritalCondition);
        expectedOccupation = view.findViewById(R.id.expectedOccupation);
        expectedFinancialBackground = view.findViewById(R.id.expectedFinancialBackground);
        expectedFamilyCondition = view.findViewById(R.id.expectedFamilyCondition);
        expectedLifePartnerQualities = view.findViewById(R.id.expectedLifePartnerQualities);
        yourContact = view.findViewById(R.id.your_contact);
        yourEmail = view.findViewById(R.id.your_email);


        ItemID = getArguments().getString("item_id");

        db = FirebaseFirestore.getInstance();
        document_reference = db.collection("userDetails").document(ItemID);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();




        loadData();


        return view;
    }

    private void loadData() {

        document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    String Name = documentSnapshot.getString("name");
                    String MaritalCondition = documentSnapshot.getString("maritalCondition");
                    String PermanentAddress = documentSnapshot.getString("permanentAddress");
                    String PermanentDistrict = documentSnapshot.getString("permanentDistrict");
                    String CurrentAddress = documentSnapshot.getString("currentAddress");
                    String CurrentDistrict = documentSnapshot.getString("currentDistrict");
                    String BirthDate = documentSnapshot.getString("birthDate");
                    String ColorOfBody = documentSnapshot.getString("colorOfBody");
                    String Height = documentSnapshot.getString("height");
                    String Weight = documentSnapshot.getString("weight");
                    String BloodGroup = documentSnapshot.getString("bloodGroup");
                    String Occupation = documentSnapshot.getString("occupation");
                    String MonthlyIncome = documentSnapshot.getString("monthlyIncome");
                    String GrewUp = documentSnapshot.getString("grewUp");
                    String EducationMedium = documentSnapshot.getString("educationMedium");
                    String OtherEducationalQualifications = documentSnapshot.getString("otherEducationalQualifications");
                    String FathersName = documentSnapshot.getString("fathersName");
                    String MothersName = documentSnapshot.getString("mothersName");
                    String FathersOccupation = documentSnapshot.getString("fathersOccupation");
                    String MothersOccupation = documentSnapshot.getString("mothersOccupation");
                    String Parents = documentSnapshot.getString("parents");
                    String Brothers = documentSnapshot.getString("brothers");
                    String Sisters = documentSnapshot.getString("sisters");
                    String FamilyMemberOccupation = documentSnapshot.getString("familyMemberOccupation");
                    String FamilyCondition = documentSnapshot.getString("familyCondition");
                    String PrayerPerDay = documentSnapshot.getString("prayerPerDay");
                    String PrayerEveryDay = documentSnapshot.getString("prayerEveryDay");
                    String MahramNonMahram = documentSnapshot.getString("mahramNonMahram");
                    String QuranReading = documentSnapshot.getString("quranReading");
                    String MazhabFollowing = documentSnapshot.getString("name");
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
                    String ParentsApproval = documentSnapshot.getString("parentsApproval");
                    String ReasonForMarriage = documentSnapshot.getString("reasonForMarriage");
                    String YourAsk = documentSnapshot.getString("yourAsk");
                    String ExpectedAge = documentSnapshot.getString("expectedAge");
                    String ExpectedColor = documentSnapshot.getString("expectedColor");
                    String ExpectedHeight = documentSnapshot.getString("expectedHeight");
                    String ExpectedMinimumEducation = documentSnapshot.getString("expectedMinimumEducation");
                    String ExpectedDistrict = documentSnapshot.getString("expectedDistrict");
                    String ExpectedMaritalCondition = documentSnapshot.getString("expectedMaritalCondition");
                    String ExpectedOccupation = documentSnapshot.getString("expectedOccupation");
                    String ExpectedFinancialBackground = documentSnapshot.getString("expectedFinancialBackground");
                    String ExpectedFamilyCondition = documentSnapshot.getString("expectedFamilyCondition");
                    String ExpectedLifePartnerQualities = documentSnapshot.getString("expectedLifePartnerQualities");
                    String YourContact = documentSnapshot.getString("yourContact");
                    String YourEmail = documentSnapshot.getString("yourEmail");


                    name.setText(Name);
                    maritalCondition.setText(MaritalCondition);
                    permanentAddress.setText(PermanentAddress);
                    permanentDistrict.setText(PermanentDistrict);
                    currentAddress.setText(CurrentAddress);
                    currentDistrict.setText(CurrentDistrict);
                    birthDate.setText(BirthDate);
                    colorOfBody.setText(ColorOfBody);
                    height.setText(Height);
                    weight.setText(Weight);
                    bloodGroup.setText(BloodGroup);
                    occupation.setText(Occupation);
                    monthlyIncome.setText(MonthlyIncome);
                    grewUp.setText(GrewUp);
                    educationMedium.setText(EducationMedium);
                    otherEducationalQualifications.setText(OtherEducationalQualifications);
                    fathersName.setText(FathersName);
                    mothersName.setText(MothersName);
                    fathersOccupation.setText(FathersOccupation);
                    mothersOccupation.setText(MothersOccupation);
                    parents.setText(Parents);
                    brothers.setText(Brothers);
                    sisters.setText(Sisters);
                    familyMemberOccupation.setText(FamilyMemberOccupation);
                    familyCondition.setText(FamilyCondition);
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
                    parentsApproval.setText(ParentsApproval);
                    reasonForMarriage.setText(ReasonForMarriage);
                    yourAsk.setText(YourAsk);
                    expectedAge.setText(ExpectedAge);
                    expectedColor.setText(ExpectedColor);
                    expectedHeight.setText(ExpectedHeight);
                    expectedMinimumEducation.setText(ExpectedMinimumEducation);
                    expectedDistrict.setText(ExpectedDistrict);
                    expectedMaritalCondition.setText(ExpectedMaritalCondition);
                    expectedOccupation.setText(ExpectedOccupation);
                    expectedFinancialBackground.setText(ExpectedFinancialBackground);
                    expectedFamilyCondition.setText(ExpectedFamilyCondition);
                    expectedLifePartnerQualities.setText(ExpectedLifePartnerQualities);
                    yourContact.setText(YourContact);
                    yourEmail.setText(YourEmail);

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
