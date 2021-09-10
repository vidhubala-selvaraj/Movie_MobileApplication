package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>{

    private ArrayList<String> rReviewsTitle = new ArrayList<>();
    private ArrayList<String> rReviewsRating = new ArrayList<>();
    private ArrayList<String> rReviewsContent = new ArrayList<>();
    private Context rcontext;

    public ReviewsAdapter(Context rcontext, ArrayList<String> rReviewsTitle, ArrayList<String> rReviewsRating, ArrayList<String> rReviewsContent) {
        this.rReviewsTitle = rReviewsTitle;
        this.rReviewsRating = rReviewsRating;
        this.rReviewsContent = rReviewsContent;
        this.rcontext = rcontext;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reviewsitem, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        holder.reviews_title.setText(rReviewsTitle.get(position));
        holder.reviews_rating.setText(rReviewsRating.get(position));
        holder.reviews_content.setText(rReviewsContent.get(position));

        holder.reviews_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(rcontext, ReviewsActivity.class);
                bundle.putString("revTitle", rReviewsTitle.get(position));
                bundle.putString("revRating", rReviewsRating.get(position));
                bundle.putString("revContent", rReviewsContent.get(position));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                rcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rReviewsTitle.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView reviews_title;
        TextView reviews_rating;
        TextView reviews_content;

        public ReviewsViewHolder(View itemView) {
            super(itemView);

            reviews_title = itemView.findViewById(R.id.itemreviews_title);
            reviews_rating = itemView.findViewById(R.id.itemreviews_rating);
            reviews_content = itemView.findViewById(R.id.itemreviews_content);
        }
    }
}
