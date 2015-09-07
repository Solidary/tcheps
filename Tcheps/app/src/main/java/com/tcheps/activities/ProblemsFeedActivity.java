package com.tcheps.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tcheps.activities.R;
import com.tcheps.adapters.ProblemsFeedAdapter;
import com.tcheps.models.Problem;
import com.tcheps.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProblemsFeedActivity extends AppCompatActivity implements
        ProblemsFeedAdapter.OnProblemsFeedClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.problems_feed_rv)
    RecyclerView rvProblemsFeed;

    @Bind(R.id.problems_feed_pose_problem)
    FloatingActionButton pfPoseProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems_feed);
        ButterKnife.bind(this);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        toolbar.setTitle(User.USERS.get(0).getDisplayName());
        toolbar.setSubtitle(User.USERS.get(0).getDescription());
        toolbar.setLogo(TextDrawable.builder()
                .buildRound(User.USERS.get(0).getInitials(), ColorGenerator.MATERIAL.getRandomColor()));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(TextDrawable.builder()
                .buildRound(User.USERS.get(0).getInitials(), ColorGenerator.MATERIAL.getRandomColor()));
    }

    private void setupRecyclerView() {
        rvProblemsFeed.setLayoutManager(new LinearLayoutManager(this));
        ProblemsFeedAdapter adapter = new ProblemsFeedAdapter(this, Problem.PROBLEMS);
        adapter.setOnProblemsFeedClickListener(this);
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

    @OnClick(R.id.problems_feed_pose_problem)
    public void onPoseProblemClick() {
        Intent poseProblemIntent = new Intent(this, PoseProblemActivity.class);
        startActivity(poseProblemIntent);
    }

    @Override
    public void onProfileClick(View view, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(UserProfileActivity.ARG_USER_OBJECTID, tag);

        Intent profileIntent = new Intent(this, UserProfileActivity.class);
        profileIntent.putExtras(bundle);
        startActivity(profileIntent);
    }

    @Override
    public void onUsersFromDescriptionClick(View v, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(UsersFromDescriptionActivity.ARG_TAG, tag);

        Intent usersFromDescriptionIntent = new Intent(this, UsersFromDescriptionActivity.class);
        usersFromDescriptionIntent.putExtras(bundle);
        startActivity(usersFromDescriptionIntent);
    }

    @Override
    public void onCommentClick(View v, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(CommentProblemActivity.ARG_PROBLEM_OBJECTID, tag);

        Intent commentProblemIntent = new Intent(this, CommentProblemActivity.class);
        commentProblemIntent.putExtras(bundle);
        startActivity(commentProblemIntent);
    }
}
