package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.graphics.Color.*;
import static android.graphics.Color.WHITE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private CardView search_cards;
    private ImageView search_image;
    private TextView search_year;
    private ImageView search_star;
    private TextView search_rating;
    private TextView search_poster_title;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void initRecyclerViewSearch(ArrayList images, ArrayList<String> id, ArrayList media_type, ArrayList sTitle, ArrayList sRating, ArrayList sYear) {
        LinearLayoutManager layoutManagerSearch = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerViewSearch = view.findViewById(R.id.searchRecycle);
        recyclerViewSearch.setLayoutManager(layoutManagerSearch);
        SearchAdapter adaptorS = new SearchAdapter(getContext(), images, id, media_type, sTitle, sRating, sYear);
        recyclerViewSearch.setAdapter(adaptorS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_search, container, false);
        search_cards  = view.findViewById(R.id.search_cards);
        search_image = view.findViewById(R.id.search_image);
        search_year = view.findViewById(R.id.search_year);
        search_poster_title = view.findViewById(R.id.search_poster_title);
        search_rating = view.findViewById(R.id.search_rating);

        SearchView sv = view.findViewById(R.id.search_view);
        sv.setQueryHint("Search movies and tv");
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println(newText);
//                String url =  "http://192.168.1.18:8080/search?query=" + newText;
                String url =  "https://pacific-legend-310201.wl.r.appspot.com/search?query=" + newText;
                RequestQueue queue = Volley.newRequestQueue(getActivity());

                ArrayList<String> search_id = new ArrayList<>();
                ArrayList<String> search_title = new ArrayList<>();
                ArrayList<String> search_media_type = new ArrayList<>();
                ArrayList<String> search_rating = new ArrayList<>();
                ArrayList<String> search_year = new ArrayList<>();
                ArrayList<String> search_poster_path = new ArrayList<>();

                JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // Display the first 500 characters of the response string.
                                try {
                                    System.out.println(response);
                                    for(int i = 0 ; i < response.length() ; i++){
                                        JSONObject detail = response.getJSONObject(i);
                                        String title = detail.getString("title").toUpperCase();
                                        String media_type = detail.getString("media_type");
                                        String rating = detail.getString("rating");
                                        String poster_path = detail.getString("poster_path");
                                        String id = detail.getString("id");
                                        String year = detail.getString("year");
//                                        searchDataList.add(new SearchData(id,title,media_type,background,rating,year));
                                        search_id.add(id);
                                        search_media_type.add(media_type);
                                        search_poster_path.add(poster_path);
                                        search_rating.add(rating);
                                        search_title.add(title);
                                        search_year.add(year);
                                        System.out.println(search_poster_path);
                                    }
                                    initRecyclerViewSearch(search_poster_path, search_id, search_media_type, search_title, search_rating, search_year);
//
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(jsonRequest);

                return false;

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}