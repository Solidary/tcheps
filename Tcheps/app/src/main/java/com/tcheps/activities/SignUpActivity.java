package com.tcheps.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public final static String ARG_TYPE_USER =
            "com.tcheps.activities.SignUpActivity.arg_type_user";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.sign_up_birth_date_btn)
    Button birthDate;

    @Bind(R.id.sign_up_student_group_ll)
    LinearLayout studentGroup;

    @Bind(R.id.sign_up_teacher_group_ll)
    LinearLayout teacherGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setupToolbar();

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(ARG_TYPE_USER).equals("student")) {
            // UserProfileFollowersFragment studentFragment = UserProfileFollowersFragment.newInstance("", "");

            // teacherGroup.setVisibility(View.INVISIBLE);teacherGroup.removeAllViews();
            teacherGroup.setVisibility(View.GONE);
            studentGroup.setVisibility(View.VISIBLE);
        } else if (bundle.getString(ARG_TYPE_USER).equals("teacher")) {
            // UserProfilePostsFragment teacherFragment = UserProfilePostsFragment.newInstance("", "");

            studentGroup.setVisibility(View.GONE);
            teacherGroup.setVisibility(View.VISIBLE);
        }
    }

    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.sign_up_activity_title));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    @OnClick(R.id.sign_up_birth_date_btn)
    public void onBirthDateClick() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Birth date");
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        String date = year + " - " + (monthOfYear + 1) + " - " + dayOfMonth;
        birthDate.setText(date);
    }
}
