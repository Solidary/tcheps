package com.tcheps.activities;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Subscribe;
import com.tcheps.TsApplication;
import com.tcheps.authenticator.TsAccountGeneral;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.api.UserAuthenticationAPI;
import com.tcheps.restful.tasks.SignInTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignInActivity extends AccountAuthenticatorActivity {

    public final static String TAG = "SignInActivity";

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

    public final static int REQUEST_SIGN_UP = 1;

    @Bind(R.id.sign_in_email_et)
    EditText siEmail;
    @Bind(R.id.sign_in_password_et)
    EditText siPassword;

    AccountManager tsAccountManager;
    String tsAccountName,
            tsAccountType,
            tsAuthTokenType;

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
        Log.d(TAG, "onCreate >>> TsAccountType Start " + tsAccountType);
        if (tsAccountType == null) {
            tsAccountType = TsAccountGeneral.ACCOUNT_TYPE;
            Log.d(TAG, "onCreate >>> TsAccountType NULL " + tsAccountType);
        }
        tsAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (tsAuthTokenType == null) {
            tsAuthTokenType = TsAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGN_UP && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult >>> " + requestCode + " >>> OK >>> " + tsAccountType);
            Log.d(TAG, "OnActivityResult >>> " +
                    data.getBooleanExtra(SignInActivity.ARG_IS_ADDING_NEW_ACCOUNT, false) +  " >>> " +
                    data.getStringExtra(AccountManager.KEY_AUTHTOKEN));
            data.putExtra(AccountManager.KEY_ACCOUNT_TYPE, tsAccountType);
            finishSignIn(data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "OnRegister >>>> Goooo");
        TsApplication.getTsEventBus().register(this);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "OnPause >>>> Goooo");
        // TsApplication.getTsEventBus().unregister(this);

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        Log.d(TAG, "OnDestroy >>>> Goooo");
        // TsApplication.getTsEventBus().unregister(this);

        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        TsApplication.getTsEventBus().register(this);
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
        startActivityForResult(signUpIntent, REQUEST_SIGN_UP);
    }

    @OnClick({R.id.sign_in_sign_in_btn})
    public void onSignInClick() {
        final String email = siEmail.getText().toString();
        final String password = siPassword.getText().toString();
        Log.d(TAG, email + " -- " + password);

        SignInTask signInTask = new SignInTask();
        signInTask.execute(email, password);

        /*
        final Intent res = new Intent();
        Intent problemFeedIntent = new Intent(this, ProblemsFeedActivity.class);
        startActivity(problemFeedIntent);
        */
    }

    @Subscribe
    public void onSignInResponse(Intent result) {
        if (result.hasExtra(AccountManager.KEY_ERROR_MESSAGE)) {
            Log.e(TAG, "Error >>> " + result.getStringExtra(AccountManager.KEY_ERROR_MESSAGE));

            return;
        }
        Log.d(TAG, "OnSignInResponse >>> " + result.getStringExtra(AccountManager.KEY_AUTHTOKEN));
        result.putExtra(AccountManager.KEY_ACCOUNT_TYPE, tsAccountType);
        finishSignIn(result);
    }

    private void finishSignIn(Intent intent) {
        String accountPassword = intent.getStringExtra(ARG_USER_PASSWORD);
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final Account account = new Account(
                accountName,
                intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        Log.d(TAG, "finishSignIn >>> " + authToken + " >>>> " + account.type);
        if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            // Creating the account
            // Password is optionnal to this call, safer not to send it really
            tsAccountManager.addAccountExplicitly(account, accountPassword, null);
        } else {
            tsAccountManager.setPassword(account, accountPassword);
        }

        // Set the auth token we got
        // No setting the auth token will cause another call to the server to authenticate the user
        tsAccountManager.setAuthToken(account, tsAuthTokenType, authToken);

        // Our base class can do what Android requires with the
        // KEY_ACCOUNT_AUTHENTICATOR_RESPONSE extra that onCreate has
        // already grabbed
        setAccountAuthenticatorResult(intent.getExtras());
        // Tell the account manager settings page that all went well
        setResult(RESULT_OK, intent);


        if (!getIntent().getBooleanExtra(ARG_FROM_AUTHENTICATOR, false)) {

            Intent problemFeedIntent = new Intent(this, ProblemsFeedActivity.class);
            startActivity(problemFeedIntent);
        }
        finish();
    }
}
