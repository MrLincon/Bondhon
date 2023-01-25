package com.matrimony.bd.Models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.matrimony.bd.R;

import java.util.HashMap;
import java.util.Map;

public class HomeDataAdapter extends FirestorePagingAdapter<HomeData, HomeDataAdapter.HomeDataHolder> {

    private OnItemClickListener listener;
    private Context mContext;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public HomeDataAdapter(@NonNull FirestorePagingOptions<HomeData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final HomeDataHolder holder, int position, @NonNull HomeData model) {
        final String post_id = getItem(position).getId();
        final String user_id = firebaseAuth.getCurrentUser().getUid();

        holder.Name.setText(model.getName());
        holder.Division.setText("জেলাঃ " + model.getPermanentDivision());
        holder.MaritalCondition.setText("বৈবাহিক অবস্থাঃ " + model.getMaritalCondition());
        holder.Birth.setText("জন্মসালঃ " + model.getBirthDate());
        holder.Occupation.setText("পেশাঃ " + model.getOccupation());
        holder.BioDataNumber.setText(model.getBioDataNumber());

        firebaseFirestore.collection("userDetails").document(post_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String Status = documentSnapshot.getString("featured");

                if (Status.equals("YES")){
                    Log.d("status", "onSuccess: "+Status);
                    holder.Featured.setVisibility(View.VISIBLE);
                }
            }
        });

//        holder.Featured.setVisibility(View.INVISIBLE);

        holder.Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Add favourite
                firebaseFirestore.collection("userDetails").document(post_id).collection("Favourite").document(user_id).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (!task.getResult().exists()) {

                                    Map<String, Object> favourite = new HashMap<>();
                                    favourite.put("timestamp", FieldValue.serverTimestamp());

                                    firebaseFirestore.collection("userDetails/" + post_id + "/Favourite").document(user_id).set(favourite);
                                    firebaseFirestore.collection("userDetails").document(post_id).update("favouriteList", FieldValue.arrayUnion(user_id));

                                } else {

                                    firebaseFirestore.collection("userDetails").document(post_id).collection("Favourite").document(user_id).delete();
                                    firebaseFirestore.collection("userDetails").document(post_id).update("favouriteList", FieldValue.arrayRemove(user_id));

                                }
                            }
                        });
            }
        });


        //Update Favourite icon
        firebaseFirestore.collection("userDetails").document(post_id).collection("Favourite").document(user_id)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        if (currentUser != null) {
                            if (documentSnapshot.exists()) {

                                holder.Favourite.setImageResource(R.drawable.ic_heart_selected);

                            } else {

                                holder.Favourite.setImageResource(R.drawable.ic_heart);

                            }
                        }

                    }
                });




    }

    @NonNull
    @Override
    public HomeDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_data_layout,
                parent, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        return new HomeDataHolder(view);
    }

    class HomeDataHolder extends RecyclerView.ViewHolder {

        TextView Name, Division, MaritalCondition, Birth, Occupation, Featured, BioDataNumber;
        ImageView Favourite;

        public HomeDataHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Division = itemView.findViewById(R.id.division);
            MaritalCondition = itemView.findViewById(R.id.marital_condition);
            Birth = itemView.findViewById(R.id.birthDate);
            Occupation = itemView.findViewById(R.id.occupation);
            Favourite = itemView.findViewById(R.id.favourite);
            Featured = itemView.findViewById(R.id.featured);
            BioDataNumber = itemView.findViewById(R.id.biodata_number);

            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
