package com.matrimony.bd.Models;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.matrimony.bd.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.HashMap;
import java.util.Map;

public class FriendAdapter extends FirestorePagingAdapter<Friend, FriendAdapter.FriendHolder> {

    private OnItemClickListener listener;
    private Context mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public FriendAdapter(@NonNull FirestorePagingOptions<Friend> options, SwipeRefreshLayout swipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FriendHolder holder, int position, @NonNull Friend model) {
        userID = model.getUser_id();

        Dialog popup = new Dialog(mContext);

        holder.Name.setText("নাম: "+model.getName());
        holder.BioDataNumber.setText("বায়োডাটা নং: "+model.getBioDataNumber());

        holder.More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_remove_friend);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                CardView remove = popup.findViewById(R.id.card_yes);

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("userDetails").document(mAuth.getUid()).collection("acceptedRequest").document(userID).delete();
                        Toast.makeText(mContext, "ফ্রেন্ড রিমুভড...", Toast.LENGTH_SHORT).show();
                        popup.dismiss();
                        refresh();
                    }
                });
                popup.show();
            }
        });
    }


    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state) {

            case LOADING_INITIAL:
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d("Paging Log", "Loading Initial data");
                break;
            case LOADING_MORE:
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d("Paging Log", "Loading next page");
                break;
            case FINISHED:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "All data loaded");
                break;
            case LOADED:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "Total data loaded "+getItemCount());
                break;
            case ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "Error loading data");
                break;
        }
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_layout,
                parent, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        return new FriendHolder(view);
    }

    class FriendHolder extends RecyclerView.ViewHolder {

        TextView Name, BioDataNumber;
        ImageView More;

        public FriendHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            BioDataNumber = itemView.findViewById(R.id.biodata_number);
            More = itemView.findViewById(R.id.more);

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
