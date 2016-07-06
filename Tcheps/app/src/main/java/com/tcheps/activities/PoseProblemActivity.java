package com.tcheps.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.tcheps.AuthPreferences;
import com.tcheps.models.Problem;
import com.tcheps.models.User;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.api.ProblemsAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PoseProblemActivity extends AppCompatActivity {

    /*@Bind(R.id.pose_problem_root)
    CoordinatorLayout ppCoordinatorLayout;*/
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.pose_problem_subject_sp)
    Spinner ppSubject;
    @Bind(R.id.pose_problem_description)
    EditText ppDescription;
    /*@Bind(R.id.pose_problem_tags)
    RecipientEditTextView ppTags;
    @Bind(R.id.pose_problem_circles)
    RecipientEditTextView ppCircles;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pose_problem);

        ButterKnife.bind(this);

        setupToolbar();
        ppSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*ppTags.submitItem(ppSubject.getSelectedItem().toString(), " ");
                DrawableRecipientChip[] tagsChips = ppTags.getSortedRecipients();
                ppTags.removeMoreChip();*/
                /*Snackbar
                        .make(ppCoordinatorLayout,
                        tagsChips[0].getOriginalText(), Snackbar.LENGTH_LONG)
                        .show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setupRecipientEdit();
    }

    private void setupToolbar() {
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void setupRecipientEdit() {
        /*ppTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        ppTags.setAdapter(new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, this));

        ppCircles.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        ppCircles.setAdapter(new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, this));
        ppCircles.submitItem("Public", "89 004 67");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pose_problem, menu);
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
        if (id == R.id.action_cancel) {
            finish();

            return true;
        }
        if (id == R.id.action_submit) {
            Problem pb = new Problem();
            pb.setDescription(ppDescription.getText().toString());
            pb.setSubject(ppSubject.getSelectedItem().toString());

            /*AuthPreferences authPreferences = new AuthPreferences(this);
            String authToken = authPreferences.getToken();
            User user = authPreferences.getUser();*/

            /*ProblemsAPI ps = TsServiceGenerator.create(ProblemsAPI.class, authToken);
            ps.create(pb, new Callback<Problem>() {
                @Override
                public void success(Problem problem, Response response) {
                    Log.d("Tchep's", "On Success >>> " + problem.toString());
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Tchep's", "On Failure >>> " + error.getMessage());
                    // Use a snackbar to display the error.
                }
            });*/
        }

        return super.onOptionsItemSelected(item);
    }


}
