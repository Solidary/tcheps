package com.tcheps.utils;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * Created by mael-fosso on 9/2/15.
 */
public class Utils {

    public static Drawable getTintedDrawable(Resources res,
                                      @DrawableRes int drawableResId, int colorResId) {
        Drawable drawable = res.getDrawable(drawableResId);
        int color = colorResId; // res.getColor(colorResId);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        return drawable;
    }
}
