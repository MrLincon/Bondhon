package com.matrimony.bd.Activity.QnA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrimony.bd.R;

import java.util.ArrayList;

public class CategoriesRVAdapter extends RecyclerView.Adapter<CategoriesRVAdapter.ViewHolder> {

    // variables for array list and context.

    private ArrayList<CategoriesModal> categoriesModalArrayList;
    private Context context;

    // creating a constructor.
    public CategoriesRVAdapter(ArrayList<CategoriesModal> categoriesModalArrayList, Context context) {
        this.categoriesModalArrayList = categoriesModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new CategoriesRVAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_categories_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesRVAdapter.ViewHolder holder, int position) {

        // setting data to our views on below line.
        CategoriesModal modal = categoriesModalArrayList.get(position);
        holder.categoryTV.setText(modal.getNewsCategory());
        NewsRVAdapter adapter = new NewsRVAdapter(modal.getNewsModalArrayList(), context);
        holder.newsRV.setLayoutManager(new LinearLayoutManager(context));
        holder.newsRV.setAdapter(adapter);

        // on below line we are getting our expanded boolean.
        boolean expanded = modal.isExpanded();
        if (expanded) {
            // if it is true then we have to make our recycler view card visible.
            holder.newsCV.setVisibility(View.VISIBLE);
        } else {
            // if it is false then we have to make
            // recycler view card invisible
            // we will be using a visibility gone.
            holder.newsCV.setVisibility(View.GONE);
        }

        // adding click listener to our category tv.
        holder.categoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click we are
                // setting our expanded boolean
                modal.setExpanded(!modal.isExpanded());
                // after updating data we are calling
                // a notify item changed method.
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list on below line.
        return categoriesModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating new variables for our views.
        private RecyclerView newsRV;
        private TextView categoryTV;
        private CardView newsCV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views.
            newsCV = itemView.findViewById(R.id.idCVCategory);
            categoryTV = itemView.findViewById(R.id.idTVCategory);
            newsRV = itemView.findViewById(R.id.idRVNews);
        }
    }
}

