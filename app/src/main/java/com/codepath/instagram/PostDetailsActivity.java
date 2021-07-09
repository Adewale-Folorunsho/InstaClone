package com.codepath.instagram;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

public class PostDetailsActivity extends AppCompatActivity {

    Post post;

    TextView tvUsername;
    TextView tvDescription;
    TextView tvCreatedAt;
    ImageView ivImage;


    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details_activity);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvCreatedAt = (TextView) findViewById(R.id.tvCreatedAt);
        ivImage = (ImageView) findViewById(R.id.ivImage);


        // unwrap the movie passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        Log.d("PostDetailsActivity", String.format("Showing details for '%s'", post.getUser().getUsername()));

        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        tvCreatedAt.setText(post.calculateTimeAgo());

        ParseFile imageUrl;

        //Context context;
        imageUrl = post.getImage();
        Glide.with(this)
                .load(imageUrl.getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivImage);

    }
};
