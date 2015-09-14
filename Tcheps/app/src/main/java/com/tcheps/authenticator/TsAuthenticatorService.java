package com.tcheps.authenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by mael-fosso on 9/9/15.
 */
public class TsAuthenticatorService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /*if(intent.getAction().equals(
                AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
            return null;
        }*/

        AbstractAccountAuthenticator authenticator =
                new TsAuthenticator(this);
        return authenticator.getIBinder();
    }
}
