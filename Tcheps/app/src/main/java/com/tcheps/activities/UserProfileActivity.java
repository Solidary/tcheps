package com.tcheps.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tcheps.activities.R;
import com.tcheps.adapters.TsPageAdapter;
import com.tcheps.data.Constant;
import com.tcheps.fragments.UserProfileCirclesFragment;
import com.tcheps.fragments.UserProfileFollowersFragment;
import com.tcheps.fragments.UserProfileFollowingFragment;
import com.tcheps.fragments.UserProfilePostsFragment;
import com.tcheps.fragments.UsersFragment;
import com.tcheps.models.Comment;
import com.tcheps.models.User;

// import org.parceler.apache.commons.lang.WordUtils;
import org.parceler.apache.commons.lang.WordUtils;
import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserProfileActivity extends AppCompatActivity {

    public static final String ARG_USER_OBJECTID =
        "com.tcheps.activities.UserProfileActivity.arg_user_objectid";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.user_profile_last_name)
    TextView upLastName;
    @Bind(R.id.user_profile_avatar)
    ImageView upAvatar;
    @Bind(R.id.user_profile_display_name)
    TextView upDisplayName;
    @Bind(R.id.user_profile_description)
    TextView upDescription;

    @Bind(R.id.user_profile_stats_posts_tv)
    TextView upStatsPost;
    @Bind(R.id.user_profile_stats_followers_tv)
    TextView upStatsFollowers;
    @Bind(R.id.user_profile_stats_following_tv)
    TextView upStatsFollwing;
    @Bind(R.id.user_profile_stats_circle_tv)
    TextView upStatsCircles;

    @Bind(R.id.user_profile_tab_layout)
    TabLayout upTablayout;
    @Bind(R.id.user_profile_view_pager)
    ViewPager upViewPager;

    @Bind(R.id.user_profile_fab)
    FloatingActionButton upFAB;

    private User user;
    List<User> users = Constant.getUsersData(getBaseContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(ARG_USER_OBJECTID) != null) {
            String tag = bundle.getString(ARG_USER_OBJECTID);

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getObjectId().equals(tag)) {
                    user = users.get(i);

                    break;
                }
            }
        }
        if (user.getName().equals(users.get(0).getName())) {
            upFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_white_18dp));
        } else {
            upFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate_white_18dp));
        }


        setupToolbar();
        setupCollapsingToolbar();
        setupViewPagerAndTabs();
    }

    private void setupToolbar() {
        toolbar.setTitle(users.get(0).getName());
        toolbar.setSubtitle(users.get(0).getDescription());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupCollapsingToolbar() {
        /*String lastname = user.getName().substring(0, 1).toUpperCase() +
                user.getLastName().substring(1).toLowerCase();
        String firstname = user.getFirstName().substring(0, 1).toUpperCase() +
                user.getFirstName().substring(1).toLowerCase();
            Log.d("Tcheps", "setupCollapsingToolbar >>> " + lastname);*/
        // String lastname = WordUtils.capitalize(user.getLastName());
        upLastName.setText(WordUtils.capitalize(user.getName()));
        upAvatar.setImageDrawable(
                TextDrawable.builder()
                        .buildRound(user.getInitials(), ColorGenerator.MATERIAL.getRandomColor())
        );
        upDisplayName.setText(user.getName());
        upDescription.setText(user.getDescription());
    }

    private void setupViewPagerAndTabs() {
        TsPageAdapter adapter = new TsPageAdapter(getSupportFragmentManager(), this);

        adapter.addFragment(new UsersFragment(users), "Posts");
        adapter.addFragment(new UsersFragment(users), "Followers");
        adapter.addFragment(new UsersFragment(users), "Following");
        adapter.addFragment(new UsersFragment(users), "Circles");
        upViewPager.setAdapter(adapter);

        upTablayout.setupWithViewPager(upViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
