package it.unimib.icasiduso.sportrack.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DeviceUtils {

    public DeviceUtils(){
    }

    public static int getScreenWidthMinusPadding(Activity activity, int padding){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (activity != null) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels - Math.round(padding * (displayMetrics.densityDpi / 160f));
    }
}
