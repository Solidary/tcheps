package com.tcheps.activities;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tcheps.activities.R;
import com.tcheps.adapters.TsPageAdapter;
import com.tcheps.fragments.UserProfileCirclesFragment;
import com.tcheps.fragments.UserProfileFollowersFragment;
import com.tcheps.fragments.UserProfileFollowingFragment;
import com.tcheps.fragments.UserProfilePostsFragment;
import com.tcheps.fragments.UsersFragment;
import com.tcheps.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsersFromDescriptionActivity extends AppCompatActivity {

    public final static String ARG_TAG =
            "com.tcheps.activities.UsersFromDescriptionActivity.arg_from_description";
    public final static String ARG_FROM_TYPE_OF_USER =
            "com.tcheps.activities.UsersFromDescriptionActivity.arg_type_of_user";

    @Bind(R.id.user_from_description_coordinator_layout)
    CoordinatorLayout ufdCordinatorLayout;
    @Bind(R.id.user_from_description_app_bar_layout)
    AppBarLayout ufdAppBarLayout;
    @Bind(R.id.user_from_description_view_pager)
    ViewPager ufdViewPager;
    @Bind(R.id.user_from_description_tab_layout)
    TabLayout ufdTabLayout;
    // @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_user_from_description);
        ButterKnife.bind(this);

        //setupToolbar();
        setupViewPagerAndTabs();
    }

    private void setupToolbar() {
        toolbar.setTitle("User from ...");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupViewPagerAndTabs() {
        TsPageAdapter adapter = new TsPageAdapter(getSupportFragmentManager(), this);

        Bundle bundle = getIntent().getExtras();
        String description = bundle.getString(ARG_TAG);

        String[] splitDescription = description.split(",");
        String[] titles = new String[splitDescription.length - 1];
        String type = splitDescription[splitDescription.length - 1].trim();

        for(int i = 0; i < (splitDescription.length - 1); i++) {
            if (type.equals("student")) {
                if (i == 0) titles[i] = splitDescription[i].trim();
                else if (i == 1) titles[i] = splitDescription[i].trim();
            } else if (type.equals("teacher")) {
                if (i == 0) titles[i] = "Subject : " + splitDescription[i].trim();
                else if (i == 1) titles[i] = "Place : " + splitDescription[i].trim();
            }

            adapter.addFragment(new UsersFragment(User.USERS), titles[i]);
        }
        ufdViewPager.setAdapter(adapter);

        ufdTabLayout.setupWithViewPager(ufdViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_fetch_user_from_description, menu);
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
