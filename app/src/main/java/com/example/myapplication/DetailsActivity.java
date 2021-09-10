package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class DetailsActivity extends AppCompatActivity {
    TextView details_page_title;
    TextView details_overview;
    TextView details_genres;
    TextView details_year;
    TextView emptyView;
    private ConstraintLayout loadingLayoutSpinner;
    private ArrayList<String> rReviewsTitle = new ArrayList<>();
    private ArrayList<String> rReviewsRating = new ArrayList<>();
    private ArrayList<String> rReviewsContent = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}
        getIncomingIntent();

    }
    private void initRecyclerViewCast(ArrayList personImages, ArrayList personNames) {
        // get the reference of RecyclerView
        RecyclerView recyclerViewDetails = (RecyclerView) this.findViewById(R.id.recyclerViewDetailsPage);
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailsActivity.this,3);
        recyclerViewDetails.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(DetailsActivity.this, personImages, personNames);
        recyclerViewDetails.setAdapter(customAdapter); // set the Adapter to RecyclerView

    }
    private void initRecyclerViewRecommended(ArrayList mImageUrls, ArrayList id, String media_type, ArrayList title) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRecommended);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdaptor adaptor = new RecyclerViewAdaptor(DetailsActivity.this, mImageUrls, id, media_type, title);
        recyclerView.setAdapter(adaptor);
    }
    private void initRecyclerViewReviews(ArrayList rReviewsTitle, ArrayList rReviewsRating, ArrayList rReviewsContent) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReviews);
        recyclerView.setLayoutManager(layoutManager);
        ReviewsAdapter adaptor = new ReviewsAdapter(DetailsActivity.this, rReviewsTitle, rReviewsRating, rReviewsContent);
        recyclerView.setAdapter(adaptor);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkWatchList(SharedPreferences pref, String id, String media_type, String profile_path, String title){
        SharedPreferences.Editor editor = pref.edit();
        ImageView watchlistBtn = findViewById(R.id.d_circle);
        String key = "watchlist";

        Boolean flag = true;
        String main_arr_str = pref.getString(key,null);
        System.out.println("In Detail: " + main_arr_str);
        if(main_arr_str != null && main_arr_str.length() != 0){
            String[] arr = main_arr_str.split("#");
            for(int j = 0 ; j < arr.length ; j++) {
                String[] temp = arr[j].split(",");
                if (temp[0].equals(id) && temp[1].equals(media_type)) {
                    //removing the list item
                    watchlistBtn.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);;
                    flag = false;
                }
            }
            if(flag){
                watchlistBtn.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
            }
        }

        watchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flag = true;
                String key = "watchlist";
                String value = id + "," + media_type+ "," + profile_path + "," + title;
                String main_arr_str = pref.getString(key,null);
                System.out.println("Before: " + main_arr_str);
                if(main_arr_str != null && main_arr_str.length() != 0){
                    String[] arr = main_arr_str.split("#");
                    for(int j = 0 ; j < arr.length ; j++) {
                        String[] temp = arr[j].split(",");
                        if (temp[0].equals(id) && temp[1].equals(media_type)) {
                            //removing the list item
                            arr = ArrayUtils.remove(arr, j);
                            Toast.makeText(v.getContext(), title +" was removed from the watchlist",Toast.LENGTH_LONG).show();
                            main_arr_str = String.join("#", arr);
                            if(main_arr_str.length() != 0)
                                main_arr_str += "#";
                            flag = false;
                            watchlistBtn.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
                        }
                    }
                    if(flag){
                        main_arr_str = main_arr_str + value + "#";
                        Toast.makeText(v.getContext(), title +" was added to the watchlist",Toast.LENGTH_LONG).show();
                        watchlistBtn.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                    }
                }
                else{
                    main_arr_str = value + "#";
                    Toast.makeText(v.getContext(), title +" was added to the watchlist",Toast.LENGTH_LONG).show();
                    watchlistBtn.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                }
                editor.putString(key,main_arr_str);
                editor.commit();
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("id")) {

            String imageUrl = getIntent().getStringExtra("image_url");
            String id = getIntent().getStringExtra("id");
            String media_type = getIntent().getStringExtra("media_type");
            System.out.println(id);
            System.out.println(media_type);
            ImageButton d_fb =  findViewById(R.id.d_fb);
            ImageButton d_twitter = findViewById(R.id.d_twitter);



            d_fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://www.themoviedb.org/" + media_type + "/" + id;
                    String fbURL = "https://www.facebook.com/sharer/sharer.php?u=" + url ;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(fbURL));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.android.chrome");
                    try {
                        getApplicationContext().startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "unable to open chrome", Toast.LENGTH_SHORT).show();
                        i.setPackage(null);
                        getApplicationContext().startActivity(i);
                    }
                }
            });


            d_twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://www.themoviedb.org/" + media_type + "/" + id;
                    String tweetUrl = "https://twitter.com/intent/tweet?text=Check this out! " + url;
                    //handle menu3 click
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.android.chrome");
                    try {
                        getApplicationContext().startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "unable to open chrome", Toast.LENGTH_SHORT).show();
                        i.setPackage(null);
                        getApplicationContext().startActivity(i);
                    }
                }
            });
            loadingLayoutSpinner = (ConstraintLayout) findViewById(R.id.loadingDetail);
            loadingLayoutSpinner.setVisibility(View.VISIBLE);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.1.18:8080/watch?id=" +id + "&media_type=" +media_type;
