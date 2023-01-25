package com.matrimony.bd.BottomSheets.ProfileBottomSheets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BottomSheetF2 extends BottomSheetDialogFragment {

    TextView bioDataType, maritalCondition, permanentAddress, permanentDivision, currentAddress,
            currentDivision, birthDate, colorOfBody, height, weight, bloodGroup, occupation, monthlyIncome;


    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;


    public BottomSheetF2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_f2, container, false);

        bioDataType = view.findViewById(R.id.bioDataType);
        maritalCondition = view.findViewById(R.id.maritalCondition);
        permanentAddress = view.findViewById(R.id.permanentAddress);
        permanentDivision = view.findViewById(R.id.permanentDistrict);
        currentAddress = view.findViewById(R.id.currentAddress);
        currentDivision = view.findViewById(R.id.currentDistrict);
        birthDate = view.findViewById(R.id.birthDate);
        colorOfBody = view.findViewById(R.id.colorOfBody);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        bloodGroup = view.findViewById(R.id.bloodGroup);
        occupation = view.findViewById(R.id.occupation);
        monthlyIncome = view.findViewById(R.id.monthlyIncome);

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


                    String BioDataType = documentSnapshot.getString("bioDataType");
                    String MaritalCondition = documentSnapshot.getString("maritalCondition");
                    String PermanentAddress = documentSnapshot.getString("permanentDistrict");
                    String PermanentDivision = documentSnapshot.getString("permanentDivision");
                    String CurrentAddress = documentSnapshot.getString("currentDistrict");
                    String CurrentDivision = documentSnapshot.getString("currentDivision");
                    String BirthDate = documentSnapshot.getString("birthDate");
                    String ColorOfBody = documentSnapshot.getString("colorOfBody");
                    String Height = documentSnapshot.getString("height");
                    String Weight = documentSnapshot.getString("weight");
                    String BloodGroup = documentSnapshot.getString("bloodGroup");
                    String Occupation = documentSnapshot.getString("occupation");
                    String MonthlyIncome = documentSnapshot.getString("monthlyIncome");

                    bioDataType.setText(BioDataType);
                    maritalCondition.setText(MaritalCondition);
                    permanentAddress.setText(PermanentAddress);
                    permanentDivision.setText(PermanentDivision);
                    currentAddress.setText(CurrentAddress);
                    currentDivision.setText(CurrentDivision);
                    birthDate.setText(BirthDate);
                    colorOfBody.setText(ColorOfBody);
                    height.setText(Height);
                    weight.setText(Weight);
                    bloodGroup.setText(BloodGroup);
                    occupation.setText(Occupation);
                    monthlyIncome.setText(MonthlyIncome);

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
