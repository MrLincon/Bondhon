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

public class BottomSheetF9 extends BottomSheetDialogFragment {

    TextView expectedAge, expectedColor, expectedHeight, expectedMinimumEducation, expectedDistrict,
            expectedMaritalCondition, expectedOccupation, expectedFinancialBackground, expectedFamilyCondition,
            expectedLifePartnerQualities;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    public BottomSheetF9() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_f9, container, false);

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
