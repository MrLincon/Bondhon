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


public class Fragment10 extends Fragment {

    View view;

    TextInputEditText bioDataApproval, promiseToAllah, authorityNotAtFault;

    CardView save;

    RadioGroup rgYesNo;

    Dialog popup;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_10, container, false);

        bioDataApproval = view.findViewById(R.id.bioDataApproval);
        promiseToAllah = view.findViewById(R.id.promiseToAllah);
        authorityNotAtFault = view.findViewById(R.id.authorityNotAtFault);
        save = view.findViewById(R.id.save);

        bioDataApproval.setFocusable(false);
        promiseToAllah.setFocusable(false);
        authorityNotAtFault.setFocusable(false);

        popup = new Dialog(getActivity());

        ((UpdateProfileActivity) getActivity()).next.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        bioDataApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_yes_no);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgYesNo = popup.findViewById(R.id.rg_yes_no);

                popup.show();

                rgYesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String text = radioButton.getText().toString();

                        bioDataApproval.setText(text);
                        popup.dismiss();
                    }
                });
            }
        });

        promiseToAllah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_yes_no);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgYesNo = popup.findViewById(R.id.rg_yes_no);

                popup.show();

                rgYesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String text = radioButton.getText().toString();

                        promiseToAllah.setText(text);
                        popup.dismiss();
                    }
                });
            }
        });

        authorityNotAtFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_yes_no);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgYesNo = popup.findViewById(R.id.rg_yes_no);

                popup.show();

                rgYesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String text = radioButton.getText().toString();

                        authorityNotAtFault.setText(text);
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

    private void saveData() {

        String BioDataApproval = bioDataApproval.getText().toString();
        String PromiseToAllah = promiseToAllah.getText().toString();
        String AuthorityNotAtFault = authorityNotAtFault.getText().toString();

        if (!BioDataApproval.isEmpty() && !PromiseToAllah.isEmpty()
                && !AuthorityNotAtFault.isEmpty()) {

            if (!BioDataApproval.equals("না") && !PromiseToAllah.equals("না") && !AuthorityNotAtFault.equals("না")) {

                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        popup.setContentView(R.layout.popup_saving);
                        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popup.show();

                        if (!documentSnapshot.exists()) {

                            final String id = document_reference.getId();
                            Map<String, Object> userMap = new HashMap<>();

                            userMap.put("bioDataApproval", BioDataApproval);
                            userMap.put("promiseToAllah", PromiseToAllah);
                            userMap.put("authorityNotAtFault", AuthorityNotAtFault);
                            userMap.put("user_id", userID);
                            userMap.put("id", id);
                            userMap.put("timestamp", FieldValue.serverTimestamp());
                            document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    bioDataApproval.setEnabled(false);
                                    promiseToAllah.setEnabled(false);
                                    authorityNotAtFault.setEnabled(false);

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

                            userMap.put("bioDataApproval", BioDataApproval);
                            userMap.put("promiseToAllah", PromiseToAllah);
                            userMap.put("authorityNotAtFault", AuthorityNotAtFault);
                            userMap.put("user_id", userID);
                            userMap.put("timestamp", FieldValue.serverTimestamp());
                            document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    bioDataApproval.setEnabled(false);
                                    promiseToAllah.setEnabled(false);
                                    authorityNotAtFault.setEnabled(false);

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

            }else{
                Toast.makeText(getContext(), "না উত্তর গ্রহণযোগ্য নয়!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "এই (*) চিহ্ন দেওয়া প্রশ্ন অবশই উত্তর দিতে হবে!", Toast.LENGTH_SHORT).show();
        }
    }

}
