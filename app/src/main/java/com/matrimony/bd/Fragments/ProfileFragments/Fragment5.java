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


public class Fragment5 extends Fragment {

    View view;

    TextInputEditText fathersName, mothersName, fathersOccupation, mothersOccupation, brothers,
            sisters, familyMemberOccupation, familyCondition;

    CardView save;

    RadioGroup rgBrothers, rgSisters;

    Dialog popup;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_5, container, false);

        fathersName = view.findViewById(R.id.fathersName);
        mothersName = view.findViewById(R.id.mothersName);
        fathersOccupation = view.findViewById(R.id.fathersOccupation);
        mothersOccupation = view.findViewById(R.id.mothersOccupation);
        sisters = view.findViewById(R.id.sisters);
        brothers = view.findViewById(R.id.brothers);
        familyMemberOccupation = view.findViewById(R.id.familyMemberOccupation);
        familyCondition = view.findViewById(R.id.familyCondition);
        save = view.findViewById(R.id.save);

        brothers.setFocusable(false);
        sisters.setFocusable(false);

        popup = new Dialog(getActivity());

        ((UpdateProfileActivity) getActivity()).next.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        brothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_brother);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgBrothers = popup.findViewById(R.id.rg_brothers);

                popup.show();

                rgBrothers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String Brothers = radioButton.getText().toString();

                        brothers.setText(Brothers);
                        popup.dismiss();
                    }
                });
            }
        });

        sisters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_sister);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgSisters = popup.findViewById(R.id.rg_sisters);

                popup.show();

                rgSisters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String Sisters = radioButton.getText().toString();

                        sisters.setText(Sisters);
                        popup.dismiss();
                    }
                });
            }
        });


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


                    String FathersName = documentSnapshot.getString("fathersName");
                    String MothersName = documentSnapshot.getString("mothersName");
                    String FathersOccupation = documentSnapshot.getString("fathersOccupation");
                    String MothersOccupation = documentSnapshot.getString("mothersOccupation");
                    String Sisters = documentSnapshot.getString("sisters");
                    String Brothers = documentSnapshot.getString("brothers");
                    String FamilyMemberOccupation = documentSnapshot.getString("familyMemberOccupation");
                    String FamilyCondition = documentSnapshot.getString("familyCondition");

                    fathersName.setText(FathersName);
                    mothersName.setText(MothersName);
                    fathersOccupation.setText(FathersOccupation);
                    mothersOccupation.setText(MothersOccupation);
                    sisters.setText(Sisters);
                    brothers.setText(Brothers);
                    familyMemberOccupation.setText(FamilyMemberOccupation);
                    familyCondition.setText(FamilyCondition);

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

        String FathersName = fathersName.getText().toString();
        String MothersName = mothersName.getText().toString();
        String FathersOccupation = fathersOccupation.getText().toString();
        String MothersOccupation = mothersOccupation.getText().toString();
        String Sisters = sisters.getText().toString();
        String Brothers = brothers.getText().toString();
        String FamilyMemberOccupation = familyMemberOccupation.getText().toString();
        String FamilyCondition = familyCondition.getText().toString();

        if (!FathersName.isEmpty() && !MothersName.isEmpty()
                && !FathersOccupation.isEmpty() && !MothersOccupation.isEmpty()
                && !Sisters.isEmpty() && !Brothers.isEmpty()
                && !FamilyCondition.isEmpty() ) {

            document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    popup.setContentView(R.layout.popup_saving);
                    popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popup.show();

                    if (!documentSnapshot.exists()) {

                        final String id = document_reference.getId();
                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("fathersName", FathersName);
                        userMap.put("mothersName", MothersName);
                        userMap.put("fathersOccupation", FathersOccupation);
                        userMap.put("mothersOccupation", MothersOccupation);
                        userMap.put("sisters", Sisters);
                        userMap.put("brothers", Brothers);
                        userMap.put("familyMemberOccupation", FamilyMemberOccupation);
                        userMap.put("familyCondition", FamilyCondition);
                        userMap.put("user_id", userID);
                        userMap.put("id", id);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                fathersName.setEnabled(false);
                                mothersName.setEnabled(false);
                                fathersOccupation.setEnabled(false);
                                mothersOccupation.setEnabled(false);
                                sisters.setEnabled(false);
                                brothers.setEnabled(false);
                                familyMemberOccupation.setEnabled(false);
                                familyCondition.setEnabled(false);

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

                        userMap.put("fathersName", FathersName);
                        userMap.put("mothersName", MothersName);
                        userMap.put("fathersOccupation", FathersOccupation);
                        userMap.put("mothersOccupation", MothersOccupation);
                        userMap.put("sisters", Sisters);
                        userMap.put("brothers", Brothers);
                        userMap.put("familyMemberOccupation", FamilyMemberOccupation);
                        userMap.put("familyCondition", FamilyCondition);
                        userMap.put("user_id", userID);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                fathersName.setEnabled(false);
                                mothersName.setEnabled(false);
                                fathersOccupation.setEnabled(false);
                                mothersOccupation.setEnabled(false);
                                sisters.setEnabled(false);
                                brothers.setEnabled(false);
                                familyMemberOccupation.setEnabled(false);
                                familyCondition.setEnabled(false);

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
