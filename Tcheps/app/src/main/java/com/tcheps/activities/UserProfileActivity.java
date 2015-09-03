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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tcheps.activities.R;
import com.tcheps.adapters.TsPageAdapter;
import com.tcheps.fragments.UserProfileCirclesFragment;
import com.tcheps.fragments.UserProfileFollowersFragment;
import com.tcheps.fragments.UserProfileFollowingFragment;
import com.tcheps.fragments.UserProfilePostsFragment;
import com.tcheps.models.User;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(ARG_USER_OBJECTID) != null) {
            String tag = bundle.getString(ARG_USER_OBJECTID);

            for (int i = 0; i < User.USERS.size(); i++) {
                if (User.USERS.get(i).getObjectId().equals(tag)) {
                    user = User.USERS.get(i);

                    break;
                }
            }
        }
        if (user.getDisplayName().equals(User.USERS.get(0).getDisplayName())) {
            upFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_white_18dp));
        } else {
            upFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate_white_18dp));
        }


        setupToolbar();
        setupCollapsingToolbar();
        setupViewPagerAndTabs();
    }

    private void setupToolbar() {
        toolbar.setTitle(User.USERS.get(0).getDisplayName());
        toolbar.setSubtitle(User.USERS.get(0).getDescription());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupCollapsingToolbar() {
        upLastName.setText(user.getLastName());
        /*upLastName.setImageDrawable(
                TextDrawable.builder()
                        .beginConfig()
                            .useFont(Typeface.createFromAsset(getAssets(), "fonts/Royal2.ttf"))
                            .bold()
                            .toUpperCase()
                        .endConfig()
                        .buildRect(user.getLastName(), R.color.color_toolbar)
        );*/
        upAvatar.setImageDrawable(
                TextDrawable.builder()
                        .buildRound(user.getInitials(), ColorGenerator.MATERIAL.getRandomColor())
        );
        upDisplayName.setText(user.getDisplayName());
        upDescription.setText(user.getDescription());
    }

    private void setupViewPagerAndTabs() {
        TsPageAdapter adapter = new TsPageAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new UserProfilePostsFragment(), "Posts");
        adapter.addFragment(new UserProfileFollowersFragment(), "Followers");
        adapter.addFragment(new UserProfileFollowingFragment(), "Following");
        adapter.addFragment(new UserProfileCirclesFragment(), "Circles");
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
