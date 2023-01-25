package com.matrimony.bd.Fragments.ProfileFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.matrimony.bd.Activity.UpdateProfileActivity;
import com.matrimony.bd.R;

import java.util.HashMap;
import java.util.Map;


public class Fragment1 extends Fragment {

    View view;

    TextInputEditText name, parents;
    CardView save;

    Dialog popup;

    int count;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);

        name = view.findViewById(R.id.name);
        parents = view.findViewById(R.id.parents);
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

        db.collection("userDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }

                            Log.d("CountGG", "onComplete: "+count);
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
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

                    String Name = documentSnapshot.getString("name");
                    String Parents = documentSnapshot.getString("parents");

                    name.setText(Name);
                    parents.setText(Parents);

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



        String Name = name.getText().toString();
        String Parents = parents.getText().toString();

        if (!Name.isEmpty() && !Parents.isEmpty()) {

            document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    popup.setContentView(R.layout.popup_saving);
                    popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popup.show();

                    if (!documentSnapshot.exists()) {

                        final String id = document_reference.getId();
                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("name", Name);
                        userMap.put("parents", Parents);
                        userMap.put("user_id", userID);
                        userMap.put("id", id);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                name.setEnabled(false);
                                parents.setEnabled(false);

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

                        userMap.put("name", Name);
                        userMap.put("parents", Parents);
                        userMap.put("user_id", userID);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                name.setEnabled(false);
                                parents.setEnabled(false);

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
