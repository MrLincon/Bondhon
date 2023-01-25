package com.matrimony.bd.Fragments.ProfileFragments;

import android.app.Dialog;
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


public class Fragment11 extends Fragment {

    View view;

    TextInputEditText parentsNumber, parentsIdentity, yourEmail, yourContact;
    CardView save;

    Dialog popup;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_11, container, false);

        parentsNumber = view.findViewById(R.id.parentsNumber);
        parentsIdentity = view.findViewById(R.id.parentsIdentity);
        yourEmail = view.findViewById(R.id.yourEmail);
        yourContact = view.findViewById(R.id.yourContact);
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


                    String ParentsNumber = documentSnapshot.getString("parentsNumber");
                    String ParentsIdentity = documentSnapshot.getString("parentsIdentity");
                    String YourEmail = documentSnapshot.getString("yourEmail");
                    String YourContact = documentSnapshot.getString("yourContact");

                    parentsNumber.setText(ParentsNumber);
                    parentsIdentity.setText(ParentsIdentity);
                    yourEmail.setText(YourEmail);
                    yourContact.setText(YourContact);

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

        String ParentsNumber = parentsNumber.getText().toString();
        String ParentsIdentity = parentsIdentity.getText().toString();
        String YourEmail = yourEmail.getText().toString();
        String YourContact = yourContact.getText().toString();

        if (!ParentsNumber.isEmpty() && !ParentsIdentity.isEmpty() && !YourEmail.isEmpty() && !YourContact.isEmpty()) {

            document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    popup.setContentView(R.layout.popup_saving);
                    popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popup.show();

                    if (!documentSnapshot.exists()) {

                        final String id = document_reference.getId();
                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("parentsNumber", ParentsNumber);
                        userMap.put("parentsIdentity", ParentsIdentity);
                        userMap.put("yourEmail", YourEmail);
                        userMap.put("yourContact", YourContact);
                        userMap.put("featured", "NO");
                        userMap.put("user_id", userID);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                parentsNumber.setEnabled(false);
                                parentsIdentity.setEnabled(false);
                                yourEmail.setEnabled(false);
                                yourContact.setEnabled(false);

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

                        userMap.put("parentsNumber", ParentsNumber);
                        userMap.put("parentsIdentity", ParentsIdentity);
                        userMap.put("yourEmail", YourEmail);
                        userMap.put("yourContact", YourContact);
                        userMap.put("featured", "NO");
                        userMap.put("user_id", userID);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                parentsNumber.setEnabled(false);
                                parentsIdentity.setEnabled(false);
                                yourEmail.setEnabled(false);
                                yourContact.setEnabled(false);

                                bioDataNumber();
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

    private void bioDataNumber(){

        //Get data Count

        db.collection("userDetails").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("bioDataNumber");
                Log.d("status", "onSuccess: "+status);

                if (status==null){
                    db.collection("DocCount").document("7hy6bzZ9Nymubs3ZRrT5").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            String count = documentSnapshot.getString("count");
                            int dataCount = Integer.parseInt(count);

                            Map<String, Object> userMap = new HashMap<>();

                            userMap.put("count", String.valueOf(dataCount+1));
                            db.collection("DocCount").document("7hy6bzZ9Nymubs3ZRrT5").update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });


                            db.collection("userDetails").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                                    String dataCode = null;

                                    if(dataCount==0){
                                        dataCode = "BD000"+1;
                                        //BD0001
                                    }else if(dataCount<10){
                                        dataCode = "BD000"+dataCount;
                                        //BD0009
                                    }else if(dataCount<100){
                                        dataCode = "BD00"+dataCount;
                                        //BD0099
                                    }else if(dataCount<1000){
                                        dataCode = "BD0"+dataCount;
                                        //BD0999
                                    }else if(dataCount>=1000){
                                        dataCode = "BD"+dataCount;
                                        //BD1000+
                                    }


                                    Map<String, Object> userMap = new HashMap<>();

                                    userMap.put("bioDataNumber", dataCode);
                                    userMap.put("status", "pending");
                                    document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getContext(), "আপনার প্রোফাইলটি অপেক্ষমান তালিকায় রাখা হল!", Toast.LENGTH_SHORT).show();
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
                            });


                        }
                    });
                }else{
                    ((UpdateProfileActivity) getActivity()).next.setVisibility(View.VISIBLE);
                    popup.dismiss();
                }
            }
        });



    }

}
