package com.tcheps.restful.tasks;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;

import com.tcheps.TsApplication;
import com.tcheps.activities.SignInActivity;
import com.tcheps.authenticator.TsAccountGeneral;
import com.tcheps.restful.adapters.UserAuthenticationRestAdapter;
import com.tcheps.restful.api.UserAuthenticationAPI;
import com.tcheps.restful.responses.SignResponse;

/**
 * Created by mael-fosso on 9/11/15.
 */
public class SignInTask extends AsyncTask<String, Void, Intent> {

    @Override
    protected Intent doInBackground(String... params) {
        final Intent result = new Intent();
        String email = params[0],
                password = params[1];

        SignResponse signResponse;
        UserAuthenticationRestAdapter userAuthenticationRestAdapter
                = new UserAuthenticationRestAdapter();

        try {
            signResponse = userAuthenticationRestAdapter.signIn(email, password);

            result.putExtra(AccountManager.KEY_AUTHTOKEN, signResponse.getToken());
            result.putExtra(AccountManager.KEY_ACCOUNT_NAME, signResponse.getUser().getEmail());
            result.putExtra(AccountManager.KEY_ACCOUNT_TYPE, TsAccountGeneral.ACCOUNT_TYPE);

            result.putExtra(SignInActivity.ARG_USER_PASSWORD, password);
            result.putExtra(SignInActivity.ARG_USER_DATA, signResponse.getUser());
        } catch (Exception ex) {
            result.putExtra(AccountManager.KEY_ERROR_MESSAGE, ex.getMessage());
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Intent intent) {

        TsApplication.getTsEventBus().post(intent);
    }
}
