package com.tcheps.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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

    @OnClick({R.id.sign_in_sign_up_student_btn, R.id.sign_in_sign_up_teacher_btn})
    public void onSignUpClick(Button bv) {
        Bundle bundle = new Bundle();

        if (bv.getId() == R.id.sign_in_sign_up_student_btn) {
            bundle.putString(SignUpActivity.ARG_TYPE_USER, "student");
        } else if (bv.getId() == R.id.sign_in_sign_up_teacher_btn) {
            bundle.putString(SignUpActivity.ARG_TYPE_USER, "teacher");
        }

        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        signUpIntent.putExtras(bundle);
        startActivity(signUpIntent);
    }

    @OnClick({R.id.sign_in_sign_in_btn})
    public void onSignInClick() {
        Intent problemFeedIntent = new Intent(this, ProblemsFeedActivity.class);
        startActivity(problemFeedIntent);
    }
}