//            String url = "https://pacific-legend-310201.wl.r.appspot.com/watch?id=" +id + "&media_type=" + media_type;
            System.out.println(media_type);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = LOLLIPOP)
                        @Override
                        public void onResponse(JSONObject response) {
                            ArrayList<String> video = new ArrayList<>();
                            ArrayList<String> title = new ArrayList<>();
                            ArrayList<String> overview = new ArrayList<>();
                            ArrayList<String> genres = new ArrayList<>();
                            ArrayList<String> year = new ArrayList<>();
                            ArrayList<String> cast = new ArrayList<>();
                            ArrayList<String> cast_names = new ArrayList<>();
                            ArrayList<String> reviews_author = new ArrayList<>();
                            ArrayList<String> reviews_rating = new ArrayList<>();
                            ArrayList<String> reviews_content = new ArrayList<>();
                            ArrayList<String> recommended_picks = new ArrayList<>();
                            ArrayList<String> recommended_picks_id = new ArrayList<>();
                            ArrayList<String> recommended_media_type = new ArrayList<>();
                            ArrayList<String> recommended_title = new ArrayList<>();
                            ArrayList<String> poster_path = new ArrayList<>();


//
                            try {
                                JSONArray jsonVideo = response.getJSONArray("v");
                                JSONArray jsonDetails = response.getJSONArray("d");
                                JSONArray jsonCast = response.getJSONArray("c");
                                JSONArray jsonReviews = response.getJSONArray("r");
                                JSONArray jsonRecommended = response.getJSONArray("rd");


                                for (int i = 0 ; i < jsonVideo.length() ; i++){
                                    JSONObject movie = jsonVideo.getJSONObject(i);
                                    String urlVideo = movie.getString("key");
                                    video.add(urlVideo);
                                }setYoutube(video.get(0));

                                for (int i = 0 ; i < jsonDetails.length() ; i++){
                                    JSONObject movie = jsonDetails.getJSONObject(i);
                                    String info_title = movie.getString("title");
                                    String poster = "https://image.tmdb.org/t/p/w500"+movie.getString("poster_path");
                                    String info_overview = movie.getString("overview");
                                    String info_genres = movie.getString("gen");
                                    String info_year = movie.getString("year");
                                    if (info_genres.isEmpty()) {
                                        genres.add("Nil");
                                    }
                                    else {genres.add(info_genres);}
                                    if (info_year.isEmpty()) {
                                        year.add("Nil");
                                    }
                                    else {year.add(info_year);}
                                    title.add(info_title);
                                    overview.add(info_overview);
                                    year.add(info_year);
                                    poster_path.add(poster);
                                }

                                for (int i = 0 ; i < 6 ; i++){
                                    JSONObject movie = jsonCast.getJSONObject(i);
                                    String info_cast = movie.getString("profile_path");
                                    String info_cast_names = movie.getString("name");
                                    cast.add(info_cast);
                                    cast_names.add(info_cast_names);
                                }
                                initRecyclerViewCast(cast, cast_names);


                                for (int i = 0 ; i < jsonReviews.length() ; i++){
                                    JSONObject movie = jsonReviews.getJSONObject(i);
                                    String info_reviews_author = movie.getString("author");
                                    String info_reviews_rating = movie.getString("rating");
                                    String info_reviews_content = movie.getString("content");
                                    String info_reviews_day = movie.getString("created_at_day");
                                    String info_reviews_year = movie.getString("created_at_year");
                                    String info_reviews_month = movie.getString("created_at_month");
                                    String info_reviews_date = movie.getString("created_at_date");
//
                                    reviews_author.add("by "+ info_reviews_author + " on " + info_reviews_day + ", " + info_reviews_month + " " + info_reviews_date + " " + info_reviews_year);
                                    reviews_rating.add(info_reviews_rating + "/" + "5");
                                    reviews_content.add(info_reviews_content);
                                }
                                initRecyclerViewReviews(reviews_author, reviews_rating, reviews_content);


                                for (int i = 0 ; i < jsonRecommended.length() ; i++){
                                    System.out.println("Inside Recc");
                                    JSONObject movie = jsonRecommended.getJSONObject(i);
                                    String info_rd = movie.getString("poster_path");
                                    String info_rd_id = movie.getString("id");
                                    String info_rd_title = movie.getString("title");
//                                    String info_media_type = movie.getString("media_type");

                                        recommended_picks.add(info_rd);
                                        recommended_picks_id.add(info_rd_id);
                                        recommended_media_type.add(media_type);
                                        recommended_title.add(info_rd_title);
                                }
//                                }
//                        }
                                initRecyclerViewRecommended(recommended_picks, recommended_picks_id, recommended_media_type.get(0), recommended_title);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//                            checkWatchList(pref,id,media_type,poster_path.get(0),title.get(0));
                            setImage(title.get(0), overview.get(0), genres.get(0), year.get(0));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });
            jsonRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            loadingLayoutSpinner.setVisibility(View.GONE);
            queue.add(jsonRequest);
        }
    }

    private void setImage(String name, String ovr, String gen, String yr) {
//        ImageView image = findViewById(R.id.details_page_image);
        String imageUrl = getIntent().getStringExtra("image_url");
        details_page_title = findViewById(R.id.details_page_title);
        details_page_title.setText(name);
        details_overview = findViewById(R.id.details_overview);
        details_overview.setText(ovr);
        details_genres = findViewById(R.id.details_genres);
        details_genres.setText(gen);
        details_year = findViewById(R.id.details_year);
        details_year.setText(yr);

//        Glide.with(this)
//                .asBitmap()
//                .load(imageUrl)
//                .into(image);
    }
    @RequiresApi(api = LOLLIPOP)
    private void setYoutube(String trailer){
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youTubePlayerView);
//        ImageView backgroundImg = findViewById(R.id.details_page_image);

//        backgroundImg.setVisibility(View.GONE);
        youTubePlayerView.setVisibility(View.VISIBLE);
//        backgroundImg.setTranslationZ(Float.parseFloat("0dp"));
//        youTubePlayerView.setTranslationZ(Float.parseFloat("1dp"));
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = trailer;
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }



}

