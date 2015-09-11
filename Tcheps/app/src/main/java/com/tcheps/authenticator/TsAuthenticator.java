package com.tcheps.authenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tcheps.activities.SignInActivity;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.responses.SignResponse;
import com.tcheps.restful.api.UserAuthenticationAPI;

/**
 * Created by mael-fosso on 9/9/15.
 */
public class TsAuthenticator extends AbstractAccountAuthenticator {

    protected Context mContext;

    public TsAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse,
                             String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options)
                throws NetworkErrorException {
        // We absolutely cannot add an account without some information
        // from the user; so we're definitely going to return an Intent
        // via KEY_INTENT
        final Bundle bundle = new Bundle();

        // We're going to use a LoginActivity to talk to the user (mContext
        // we'll have noted on construction).
        final Intent intent = new Intent(mContext, SignInActivity.class);

        // We can configure that activity however we wish via the Intent.
        // We'll set ARG_IS_ADDING_NEW_ACCOUNT so the activity knows to ask for the account name as well
        intent.putExtra(SignInActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(SignInActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(SignInActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);

        intent.putExtra(SignInActivity.ARG_FROM_AUTHENTICATOR, true);

        // It will also need to know how to send its response to the account manager;
        // SignInActivity muste derive from AccountAuthenticatorActivity, which want this key set
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);

        // Wrap up this intent, and return it, which will cause the intent to be run
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);

        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse,
                               final Account account,
                               String authTokenType, Bundle options) throws NetworkErrorException {

        UserAuthenticationAPI userAuthenticationAPI = TsServiceGenerator.create(UserAuthenticationAPI.class);
        // We can add rejection of a request for a token type we
        // don't support here

        // Get the instance of the AccountManager that's making the
        // request
        final AccountManager am = AccountManager.get(mContext);

        // See if there is already an authentication token stored
        String authToken = am.peekAuthToken(account, authTokenType);

        // If we have no token, use the account credentials to fetch a new one, effectively
        // another logon
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                /*userAuthenticationAPI.signIn(account.name, password, new Callback<SignResponse>() {
                    @Override
                    public void success(SignResponse signResponse, Response response) {
                        // authToken = signResponse.getToken();

                        successSignInHandBackToken(signResponse, account);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });*/
            }
        }

        // If we either got a cached token, or fetched a new one, hand
        // it back to the client that called us.
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we don't have a token, and we don't have a password that will let us
        // get new one (or we weren't able to use the password we do have). We need to fetch information
        // from the user, we do that by creating and intent to an activity child class
        final Intent intent = new Intent(mContext, SignInActivity.class);

        // We want to give the Activity the information we want it to return to the AccountManager
        // We'll cover that with the KEY_ACCOUNT_AUTHENTICATOR_RESPONSE parameter
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        // We'll also give it the parameters we've already looked up, were give
        intent.putExtra(SignInActivity.ARG_IS_ADDING_NEW_ACCOUNT, false);
        intent.putExtra(SignInActivity.ARG_ACCOUNT_NAME, account.name);
        intent.putExtra(SignInActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(SignInActivity.ARG_AUTH_TYPE, authTokenType);

        intent.putExtra(SignInActivity.ARG_FROM_AUTHENTICATOR, true);

        // Remember that we have to return a Bundle, not an Intent, but
        // we can tell the caller to run our intent to get its
        // information with the KEY_INTENT parameter in the returned
        // Bundle
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    private Bundle successSignInHandBackToken(SignResponse signResponse, Account account) {
        // If we either got a cached token, or fetched a new one, hand
        // it back to the client that called us.
        if (!TextUtils.isEmpty(signResponse.getToken())) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, signResponse.getToken());

            return result;
        }

        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (TsAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType)) {
            return TsAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        } else if (TsAccountGeneral.AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType)) {
            return TsAccountGeneral.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        } else {
            return authTokenType + "(Label)";
        }
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }
}
