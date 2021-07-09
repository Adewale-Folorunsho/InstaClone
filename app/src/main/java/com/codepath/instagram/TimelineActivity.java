package com.codepath.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    protected PostsAdapter postsAdapter;
    protected List<Post> posts;
    protected RecyclerView rvPosts;
    protected SwipeRefreshLayout swipeContainer;
    private String TAG = "TimelineActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_activity);

        posts = new ArrayList<>();
        postsAdapter = new PostsAdapter(this, posts);

        rvPosts = findViewById((R.id.rvPosts));
        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer = findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();

    }

    public void fetchTimelineAsync(int page) {
        posts.clear();
        queryPosts();
        swipeContainer.setRefreshing(false);
    }

private void queryPosts(){
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(Post.KEY_USER);
    query.include(Post.KEY_TIME);
    query.setLimit(postsAdapter.getItemCount()-1);
    query.orderByDescending("createdAt");
    query.findInBackground(new FindCallback<Post>() {
        @Override
        public void done(List<Post> postsFromDB, ParseException e) {
            if(e != null){
                Log.e(TAG, "Error getting posts");
                return;
            }

            //test
            for(Post post:postsFromDB){
                Log.i(TAG, "Username: " + post.getUser().getUsername() + " Description: " + post.getDescription() + " Time: " + post.calculateTimeAgo());
            }

            posts.addAll(postsFromDB);
            postsAdapter.notifyDataSetChanged();

        }
    });
    }

    public void makePost(View view){
        Log.i(TAG, "Make post activity opened");
        Intent intent = new Intent(TimelineActivity.this, PostActivity.class);
        startActivity(intent);
    }
}
