package com.pik.smartcity;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by fahri19 on 05/04/16.
 */
public class CityPreference {

    SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getCity() {
        return prefs.getString("city", "Jakarta");
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }
}
