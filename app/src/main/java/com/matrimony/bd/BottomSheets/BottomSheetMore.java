package com.matrimony.bd.BottomSheets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.matrimony.bd.Activity.AboutusActivity;
import com.matrimony.bd.Activity.MainActivity;
import com.matrimony.bd.Activity.QnA.QNAActivity;
import com.matrimony.bd.Activity.Tutorial.TutorialActivity;
import com.matrimony.bd.R;

public class BottomSheetMore extends BottomSheetDialogFragment {

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;
    CardView qna,tut;
    public BottomSheetMore() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_more, container, false);
          qna=view.findViewById(R.id.q_and_a);
          tut=view.findViewById(R.id.tutorial);
          qna.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent=new Intent(getActivity(), QNAActivity.class);
                  startActivity(intent);
              }
          });
        tut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AboutusActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


    }
}
