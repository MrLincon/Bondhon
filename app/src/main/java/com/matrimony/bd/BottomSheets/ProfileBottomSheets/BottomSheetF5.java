package com.matrimony.bd.BottomSheets.ProfileBottomSheets;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class BottomSheetF5 extends BottomSheetDialogFragment {

    TextView fathersName, mothersName, fathersOccupation, mothersOccupation, brothers,
            sisters, familyMemberOccupation, familyCondition;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    public BottomSheetF5() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_f5, container, false);

        fathersName = view.findViewById(R.id.fathersName);
        mothersName = view.findViewById(R.id.mothersName);
        fathersOccupation = view.findViewById(R.id.fathersOccupation);
        mothersOccupation = view.findViewById(R.id.mothersOccupation);
        sisters = view.findViewById(R.id.sisters);
        brothers = view.findViewById(R.id.brothers);
        familyMemberOccupation = view.findViewById(R.id.familyMemberOccupation);
        familyCondition = view.findViewById(R.id.familyCondition);

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
}
