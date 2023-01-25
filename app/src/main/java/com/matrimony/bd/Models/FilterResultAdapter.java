package com.matrimony.bd.Models;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.matrimony.bd.R;

public class FilterResultAdapter extends FirestorePagingAdapter<FilterResult, FilterResultAdapter.FilterHolder> {

    private OnItemClickListener listener;
    private Context mContext;

    public FilterResultAdapter(@NonNull FirestorePagingOptions<FilterResult> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final FilterHolder holder, int position, @NonNull FilterResult model) {
        holder.Name.setText(model.getName());
        holder.Division.setText(model.getPermanentDivision());
        holder.MaritalCondition.setText(model.getMaritalCondition());
        holder.EducationMedium.setText(model.getEducationMedium());
        holder.PrayerPerDay.setText(model.getPrayerPerDay());
        holder.Parents.setText(model.getParents());
    }

    @NonNull
    @Override
    public FilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_result_layout,
                parent, false);

        return new FilterHolder(view);
    }

    class FilterHolder extends RecyclerView.ViewHolder {
        TextView Name, Division, MaritalCondition, EducationMedium, PrayerPerDay, Parents;

        public FilterHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Division = itemView.findViewById(R.id.division);
            MaritalCondition = itemView.findViewById(R.id.marital_condition);
            EducationMedium = itemView.findViewById(R.id.education_medium);
            PrayerPerDay = itemView.findViewById(R.id.prayer);
            Parents = itemView.findViewById(R.id.parents);

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
