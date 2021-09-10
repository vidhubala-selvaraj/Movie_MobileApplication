package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    ArrayList<String> personImages;
    ArrayList<String> personNames;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> media_type = new ArrayList<>();
    Toast toast;
    Context context;
    public CustomAdapter(Context context, ArrayList personImages, ArrayList personNames) {
        this.context = context;
        this.personImages = personImages;
        this.personNames = personNames;
    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_griditem, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CustomAdapter.ViewHolder vh = new CustomAdapter.ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        Glide.with(context)
                .asBitmap()
                .load(personImages.get(position))
                .into(holder.image);

        holder.names.setText(personNames.get(position));

//        holder.d_circle.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onClick (View view) {
//                Boolean flag = true;
//                String key = "watchlist";
//                String value = id.get(position) + "," + media_type + "," + mImageUrls.get(position) + "," + title.get(position);
//                String main_arr_str = pref.getString(key,null);
//                System.out.println("Before: " + main_arr_str);
//                if(main_arr_str != null){
//                    String[] arr = main_arr_str.split("#");
//                    for(int j = 0 ; j < arr.length ; j++) {
//                        String[] temp = arr[j].split(",");
//                        if (temp[0].equals(id.get(position)) && temp[1].equals(media_type)) {
//                            //removing the list item
//                            arr = ArrayUtils.remove(arr, j);
//                            main_arr_str = String.join("#", arr);
//                            if(main_arr_str.length() != 0)
//                                main_arr_str += "#";
//                            System.out.println("Delimited"+ main_arr_str);
//                            flag = false;
//                        }
//                    }
//                    if(flag){
//                        main_arr_str = main_arr_str + value + "#";
//                    }
//                }
//                if(main_arr_str == null){
//                    main_arr_str = value + "#";
//                }
//                System.out.println("After: " + main_arr_str);
//                editor.putString(key, main_arr_str);
//                editor.commit();
//                Toast.makeText(context,title.get(position) + " was added to the Watchlist",Toast.LENGTH_LONG).show();
////                                Toast.makeText(mContext,"Added",Toast.LENGTH_LONG).show();
//
//            }
//        });
//


        }



    @Override
    public int getItemCount() {
        return personImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;
        public TextView names;
        public ImageButton d_circle;
//        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.imageofdetails);
            names = v.findViewById(R.id.castname);
            d_circle = v.findViewById(R.id.d_circle);


//            image = (ImageView) itemView.findViewById(R.id.imageofdetails);
        }
    }
}
