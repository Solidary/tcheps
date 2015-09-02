package com.tcheps.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tcheps.activities.R;
import com.tcheps.adapters.ProblemsFeedAdapter;
import com.tcheps.models.Problem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProblemsFeedActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.problems_feed_rv)
    RecyclerView rvProblemsFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems_feed);
        ButterKnife.bind(this);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        toolbar.setTitle("Problems");
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        rvProblemsFeed.setLayoutManager(new LinearLayoutManager(this));
        ProblemsFeedAdapter adapter = new ProblemsFeedAdapter(this, Problem.PROBLEMS);
        rvProblemsFeed.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_problems_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
