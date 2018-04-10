package ahn.spring2018;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data");
    private ArrayList<String> urls, captions, user, timestamps;
    private FeedAdapter adapter;

    private ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            refresh();
            for (DataSnapshot timeData : dataSnapshot.getChildren()) {
                timestamps.add(timeData.getKey());
                for (DataSnapshot userData : timeData.getChildren()) {
                    String value = userData.getValue(String.class);
                    switch (userData.getKey()) {
                        case "url":
                            urls.add(value);
                            break;
                        case "caption":
                            captions.add(value);
                            break;
                        default:
                            user.add(value);
                    }
                }
            }

            Collections.reverse(urls);
            Collections.reverse(user);
            Collections.reverse(timestamps);
            Collections.reverse(captions);
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        urls = new ArrayList<>();
        timestamps = new ArrayList<>();
        user = new ArrayList<>();
        captions = new ArrayList<>();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.feed_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FeedAdapter(this, captions, urls, user, timestamps);
        recyclerView.setAdapter(adapter);
    }

    private void refresh() {
        urls.clear();
        timestamps.clear();
        user.clear();
        captions.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseReference.addValueEventListener(eventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(eventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.compose_button)
            startActivity(new Intent(this, ComposeActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public class FeedHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView usernameTextview;
        TextView captionTextview;
        TextView timeTextview;

        public FeedHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_preview);
            usernameTextview = itemView.findViewById(R.id.user_name);
            captionTextview = itemView.findViewById(R.id.caption_textview);
            timeTextview = itemView.findViewById(R.id.time_stamp);
        }
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedHolder> {

        private List<String> urls, captions, users, timestamps;
        private Context context;

        public FeedAdapter(Context context, List<String> captions, List<String> urls,
                           List<String> users, List<String> timestamps) {
            this.context = context;
            this.captions = captions;
            this.timestamps = timestamps;
            this.urls = urls;
            this.users = users;
        }

        @Override
        public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_card_item, null);
            return new FeedHolder(rootView);
        }

        @Override
        public void onBindViewHolder(FeedHolder holder, final int position) {
            holder.timeTextview.setText(timestamps.get(position));
            holder.usernameTextview.setText(users.get(position));
            holder.captionTextview.setText(captions.get(position));
            Picasso.with(context).load(urls.get(position)).into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FeedActivity.this, FullscreenActivity.class);
                    intent.putExtra("url", urls.get(position));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }
}
