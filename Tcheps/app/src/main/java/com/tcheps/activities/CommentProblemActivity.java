package com.tcheps.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tcheps.activities.R;
import com.tcheps.adapters.CommentProblemAdapter;
import com.tcheps.adapters.ProblemsFeedAdapter;
import com.tcheps.models.Problem;
import com.tcheps.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentProblemActivity extends AppCompatActivity implements
        CommentProblemAdapter.OnCommentProblemClickListener {

    public final static String ARG_PROBLEM_OBJECTID
            = "com.tcheps.activities.CommentProblemActivity.arg_problem_id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.comment_problem_comments)
    RecyclerView rvComments;

    @Bind(R.id.comment_problem_author_avatar)
    ImageView cpAuthorAvatar;
    @Bind(R.id.comment_problem_author_display_name)
    TextView cpAuthorDisplayName;
    @Bind(R.id.comment_problem_author_description)
    TextView cpAuthorDescription;
    @Bind(R.id.comment_problem_description)
    TextView cpDescription;

    Problem problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_problem);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(ARG_PROBLEM_OBJECTID) != null) {
            String tag = bundle.getString(ARG_PROBLEM_OBJECTID);

            for (int i = 0; i < Problem.PROBLEMS.size(); i++) {
                if (Problem.PROBLEMS.get(i).getObjectId().equals(tag)) {
                    problem = Problem.PROBLEMS.get(i);

                    break;
                }
            }
        }

        setupHeader();
        setupToolbar();
        setupRecyclerView();
    }

    private void setupHeader() {
        cpAuthorAvatar.setImageDrawable(
                TextDrawable.builder()
                        .buildRound(problem.getAuthor().getInitials(), ColorGenerator.MATERIAL.getRandomColor())
        );
        cpAuthorDisplayName.setText(problem.getAuthor().getDisplayName());
        cpAuthorDescription.setText(problem.getAuthor().getDescription());
        cpDescription.setText(getString(R.string.lorem_ipsum));
    }

    private void setupToolbar() {
        toolbar.setTitle("Comments ...");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        CommentProblemAdapter adapter = new CommentProblemAdapter(this, problem.getComments());
        adapter.setOnCommentProblemClickListener(this);
        rvComments.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_comment_problem, menu);
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
    public void onLikeClick(View v, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(CommentProblemActivity.ARG_PROBLEM_OBJECTID, tag);

        Intent commentProblemIntent = new Intent(this, CommentProblemActivity.class);
        commentProblemIntent.putExtras(bundle);
        startActivity(commentProblemIntent);
    }
}
