package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView textView;
    private TextView textViewShows;
    private boolean check;
    private ConstraintLayout loadingLayoutSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);}
    }

    private void setContentView(int fragment_home) {
    }
    private void setSupportActionBar(Toolbar toolbar) {
    }


    private void carousel_display(ArrayList carousel){
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.slider);
        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getActivity(), carousel);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);
        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);
        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();
//        sliderView.setBackground(sliderView.getBackground());
    }

    private void carousel_display_shows(ArrayList carousel_shows){
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.slider);
        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getActivity(), carousel_shows);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);
        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);
        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();
//        sliderView.setBackground(sliderView.getBackground());
    }

    private void initRecyclerView(ArrayList mImageUrls, ArrayList id, String media_type, ArrayList title) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdaptor adaptor = new RecyclerViewAdaptor(getContext(), mImageUrls, id, media_type, title);
        recyclerView.setAdapter(adaptor);
    }

    private void initRecyclerViewPopular(ArrayList mImageUrls, ArrayList id, String media_type, ArrayList title) {
        LinearLayoutManager layoutManagerPopular = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
        RecyclerViewAdaptor adaptorP = new RecyclerViewAdaptor(getContext(), mImageUrls, id, media_type, title);
        recyclerViewPopular.setAdapter(adaptorP);

    }

    private void initRecyclerViewPopularShows(ArrayList mImageUrls, ArrayList id, String media_type, ArrayList title) {
        LinearLayoutManager layoutManagerPopular = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
        RecyclerViewAdaptor adaptorP = new RecyclerViewAdaptor(getContext(), mImageUrls, id, media_type, title);
        recyclerViewPopular.setAdapter(adaptorP);

    }

    private void initRecyclerViewTopRatedShows(ArrayList mImageUrls, ArrayList id, String media_type, ArrayList title) {
        LinearLayoutManager layoutManagerPopular = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewPopular = view.findViewById(R.id.recyclerView);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
        RecyclerViewAdaptor adaptorP = new RecyclerViewAdaptor(getContext(), mImageUrls, id, media_type, title);
        recyclerViewPopular.setAdapter(adaptorP);

    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        textView  = view.findViewById(R.id.textView3);
        textViewShows = view.findViewById(R.id.textView8);
        textViewShows.setTextColor(getResources().getColor(R.color.colorPrimary));
        // assigning ID of the toolbar to a variable
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        loadingLayoutSpinner = (ConstraintLayout) view.findViewById(R.id.loadingLayout);
        loadingLayoutSpinner.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String url = "http://192.168.1.18:8080/posts";
        String url = "https://pacific-legend-310201.wl.r.appspot.com/posts";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<SliderData> carousel = new ArrayList<>();
                        ArrayList<SliderData> carousel_shows = new ArrayList<>();
                        ArrayList<String> popular = new ArrayList<>();
                        ArrayList<String> toprated = new ArrayList<>();
                        ArrayList<String> popularshows = new ArrayList<>();
                        ArrayList<String> topratedshows = new ArrayList<>();

                        ArrayList<SliderData> carousel_id = new ArrayList<>();
                        ArrayList<SliderData> carousel_shows_id = new ArrayList<>();
                        ArrayList<String> popular_id = new ArrayList<>();
                        ArrayList<String> toprated_id = new ArrayList<>();
                        ArrayList<String> popularshows_id = new ArrayList<>();
                        ArrayList<String> topratedshows_id = new ArrayList<>();
                        ArrayList<String> popular_title = new ArrayList<>();
                        ArrayList<String> toprated_title = new ArrayList<>();
                        ArrayList<String> popularshows_title = new ArrayList<>();
                        ArrayList<String> topratedshows_title = new ArrayList<>();

                        try {
                            JSONArray jsonArray = response.getJSONArray("carousel");
                            JSONArray jsonArrayShows = response.getJSONArray("trending_shows");
                            JSONArray jsonTopRated = response.getJSONArray("top_rated_movies");
                            JSONArray jsonPopular = response.getJSONArray("popular_movies");
                            JSONArray jsonTopRatedShows = response.getJSONArray("top_rated_shows");
                            JSONArray jsonPopularShows = response.getJSONArray("popular_shows");

                            for (int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject movie = jsonArray.getJSONObject(i);
                                String url = movie.getString("backdrop_path");
                                carousel.add(new SliderData(url));
                            }
                            carousel_display(carousel);

                            for (int i = 0 ; i < 5 ; i++){
                                JSONObject movie = jsonArrayShows.getJSONObject(i);
                                String url = movie.getString("poster_path");
                                carousel_shows.add(new SliderData(url));
                            }

                            for (int i = 0 ; i < 10 ; i++){
                                JSONObject movie = jsonTopRated.getJSONObject(i);
                                String url = movie.getString("poster_path");
                                String id = movie.getString("id");
                                String title = movie.getString("title");
                                toprated.add(url);
                                toprated_id.add(id);
                                toprated_title.add(title);
                            }
                            initRecyclerView(toprated, toprated_id, "movie", toprated_title);

                            for (int i = 0 ; i < 10 ; i++){
                                JSONObject movie = jsonPopular.getJSONObject(i);
                                String url = movie.getString("poster_path");
                                String id = movie.getString("id");
                                String title = movie.getString("title");
                                popular.add(url);
                                popular_id.add(id);
                                popular_title.add(title);
                            }
                            initRecyclerViewPopular(popular, popular_id, "movie", popular_title);

                            for (int i = 0 ; i < 10 ; i++){
                                JSONObject movie = jsonTopRatedShows.getJSONObject(i);
                                String url = movie.getString("poster_path");
                                String id = movie.getString("id");
                                String title = movie.getString("title");
                                topratedshows.add(url);
                                topratedshows_id.add(id);
                                topratedshows_title.add(title);
                            }

                            for (int i = 0 ; i < 10 ; i++){
                                JSONObject movie = jsonPopularShows.getJSONObject(i);
                                String url = movie.getString("poster_path");
                                String id = movie.getString("id");
                                String title = movie.getString("title");
                                popularshows.add(url);
                                popularshows_id.add(id);
                                popularshows_title.add(title);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                textView.setTextColor(getResources().getColor(R.color.white));
                                textViewShows.setTextColor(getResources().getColor(R.color.colorPrimary));
                                carousel_display(carousel);
                                initRecyclerView(toprated, toprated_id, "movie", toprated_title);
                                initRecyclerViewPopular(popular, popular_id, "movie", popular_title);
                            }
                        });

                        textViewShows.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                textViewShows.setTextColor(getResources().getColor(R.color.white));
                                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                                carousel_display_shows(carousel_shows);
                                initRecyclerViewPopularShows(popularshows, popularshows_id, "tv", popularshows_title);
                                initRecyclerViewTopRatedShows(topratedshows, topratedshows_id, "tv", topratedshows_title);
                            }
                        });
//                        if (check == true) {
//                            textView.setTextColor(Color.RED);
//                            textViewShows.setTextColor(Color.CYAN);
//                        }
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

        TextView poweredBy = view.findViewById(R.id.tmdb);
        poweredBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.themoviedb.org/";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    getContext().startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "unable to open chrome", Toast.LENGTH_SHORT).show();
                    i.setPackage(null);
                    getContext().startActivity(i);
                }
            }
        });
        return view;
    }

}