package com.tcheps;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.tcheps.activities.ProblemsFeedActivity;
import com.tcheps.activities.SignInActivity;
import com.tcheps.authenticator.TsAccountGeneral;
import com.tcheps.models.User;

import java.io.IOException;

public class AuthActivity extends AppCompatActivity {
    public final static String TAG = "AuthActivity";

    AccountManager am;
    AuthPreferences authPreferences;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        Log.d(TAG, "On Create >>> Get accounts by type");
        am = AccountManager.get(this);
        authPreferences = new AuthPreferences(this);

        Account[] accounts = am.getAccountsByType(TsAccountGeneral.ACCOUNT_TYPE);
        Log.d(TAG, "On Create >>> " + TsAccountGeneral.ACCOUNT_TYPE + " >>> " + accounts.length);
        if (accounts.length == 0) {
            /*Intent siIntent = new Intent(this, SignInActivity.class);
            siIntent.putExtra(SignInActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
            startActivity(siIntent);

            finish();*/
            // return;
            Log.d(TAG, "On Create >>> No suitable account found >>> Directing user to add one");
            final AccountManagerFuture<Bundle> future = am.addAccount(
                    TsAccountGeneral.ACCOUNT_TYPE,
                    TsAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,
                    null,
                    new Bundle(),
                    this,
                    new OnAccountAdded(),
                    null
            );

            return;
        }
        Log.d(TAG, "On Create >>> Get Auth Token");

        account = accounts[0];
        fetchToken();
    }

    private void fetchToken() {
        Bundle options = new Bundle();
        // am.invalidateAuthToken(account.type, );
        Log.d(TAG, "Fetch Token >>> start");
        final AccountManagerFuture<Bundle> future = am.getAuthToken(
                account,
                TsAccountGeneral.ACCOUNT_TYPE,
                null,
                false, // this,
                new OnTokenAcquired(),
                null // new Handler(new OnError())
        );
        Log.d(TAG, "Fetch Token >>> Threading");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Fetch Token >>> Into Runnable");

                try {
                    Bundle bnd = future.getResult();

                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    Log.e(TAG, (authtoken != null) ? "SUCCESS!\ntoken: " + authtoken : "FAIL");
                    Log.d(TAG, "GetToken Bundle is " + bnd);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        }).start();

        Log.d(TAG, "Fetch Token >>> end");
        finish();
    }

    private void tokenAcquired() {
        Log.d(TAG, "Launch Problems Feed Activity");
        Intent pfIntent = new Intent(this, ProblemsFeedActivity.class);
        startActivity(pfIntent);
    }

    private void accountAdded(Bundle bundle) {
        fetchToken();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult >>> " + resultCode + " >>> " + resultCode);
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
            Bundle bundle;
            Log.d(TAG, "OnTokenAcquired >>> run");
            try {
                bundle = accountManagerFuture.getResult();
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                authPreferences.setToken(token);
                Log.d(TAG, "OnTokenAcquired >>>> Received Authentication Token >>> " + token);

                tokenAcquired();
            } catch (OperationCanceledException opcex) {
                opcex.printStackTrace();
                Log.e(TAG, "OperationCanceledException >>> " + opcex.getMessage());
                return;
            } catch (AuthenticatorException aex) {
                aex.printStackTrace();
                Log.e(TAG, "AuthenticatorException >>> " + aex.getMessage());
                return;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                Log.e(TAG, "IOException >>> " + ioe.getMessage());
                return;
            }

        }
    }

    private class OnAccountAdded implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
            Bundle bundle;
            Log.d(TAG, "OnAccountAdded >>> run");
            try {
                bundle = accountManagerFuture.getResult();

                accountAdded(bundle);
            } catch (OperationCanceledException opcex) {
                opcex.printStackTrace();
                Log.e(TAG, "OperationCanceledException >>> " + opcex.getMessage());
                return;
            } catch (AuthenticatorException aex) {
                aex.printStackTrace();
                Log.e(TAG, "AuthenticatorException >>> " + aex.getMessage());
                return;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                Log.e(TAG, "IOException >>> " + ioe.getMessage());
                return;
            }
        }
    }
}
