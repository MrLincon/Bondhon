package com.matrimony.bd.Fragments.ProfileFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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


public class Fragment2 extends Fragment {

    View view;

    TextInputEditText bioDataType, maritalCondition, permanentAddress, permanentDivision, currentAddress,
            currentDivision, birthDate, colorOfBody, height, weight, bloodGroup, occupation, monthlyIncome;

    CardView save;

    RadioGroup rgBioDataType, rgMaritalCondition, rgPermanentAddress, rgPermanentDivision, rgCurrentAddress,
            rgCurrentDivision, rgColorOfBody, rgHeight, rgWeight, rgBloodGroup;

    Dialog popup;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);

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
        save = view.findViewById(R.id.save);



        popup = new Dialog(getActivity());

        ((UpdateProfileActivity) getActivity()).next.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();


        popupData();


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

    private void popupData() {

        bioDataType.setFocusable(false);
        maritalCondition.setFocusable(false);
        permanentAddress.setFocusable(false);
        permanentDivision.setFocusable(false);
        currentAddress.setFocusable(false);
        currentDivision.setFocusable(false);
        colorOfBody.setFocusable(false);
        height.setFocusable(false);
        weight.setFocusable(false);
        bloodGroup.setFocusable(false);

        bioDataType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_biodata_type);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgBioDataType = popup.findViewById(R.id.rg_bioDataType);

                popup.show();

                rgBioDataType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String BioDataType = radioButton.getText().toString();

                        bioDataType.setText(BioDataType);
                        popup.dismiss();
                    }
                });
            }
        });

        maritalCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_marital_condition);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgMaritalCondition = popup.findViewById(R.id.rg_maritalCondition);

                popup.show();

                rgMaritalCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String MaritalCondition = radioButton.getText().toString();

                        maritalCondition.setText(MaritalCondition);
                        popup.dismiss();
                    }
                });
            }
        });

        permanentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_parmanent_address);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgPermanentAddress = popup.findViewById(R.id.rg_permanentAddress);

                popup.show();

                rgPermanentAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String PermanentAddress = radioButton.getText().toString();

                        permanentAddress.setText(PermanentAddress);
                        popup.dismiss();
                    }
                });
            }
        });

        permanentDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_parmanent_division);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgPermanentDivision = popup.findViewById(R.id.rg_permanentDivision);

                popup.show();

                rgPermanentDivision.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String PermanentDivision = radioButton.getText().toString();

                        permanentDivision.setText(PermanentDivision);
                        popup.dismiss();
                    }
                });
            }
        });

        currentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_current_address);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgCurrentAddress = popup.findViewById(R.id.rg_currentAddress);

                popup.show();

                rgCurrentAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String CurrentAddress = radioButton.getText().toString();

                        currentAddress.setText(CurrentAddress);
                        popup.dismiss();
                    }
                });
            }
        });

        currentDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_current_division);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgCurrentDivision = popup.findViewById(R.id.rg_currentDivision);

                popup.show();

                rgCurrentDivision.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String CurrentDivision = radioButton.getText().toString();

                        currentDivision.setText(CurrentDivision);
                        popup.dismiss();
                    }
                });
            }
        });

        colorOfBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_color_of_body);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgColorOfBody = popup.findViewById(R.id.rg_colorOfBody);

                popup.show();

                rgColorOfBody.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String ColorOfBody = radioButton.getText().toString();

                        colorOfBody.setText(ColorOfBody);
                        popup.dismiss();
                    }
                });
            }
        });

        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_height);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgHeight = popup.findViewById(R.id.rg_height);

                popup.show();

                rgHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String Height = radioButton.getText().toString();

                        height.setText(Height);
                        popup.dismiss();
                    }
                });
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_weight);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgWeight = popup.findViewById(R.id.rg_weight);

                popup.show();

                rgWeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String Weight = radioButton.getText().toString();

                        weight.setText(Weight);
                        popup.dismiss();
                    }
                });
            }
        });

        bloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setContentView(R.layout.popup_blood_group);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                rgBloodGroup = popup.findViewById(R.id.rg_bloodGroup);

                popup.show();

                rgBloodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                        String BloodGroup = radioButton.getText().toString();

                        bloodGroup.setText(BloodGroup);
                        popup.dismiss();
                    }
                });
            }
        });

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


    private void saveData() {

        String BioDataType = bioDataType.getText().toString();
        String MaritalCondition = maritalCondition.getText().toString();
        String PermanentDistrict = permanentAddress.getText().toString();
        String PermanentDivision = permanentDivision.getText().toString();
        String CurrentDistrict = currentAddress.getText().toString();
        String CurrentDivision = currentDivision.getText().toString();
        String BirthDate = birthDate.getText().toString();
        String ColorOfBody = colorOfBody.getText().toString();
        String Height = height.getText().toString();
        String Weight = weight.getText().toString();
        String BloodGroup = bloodGroup.getText().toString();
        String Occupation = occupation.getText().toString();
        String MonthlyIncome = monthlyIncome.getText().toString();

        if (!BioDataType.isEmpty() && !MaritalCondition.isEmpty() && !PermanentDistrict.isEmpty() && !PermanentDivision.isEmpty()
                && !CurrentDistrict.isEmpty() && !CurrentDivision.isEmpty() && !BirthDate.isEmpty()
                && !BloodGroup.isEmpty() && !Occupation.isEmpty()) {

            document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    popup.setContentView(R.layout.popup_saving);
                    popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    popup.show();

                    if (!documentSnapshot.exists()) {

                        final String id = document_reference.getId();
                        Map<String, Object> userMap = new HashMap<>();

                        userMap.put("bioDataType", BioDataType);
                        userMap.put("maritalCondition", MaritalCondition);
                        userMap.put("permanentDistrict", PermanentDistrict);
                        userMap.put("permanentDivision", PermanentDivision);
                        userMap.put("currentDistrict", CurrentDistrict);
                        userMap.put("currentDivision", CurrentDivision);
                        userMap.put("birthDate", birthDate);
                        userMap.put("colorOfBody", ColorOfBody);
                        userMap.put("height", Height);
                        userMap.put("weight", Weight);
                        userMap.put("bloodGroup", BloodGroup);
                        userMap.put("occupation", Occupation);
                        userMap.put("monthlyIncome", MonthlyIncome);
                        userMap.put("user_id", userID);
                        userMap.put("id", id);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                bioDataType.setEnabled(false);
                                maritalCondition.setEnabled(false);
                                permanentAddress.setEnabled(false);
                                permanentDivision.setEnabled(false);
                                currentAddress.setEnabled(false);
                                currentDivision.setEnabled(false);
                                birthDate.setEnabled(false);
                                colorOfBody.setEnabled(false);
                                height.setEnabled(false);
                                weight.setEnabled(false);
                                bloodGroup.setEnabled(false);
                                occupation.setEnabled(false);
                                monthlyIncome.setEnabled(false);

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

                        userMap.put("bioDataType", BioDataType);
                        userMap.put("maritalCondition", MaritalCondition);
                        userMap.put("permanentDistrict", PermanentDistrict);
                        userMap.put("permanentDivision", PermanentDivision);
                        userMap.put("currentDistrict", CurrentDistrict);
                        userMap.put("currentDivision", CurrentDivision);
                        userMap.put("birthDate", BirthDate);
                        userMap.put("colorOfBody", ColorOfBody);
                        userMap.put("height", Height);
                        userMap.put("weight", Weight);
                        userMap.put("bloodGroup", BloodGroup);
                        userMap.put("occupation", Occupation);
                        userMap.put("monthlyIncome", MonthlyIncome);
                        userMap.put("user_id", userID);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        document_reference.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                bioDataType.setEnabled(false);
                                maritalCondition.setEnabled(false);
                                permanentAddress.setEnabled(false);
                                permanentDivision.setEnabled(false);
                                currentAddress.setEnabled(false);
                                currentDivision.setEnabled(false);
                                birthDate.setEnabled(false);
                                colorOfBody.setEnabled(false);
                                height.setEnabled(false);
                                weight.setEnabled(false);
                                bloodGroup.setEnabled(false);
                                occupation.setEnabled(false);
                                monthlyIncome.setEnabled(false);

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
