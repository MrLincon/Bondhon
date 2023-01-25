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

public class BottomSheetF10 extends BottomSheetDialogFragment {

    TextView bioDataApproval, promiseToAllah, authorityNotAtFault;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    public BottomSheetF10() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_f10, container, false);

        bioDataApproval = view.findViewById(R.id.bioDataApproval);
        promiseToAllah = view.findViewById(R.id.promiseToAllah);
        authorityNotAtFault = view.findViewById(R.id.authorityNotAtFault);

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


                    String BioDataApproval = documentSnapshot.getString("bioDataApproval");
                    String PromiseToAllah = documentSnapshot.getString("promiseToAllah");
                    String AuthorityNotAtFault = documentSnapshot.getString("authorityNotAtFault");

                    bioDataApproval.setText(BioDataApproval);
                    promiseToAllah.setText(PromiseToAllah);
                    authorityNotAtFault.setText(AuthorityNotAtFault);

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
