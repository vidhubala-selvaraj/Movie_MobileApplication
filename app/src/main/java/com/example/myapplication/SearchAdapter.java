package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> media_type = new ArrayList<>();
    private ArrayList<String> sTitle = new ArrayList<>();
    private ArrayList<String> sRating = new ArrayList<>();
    private ArrayList<String> sYear = new ArrayList<>();
    private Context context;
    Toast toast;

    public SearchAdapter(Context context, ArrayList<String> images, ArrayList<String> id, ArrayList<String> media_type, ArrayList<String> sTitle, ArrayList<String> sRating, ArrayList<String> sYear) {
        this.images = images;
        this.sRating = sRating;
        this.sYear = sYear;
        this.id = id;
        this.media_type = media_type;
        this.sTitle = sTitle;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(images.get(position))
                .into(holder.image);



        holder.title.setText(sTitle.get(position));
        holder.rating.setText(sRating.get(position));

        String movieAndYearStr = media_type.get(position).toUpperCase() + " (" + sYear.get(position) + ")";
        holder.mediaAndYear.setText(movieAndYearStr);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                try{
                    Intent intent = new Intent(context, DetailsActivity.class);
                    bundle.putString("id", id.get(position));
                    bundle.putString("media_type", (media_type.get(position)));
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
//                    Toast.makeText(context,"Your answer is correct!" , Toast.LENGTH_LONG ).show();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView rating, mediaAndYear, title;
        CardView card;



        public ViewHolder(View itemView){
            super(itemView);

            card  = itemView.findViewById(R.id.search_cards);
            image = itemView.findViewById(R.id.search_image);
            mediaAndYear = itemView.findViewById(R.id.search_year);
            title = itemView.findViewById(R.id.search_poster_title);
            rating = itemView.findViewById(R.id.search_rating);
        }
    }
}
