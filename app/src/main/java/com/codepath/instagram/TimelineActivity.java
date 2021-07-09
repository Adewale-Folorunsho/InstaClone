package com.codepath.instagram;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    protected PostsAdapter postsAdapter;
    protected List<Post> posts;
    protected RecyclerView rvPosts;
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

        queryPosts();

}

private void queryPosts(){
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(Post.KEY_USER);
    query.setLimit(20);
    query.orderByDescending("createdAt");
    query.findInBackground(new FindCallback<Post>() {
        @Override
        public void done(List<Post> postsFromDB, ParseException e) {
            if(e != null){
                Log.e(TAG, "Error getting posts");
                return;
            }
            for(Post post:postsFromDB){
                Log.i(TAG, "Username: " + post.getUser().getUsername() + " Description: " + post.getDescription());
            }
            posts.addAll(postsFromDB);
            postsAdapter.notifyDataSetChanged();

            rvPosts.smoothScrollToPosition(0);
        }
    });
    }
}
