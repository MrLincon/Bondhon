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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.matrimony.bd.R;

import java.util.HashMap;
import java.util.Map;

public class RequestAdapter extends FirestorePagingAdapter<Request, RequestAdapter.RequestHolder> {

    private OnItemClickListener listener;
    private Context mContext;
    private String userID, ID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference document_reference, doc_ref;

    String name, bioDataNumber;

    public RequestAdapter(@NonNull FirestorePagingOptions<Request> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final RequestHolder holder, int position, @NonNull Request model) {
        userID = model.getUser_id();

        db.collection("userDetails").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString("name");
                String bioDataNumber = documentSnapshot.getString("bioDataNumber");
                holder.Name.setText("নাম: "+name);
                holder.BioDataNumber.setText("বায়োডাটা নং: "+bioDataNumber);

            }
        });

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("userDetails").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        name = documentSnapshot.getString("name");
                        bioDataNumber = documentSnapshot.getString("bioDataNumber");

                        doc_ref = db.collection("userDetails").document(mAuth.getUid()).collection("acceptedRequest").document(userID);
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("user_id", userID);
                        userMap.put("name", name);
                        userMap.put("bioDataNumber", bioDataNumber);
                        userMap.put("timestamp", FieldValue.serverTimestamp());
                        doc_ref.set(userMap);

                        db.collection("userDetails").document(mAuth.getUid()).collection("pendingRequest").document(userID).delete();

                        Toast.makeText(mContext, "User Accepted!", Toast.LENGTH_SHORT).show();
                        refresh();
                    }
                });




            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("userDetails").document(mAuth.getUid()).collection("pendingRequest").document(userID).delete();
                Toast.makeText(mContext, "রিকুয়েস্ট ডিলিটেড...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_layout,
                parent, false);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        return new RequestHolder(view);
    }

    class RequestHolder extends RecyclerView.ViewHolder {

        TextView Name, BioDataNumber;
        ImageView Accept, Delete;

        public RequestHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            BioDataNumber = itemView.findViewById(R.id.biodata_number);
            Accept = itemView.findViewById(R.id.accept);
            Delete = itemView.findViewById(R.id.delete);

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
