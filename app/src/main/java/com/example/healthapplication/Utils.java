package com.example.healthapplication;

import android.app.Activity;
import android.content.Intent;

public class Utils {
    private static int sTheme;
    public final static int THEMEDEFAULT = 0;
    public final static int THEME1 = 1;
    public final static int THEME2 = 2;
    public final static int THEME3 = 3;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEMEDEFAULT:
                activity.setTheme(R.style.Theme_HealthApplication);
                break;
            case THEME1:
                activity.setTheme(R.style.Theme1);
                break;
            case THEME2:
                activity.setTheme(R.style.Theme2);
                break;
            case THEME3:
                activity.setTheme(R.style.Theme3);
                break;
        }
    }

}
