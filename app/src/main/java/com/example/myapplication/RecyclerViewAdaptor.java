package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>{

    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private String media_type;
    private Context mContext;
    Toast toast;

    public RecyclerViewAdaptor(Context mContext, ArrayList<String> mImageUrls, ArrayList<String> id, String media_type, ArrayList<String> title) {
        this.mImageUrls = mImageUrls;
        this.title = title;
        this.media_type = media_type;
        this.id = id;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mContext, DetailsActivity.class);
                bundle.putString("id", id.get(position));
                bundle.putString("media_type", media_type);
                intent.putExtra("image_url", mImageUrls.get(position));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.imageButton);
                //inflating menu from xml resource
                popup.inflate(R.menu.details_menu);
                renameMenu(popup,pref,id.get(position),media_type);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.details_menu_tmdb:
                                String url = "https://www.themoviedb.org/" + media_type + "/" + id.get(position);
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setPackage("com.android.chrome");
                                try {
                                    mContext.startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    toast.makeText(mContext, "unable to open chrome", Toast.LENGTH_SHORT).show();
                                    i.setPackage(null);
                                    mContext.startActivity(i);
                                }
                                return true;
                            case R.id.details_menu_fb:
                                url = "https://www.themoviedb.org/" + media_type + "/" + id.get(position);
                                String fbURL = "https://www.facebook.com/sharer/sharer.php?u=" + url ;
                                i = new Intent(Intent.ACTION_VIEW, Uri.parse(fbURL));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setPackage("com.android.chrome");
                                try {
                                    mContext.startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    toast.makeText(mContext, "unable to open chrome", Toast.LENGTH_SHORT).show();
                                    i.setPackage(null);
                                    mContext.startActivity(i);
                                }
                                return true;

                            //handle menu2 click
                            case R.id.details_menu_twitter:
                                url = "https://www.themoviedb.org/" + media_type + "/" + id.get(position);
                                String tweetUrl = "https://twitter.com/intent/tweet?text=Check this out! " + url;
                                //handle menu3 click
                                i = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setPackage("com.android.chrome");
                                try {
                                    mContext.startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    toast.makeText(mContext, "unable to open chrome", Toast.LENGTH_SHORT).show();
                                    i.setPackage(null);
                                    mContext.startActivity(i);
                                }
                                return true;
                            case R.id.detials_menu_watchlist:
//                                editor.clear();
//                                editor.commit();
                                Boolean flag = true;
                                String key = "watchlist";
                                String value = id.get(position) + "," + media_type + "," + mImageUrls.get(position) + "," + title.get(position);
                                String main_arr_str = pref.getString(key,null);
                                System.out.println("Before: " + main_arr_str);
                                if(main_arr_str != null){
                                    String[] arr = main_arr_str.split("#");
                                    for(int j = 0 ; j < arr.length ; j++) {
                                        String[] temp = arr[j].split(",");
                                        if (temp[0].equals(id.get(position)) && temp[1].equals(media_type)) {
                                            //removing the list item
                                            arr = ArrayUtils.remove(arr, j);
                                            main_arr_str = String.join("#", arr);
                                            if(main_arr_str.length() != 0)
                                                main_arr_str += "#";
                                            System.out.println("Delimited"+ main_arr_str);
                                            flag = false;
                                        }
                                    }
                                    if(flag){
                                        main_arr_str = main_arr_str + value + "#";
                                    }
                                }
                                if(main_arr_str == null){
                                    main_arr_str = value + "#";
                                }
                                System.out.println("After: " + main_arr_str);
                                editor.putString(key, main_arr_str);
                                editor.commit();
                                Toast.makeText(mContext,title.get(position) + " was added to the Watchlist",Toast.LENGTH_LONG).show();
//                                Toast.makeText(mContext,"Added",Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RelativeLayout parentLayout;
        CardView cardView;
        ImageView image;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            imageButton = itemView.findViewById(R.id.film);
            cardView = itemView.findViewById(R.id.card_layout);

        }

        @Override
        public void onClick(View v) {
        }

    }

    private void renameMenu(PopupMenu popupMenu, SharedPreferences pref,String id, String media_type){

        Boolean flag = true;
        String key = "watchlist";
        //WatchList: "id,media,url$id2,media2,url2$"
        String main_arr_str = pref.getString(key,null);
        if(main_arr_str != null){
            String[] arr = main_arr_str.split("#");
            for(int j = 0 ; j < arr.length ; j++) {
                String[] temp = arr[j].split(",");
                if (temp[0].equals(id) && temp[1].equals(media_type)) {
                    //removing the list item
                    popupMenu.getMenu().getItem(3).setTitle("Remove from WatchList");
                    flag = false;
                }
            }
            if(flag){
                popupMenu.getMenu().getItem(3).setTitle("Add to WatchList");
            }
        }
        if(main_arr_str == null){
            popupMenu.getMenu().getItem(3).setTitle("Add to WatchList");
        }
    }
}
