package com.tcheps.restful.tasks;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;

import com.tcheps.TsApplication;
import com.tcheps.activities.SignInActivity;
import com.tcheps.models.User;
import com.tcheps.restful.adapters.UserAuthenticationRestAdapter;
import com.tcheps.restful.responses.SignResponse;

/**
 * Created by mael-fosso on 9/11/15.
 */
public class SignUpTask extends AsyncTask<User, Void, Intent> {

    @Override
    protected Intent doInBackground(User... users) {
        User user = users[0];
        final Intent result = new Intent();

        SignResponse signResponse;
        UserAuthenticationRestAdapter userAuthenticationRestAdapter
                = new UserAuthenticationRestAdapter();

        try {
            signResponse = userAuthenticationRestAdapter.signUp(user);

            result.putExtra(AccountManager.KEY_AUTHTOKEN, signResponse.getToken());
            result.putExtra(AccountManager.KEY_ACCOUNT_NAME, signResponse.getUser().getEmail());

            result.putExtra(SignInActivity.ARG_USER_PASSWORD, user.getPassword());
            result.putExtra(SignInActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        } catch (Exception ex) {
            result.putExtra(AccountManager.KEY_ERROR_MESSAGE, ex.getMessage());
        }

        return result;
    }

    @Override
    protected void onPostExecute(Intent intent) {
        TsApplication.getTsEventBus().post(intent);
    }
}
