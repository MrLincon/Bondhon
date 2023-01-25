package com.matrimony.bd.Fragments.ProfileFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Fragment9 extends Fragment {

    View view;

    TextInputEditText expectedAge, expectedColor, expectedHeight, expectedMinimumEducation, expectedDistrict,
            expectedMaritalCondition, expectedOccupation, expectedFinancialBackground, expectedFamilyCondition,
            expectedLifePartnerQualities;

    CardView save;

    Dialog popup;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_9, container, false);

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

    private void saveData() {

        String ExpectedAge = expectedAge.getText().toString();
        String ExpectedColor = expectedColor.getText().toString();
        String ExpectedHeight = expectedHeight.getText().toString();
        String ExpectedMinimumEducation = expectedMinimumEducation.getText().toString();
        String ExpectedDistrict = expectedDistrict.getText().toString();
        String ExpectedMaritalCondition = expectedMaritalCondition.getText().toString();
        String ExpectedOccupation = expectedOccupation.getText().toString();
        String ExpectedFinancialBackground = expectedFinancialBackground.getText().toString();
        String ExpectedFamilyCondition = expectedFamilyCondition.getText().toString();
        String ExpectedLifePartnerQualities = expectedLifePartnerQualities.getText().toString();

        if (!ExpectedAge.isEmpty() && !ExpectedColor.isEmpty() && !ExpectedHeight.isEmpty() &&
                !ExpectedMinimumEducation.isEmpty() && !ExpectedDistrict.isEmpty() && !ExpectedMaritalCondition.isEmpty() &&
                !ExpectedOccupation.isEmpty() && !ExpectedFinancialBackground.isEmpty() && !ExpectedLifePartnerQualities.isEmpty()) {

            document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    popup.setContentView(R.layout.popup_saving);
                    popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popup.show();

                    if (!documentSnapshot.exists()) {

                        final String id = document_reference.getId();
                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("expectedAge", ExpectedAge);
                        userMap.put("expectedColor", ExpectedColor);
                        userMap.put("expectedHeight", ExpectedHeight);
                        userMap.put("expectedMinimumEducation", ExpectedMinimumEducation);
                        userMap.put("expectedDistrict", ExpectedDistrict);
                        userMap.put("expectedMaritalCondition", ExpectedMaritalCondition);
                        userMap.put("expectedOccupation", ExpectedOccupation);
                        userMap.put("expectedFinancialBackground", ExpectedFinancialBackground);
                        userMap.put("expectedFamilyCondition", ExpectedFamilyCondition);
                        userMap.put("expectedLifePartnerQualities", ExpectedLifePartnerQualities);
                        userMap.put("user_id", userID);
                        userMap.put("id", id);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                expectedAge.setEnabled(false);
                                expectedColor.setEnabled(false);
                                expectedHeight.setEnabled(false);
                                expectedMinimumEducation.setEnabled(false);
                                expectedDistrict.setEnabled(false);
                                expectedMaritalCondition.setEnabled(false);
                                expectedOccupation.setEnabled(false);
                                expectedFinancialBackground.setEnabled(false);
                                expectedFamilyCondition.setEnabled(false);
                                expectedLifePartnerQualities.setEnabled(false);

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


                        userMap.put("expectedAge", ExpectedAge);
                        userMap.put("expectedColor", ExpectedColor);
                        userMap.put("expectedHeight", ExpectedHeight);
                        userMap.put("expectedMinimumEducation", ExpectedMinimumEducation);
                        userMap.put("expectedDistrict", ExpectedDistrict);
                        userMap.put("expectedMaritalCondition", ExpectedMaritalCondition);
                        userMap.put("expectedOccupation", ExpectedOccupation);
                        userMap.put("expectedFinancialBackground", ExpectedFinancialBackground);
                        userMap.put("expectedFamilyCondition", ExpectedFamilyCondition);
                        userMap.put("expectedLifePartnerQualities", ExpectedLifePartnerQualities);
                        userMap.put("user_id", userID);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                expectedAge.setEnabled(false);
                                expectedColor.setEnabled(false);
                                expectedHeight.setEnabled(false);
                                expectedMinimumEducation.setEnabled(false);
                                expectedDistrict.setEnabled(false);
                                expectedMaritalCondition.setEnabled(false);
                                expectedOccupation.setEnabled(false);
                                expectedFinancialBackground.setEnabled(false);
                                expectedFamilyCondition.setEnabled(false);
                                expectedLifePartnerQualities.setEnabled(false);

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
