package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {
    TextView reviewsTitle;
    TextView reviewsRating;
    TextView reviewsContent;

    public ReviewsActivity() {
    }

    public ReviewsActivity(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}
        setContentView(R.layout.activity_reviews);

        reviewsTitle = findViewById(R.id.reviews_title);
        reviewsRating = findViewById(R.id.reviews_rating);
        reviewsContent = findViewById(R.id.reviews_content);
        getIncomingIntentReviews();
    }

    private void getIncomingIntentReviews(){
        if(getIntent().hasExtra("revTitle")) {

            String revTitle = getIntent().getStringExtra("revTitle");
            String revRating = getIntent().getStringExtra("revRating");
            String revContent = getIntent().getStringExtra("revContent");

            reviewsTitle.setText(revTitle);
            reviewsRating.setText(revRating);
            reviewsContent.setText(revContent);

        }
    }
}