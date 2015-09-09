package com.tcheps.activities;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.tcheps.authenticator.TsAccountGeneral;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.error.TsRetrofitError;
import com.tcheps.restful.models.SignResponse;
import com.tcheps.restful.services.UserAuthentication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignInActivity extends AccountAuthenticatorActivity {

    public final static String ARG_IS_ADDING_NEW_ACCOUNT
            =  "com.tcheps.activities.SignInActivity.arg_is_adding_new_account";
    public final static String ARG_AUTH_TYPE
            =  "com.tcheps.activities.SignInActivity.arg_is_auth_type";
    public final static String ARG_ACCOUNT_TYPE
            =  "com.tcheps.activities.SignInActivity.arg_account_type";
    public final static String ARG_ACCOUNT_NAME
            =  "com.tcheps.activities.SignInActivity.arg_account_name";
    public final static String ARG_USER_PASSWORD
            =  "com.tcheps.activities.SignInActivity.arg_user_password";
    public final static String ARG_FROM_AUTHENTICATOR
            =  "com.tcheps.activities.SignInActivity.arg_from_authenticator";

    @Bind(R.id.sign_in_email_et)
    EditText siEmail;
    @Bind(R.id.sign_in_password_et)
    EditText siPassword;

    AccountManager tsAccountManager;
    String tsAccountName,
            tsAccountType,
            tsAuthType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        tsAccountManager = AccountManager.get(getBaseContext());
        tsAccountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        if (tsAccountName != null) {
            siEmail.setText(tsAccountName);
        }
        tsAccountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        if (tsAccountType == null) {
            tsAccountType = TsAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
        tsAuthType = getIntent().getStringExtra(ARG_AUTH_TYPE);

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        final String email = siEmail.getText().toString();
        final String password = siPassword.getText().toString();
        Log.d("Tchep's", email + " -- " + password);
        final Intent res = new Intent();

        UserAuthentication userAuthentication = TsServiceGenerator.create(UserAuthentication.class);
        userAuthentication.signIn(email, password, new Callback<SignResponse>() {
            @Override
            public void success(SignResponse signResponse, Response response) {
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, tsAccountType);
                res.putExtra(AccountManager.KEY_AUTHTOKEN, signResponse.getToken());

                // We'll add one parameter for us
                res.putExtra(ARG_USER_PASSWORD, password);

                finishSignIn(res);
            }

            @Override
            public void failure(RetrofitError error) {
                TsRetrofitError body = (TsRetrofitError) error.getBodyAs(TsRetrofitError.class);
                res.putExtra(AccountManager.KEY_ERROR_CODE, body.code);
                res.putExtra(AccountManager.KEY_ERROR_MESSAGE, body.error);
            }
        });

    }

    private void finishSignIn(Intent intent) {
        String accountPassword = intent.getStringExtra(ARG_USER_PASSWORD);
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final Account account = new Account(
                accountName,
                intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            // Creating the account
            // Password is optionnal to this call, safer not to send it really
            tsAccountManager.addAccountExplicitly(account, accountPassword, null);
        } else {
            tsAccountManager.setPassword(account, accountPassword);
        }

        // Set the auth token we got
        // No setting the auth token will cause another call to the server to authenticate the user
        tsAccountManager.setAuthToken(account, tsAuthType, authToken);

        // Our base class can do what Android requires with the
        // KEY_ACCOUNT_AUTHENTICATOR_RESPONSE extra that onCreate has
        // already grabbed
        setAccountAuthenticatorResult(intent.getExtras());
        // Tell the account manager settings page that all went well
        setResult(RESULT_OK, intent);

        finish();

        if (getIntent().getBooleanExtra(ARG_FROM_AUTHENTICATOR, false)) {

            Intent problemFeedIntent = new Intent(this, ProblemsFeedActivity.class);
            startActivity(problemFeedIntent);
        }
    }
}
