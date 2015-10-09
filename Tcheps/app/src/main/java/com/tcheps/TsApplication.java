package com.tcheps;

import android.app.Application;

import com.squareup.otto.Bus;
import com.tcheps.activities.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mael-fosso on 9/3/15.
 */
public class TsApplication extends Application {

    private static Bus tsEventBus;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        // .setDefaultFontPath("fonts/Roboto-Thin.ttf")
                        // .setFontAttrId(R.attr.fontPath)
                        // .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                        .build()
        );


        // android:textColor="#333"
        // android:textColorHint="#666"
    }

    public static Bus getTsEventBus() {
        if (tsEventBus == null) {
            tsEventBus = new Bus();
        }

        return tsEventBus;
    }

}
