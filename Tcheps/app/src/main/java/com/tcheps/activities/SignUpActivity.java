package com.tcheps.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.tcheps.models.Student;
import com.tcheps.models.Teacher;
import com.tcheps.models.User;
import com.tcheps.restful.TsRetrofit;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.interfaces.UserAuthentication;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public final static String ARG_TYPE_USER =
            "com.tcheps.activities.SignUpActivity.arg_type_user";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.sign_up_first_name_et)
    EditText suFirstName;
    @Bind(R.id.sign_up_last_name_et)
    EditText suLastName;
    @Bind(R.id.sign_up_email_et)
    EditText suEmail;
    @Bind(R.id.sign_up_password_et)
    EditText suPassword;
    @Bind(R.id.sign_up_password_confirmation_et)
    EditText suPasswordConfirmation;
    @Bind(R.id.sign_up_birth_date_btn)
    Button suBirthDate;
    @Bind(R.id.sign_up_gender_rg)
    RadioGroup suGender;

    String _type;

    @Bind(R.id.sign_up_student_level_et)
    EditText suStudentLevel;
    @Bind(R.id.sign_up_student_school_et)
    EditText suStudentSchool;

    @Bind(R.id.sign_up_teacher_place_type_sp)
    Spinner suTeacherPlaceType;
    @Bind(R.id.sign_up_teacher_place_name_et)
    EditText suTeacherPlaceName;
    @Bind(R.id.sign_up_teacher_subjects_sp)
    Spinner suTeacherSubjects;

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

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        String date = year + " - " + (monthOfYear + 1) + " - " + dayOfMonth;
        suBirthDate.setText(date);
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

    @OnClick(R.id.sign_up_register_btn)
    public void onRegisterClick() {
        Bundle bundle = getIntent().getExtras();
        _type = bundle.getString(ARG_TYPE_USER);

        // User user = new User();

        UserAuthentication userAuthentication = TsServiceGenerator.create(UserAuthentication.class);

        if (_type.equals("student")) {
            Student student = new Student();
            student.setFirstName(suFirstName.getText().toString());
            student.setLastName(suLastName.getText().toString());
            student.setEmail(suEmail.getText().toString());
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                student.setBirthDate(format.parse(suBirthDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (suPassword.getText().toString().equals(suPasswordConfirmation.getText().toString())) {
                student.setPassword(suPassword.getText().toString());
            }

            student.setLevel(suStudentLevel.getText().toString());
            student.setSchool(suStudentSchool.getText().toString());

            // User createdUser = userAuthentication.signUp(student);
        } else if (bundle.getString(ARG_TYPE_USER).equals("teacher")) {
            Teacher teacher = new Teacher();

            teacher.setFirstName(suFirstName.getText().toString());
            teacher.setLastName(suLastName.getText().toString());
            teacher.setEmail(suEmail.getText().toString());
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                teacher.setBirthDate(format.parse(suBirthDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (suPassword.getText().toString().equals(suPasswordConfirmation.getText().toString())) {
                teacher.setPassword(suPassword.getText().toString());
            }

            teacher.setPlaceType(suTeacherPlaceType.getSelectedItem().toString());
            teacher.setPlaceName(suTeacherPlaceName.getText().toString());
            teacher.setSubject(suTeacherSubjects.getSelectedItem().toString());

            // User createdUser = userAuthentication.signUp(teacher);
        }


    }
}
