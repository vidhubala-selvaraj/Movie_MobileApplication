package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.ViewHolder>{
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> media_type = new ArrayList<>();
    private ArrayList<String> wTitle = new ArrayList<>();
    Context context;
    View wView;

    public WatchlistAdapter(Context context, ArrayList<String> id, ArrayList<String> media_type, ArrayList<String> mImageUrls, ArrayList<String> wTitle, View wView) {
        this.mImageUrls = mImageUrls;
        this.id = id;
        this.media_type = media_type;
        this.wTitle = wTitle;
        this.context = context;
        this.wView = wView;
    }

    @NonNull
    @Override
    public WatchlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_watch, parent, false);
        WatchlistAdapter.ViewHolder vh = new WatchlistAdapter.ViewHolder(view); // pass the view to View Holder
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Watchlist_reset(ArrayList<String> mImageUrls, ArrayList<String> id, ArrayList<String> media_type, ArrayList<String> wTitle){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String key = "watchlist";
        String arr_builder = "";
        for(int i = 0; i < id.size() ; i++){
            String val = id.get(i) + "," + media_type.get(i) + "," + mImageUrls.get(i) + "," + wTitle.get(i);
            arr_builder = arr_builder + val + "#";
        }
        editor.putString(key,arr_builder);
        editor.commit();
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(mImageUrls.get(position))
                .centerCrop()
                .into(holder.image);
        holder.remove_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),wTitle.get(position) + " was removed from the Watchlist",Toast.LENGTH_LONG).show();
                mImageUrls.remove(position);
                id.remove(position);
                media_type.remove(position);
                wTitle.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, id.size());
                notifyDataSetChanged();
                if(id.size() == 0){
                    wView.findViewById(R.id.None_WatchlistText).setVisibility(View.VISIBLE);
                }

                Watchlist_reset(mImageUrls, id, media_type, wTitle);
            }
        });
        holder.watchlist_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                try{
                    Intent intent = new Intent(context,DetailsActivity.class);
                    bundle.putString("id", id.get(position));
                    bundle.putString("media_type",media_type.get(position));
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

        String media_types = media_type.get(position);
        if (media_types.equals("tv")){
            media_types = "TV";
        }
        else{
            media_types = "Movie";
        }
        holder.watchlist_media_type.setText(media_types);
    }

    @Override
    public int getItemCount() {
        return id.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView remove_image;
        CardView watchlist_card;
        TextView watchlist_media_type;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.watchlist_poster);
            remove_image = v.findViewById(R.id.remove_image);
            watchlist_card = v.findViewById(R.id.watchlist_card);
            watchlist_media_type = v.findViewById(R.id.watchlist_media_type);

//            image = (ImageView) itemView.findViewById(R.id.imageofdetails);
        }
    }
}
