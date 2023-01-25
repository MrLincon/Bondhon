package com.matrimony.bd.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.matrimony.bd.Activity.UpdateProfileActivity;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF1;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF10;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF11;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF2;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF3;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF4;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF5;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF6;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF7;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF8;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF9;
import com.matrimony.bd.R;


public class FragmentProfileData extends Fragment {

    View view;

    CardView update, card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11;
    TextView profileStatus;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_data, container, false);

        update = view.findViewById(R.id.update);
        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);
        card6 = view.findViewById(R.id.card6);
        card7 = view.findViewById(R.id.card7);
        card8 = view.findViewById(R.id.card8);
        card9 = view.findViewById(R.id.card9);
        card10 = view.findViewById(R.id.card10);
        card11 = view.findViewById(R.id.card11);
        profileStatus = view.findViewById(R.id.profile_status);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        document_reference = db.collection("userDetails").document(userID);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(update);
            }
        });

        db.collection("userDetails").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("bioDataNumber");
                Log.d("status", "onSuccess: "+status);

                if (status!=null){
                    profileStatus.setText("বায়োডাটা নংঃ "+status);
                }else{
                    profileStatus.setText("আপনার বায়োডাটা সম্পূর্ণ নয়");
                }
            }
        });

        loadData();

        return view;
    }

    private void loadData() {

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF1 bottomSheet = new BottomSheetF1();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF2 bottomSheet = new BottomSheetF2();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF3 bottomSheet = new BottomSheetF3();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF4 bottomSheet = new BottomSheetF4();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF5 bottomSheet = new BottomSheetF5();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF6 bottomSheet = new BottomSheetF6();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF7 bottomSheet = new BottomSheetF7();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF8 bottomSheet = new BottomSheetF8();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF9 bottomSheet = new BottomSheetF9();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF10 bottomSheet = new BottomSheetF10();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            BottomSheetF11 bottomSheet = new BottomSheetF11();
                            bottomSheet.show(getFragmentManager(), "BottomSheet");

                        } else {
                            Toast.makeText(getActivity(), "Please, update your profile!", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        db.collection("userDetails").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String status = documentSnapshot.getString("bioDataNumber");
                Log.d("status", "onSuccess: "+status);

                if (status!=null){
                    profileStatus.setText("বায়োডাটা নংঃ "+status);
                }else{
                    profileStatus.setText("আপনার বায়োডাটা সম্পূর্ণ নয়");
                }
            }
        });
    }
}
