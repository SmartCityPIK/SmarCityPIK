package com.pik.smartcity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pik.smartcity.adapter.DrawerAdapter;
import com.pik.smartcity.constanst.IWhereMyLocationConstants;
import com.pik.smartcity.dataMng.TotalDataManager;
import com.pik.smartcity.fragment.FragmentAboutUs;
import com.pik.smartcity.fragment.FragmentFavorites;
import com.pik.smartcity.fragment.FragmentMyLocation;
import com.pik.smartcity.fragment.FragmentSettings;
import com.pik.smartcity.fragment.FragmentWeather;
import com.ypyproductions.utils.DirectionUtils;
import com.ypyproductions.utils.StringUtils;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class WeatherActivity extends DBFragmentActivity implements IWhereMyLocationConstants {
    Typeface weatherFont;

    TextView cityField;
    TextView updateField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    Button direktori;
    Button traffic;
    Button cctv;
    Button sungai;
    Handler handler = new Handler();
    private DrawerAdapter mDrawerAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private String[] mListStrDrawerTitles;
    private Menu mMenu;
    private ArrayList<Fragment> mListFragments = new ArrayList<Fragment>();
    private int mCurrentIndex = WEATHER_INDEX;
    private String mStartFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_weather);
        Intent mIntent = getIntent();
        if (mIntent != null) {
            mStartFrom = mIntent.getStringExtra(KEY_START_FROM);
        }

        cityField = (TextView) findViewById(R.id.city_field);
        updateField = (TextView) findViewById(R.id.update_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);

        direktori = (Button) findViewById(R.id.directory);
        traffic = (Button) findViewById(R.id.traffic);
        cctv = (Button) findViewById(R.id.cctv);
        sungai = (Button) findViewById(R.id.sungai);

        direktori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(WeatherActivity.this, MainActivity.class);
                mIntent.putExtra(KEY_START_FROM, START_FROM_WEATHER);
                DirectionUtils.changeActivity(WeatherActivity.this, R.anim.slide_in_from_right, R.anim.slide_out_to_left, true, mIntent);

            }
        });

        traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sungai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //weatherFont = Typeface.createFromAsset(this.getAssets(),"fonts/weather.ttf");

        //weatherIcon.setTypeface(weatherFont);

        updateWeatherData(new CityPreference(this).getCity());
    }

    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(WeatherActivity.this, city);
                if (json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WeatherActivity.this,
                                    WeatherActivity.this.getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        try {
            String str;
            str = json.getString("name").toUpperCase(Locale.getDefault()) + ", " + json.getJSONObject("sys").getString("country");
            cityField.setText(str);

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.getDefault()) +
                            "\n" + "Humidity : " + main.getString("humidity") +
                            "%" +
                            "\n" + "Pressure : " + main.getString("pressure") +
                            " hPa");
            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp")) + " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updateOn = df.format(new Date(json.getLong("dt") * 1000));
            updateField.setText("Last update : " + updateOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        } catch (Exception e) {
            Log.e("Simple Weather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = this.getString(R.string.weather_sunny);
            } else {
                icon = this.getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = this.getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = this.getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = this.getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = this.getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = this.getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = this.getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

    public void changeCity(String city) {
        updateWeatherData(city);
        new CityPreference(this).setCity(city);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     /*   if(item.getItemId() == R.id.change_city){
            showInputDialog();
        }*/
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCurrentIndex != WEATHER_INDEX) {
                mDrawerAdapter.setSelectedDrawer(WEATHER_INDEX);
                showFragmentByTag(TAG_WEATHER, WEATHER_INDEX);
                invalidateOptionsMenu();
                setVisibleButtonMenu(true);
                return true;

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showFragmentByTag(String mTag, int index) {
        if (StringUtils.isStringEmpty(mTag)) {
            return;
        }
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment mFragment = mFragmentManager.findFragmentByTag(mTag);

        this.mCurrentIndex = index;

        if (mListFragments.size() > 0) {
            for (Fragment mFragment1 : mListFragments) {
                if (mFragment1 != null && !mFragment1.getTag().equals(mTag)) {
                    mFragmentTransaction.hide(mFragment1);
                }
            }
        }
        if (mFragment == null) {
            if (mTag.equals(TAG_ABOUT)) {
                FragmentAboutUs mFragmentAboutUs = new FragmentAboutUs();
                mFragmentTransaction.add(R.id.content_frame, mFragmentAboutUs, mTag);
                mListFragments.add(mFragmentAboutUs);
            } else if (mTag.equals(TAG_WEATHER)) {
                FragmentWeather mFragmentWeather = new FragmentWeather();
                mFragmentTransaction.add(R.id.content_frame, mFragmentWeather, mTag);
                mListFragments.add(mFragmentWeather);
            } else if (mTag.equals(TAG_SETTINGS)) {
                FragmentSettings mFragmentSettings = new FragmentSettings();
                mFragmentTransaction.add(R.id.content_frame, mFragmentSettings, mTag);
                mListFragments.add(mFragmentSettings);
            } else if (mTag.equals(TAG_MY_LOCATION)) {
                FragmentMyLocation mFragmentMyLocation = new FragmentMyLocation();
                mFragmentTransaction.add(R.id.content_frame, mFragmentMyLocation, mTag);
                mListFragments.add(mFragmentMyLocation);
            } else if (mTag.equals(TAG_FAVORITE)) {
                FragmentFavorites mFragmentFavorite = new FragmentFavorites();
                mFragmentTransaction.add(R.id.content_frame, mFragmentFavorite, mTag);
                mListFragments.add(mFragmentFavorite);
            }
        } else {
            mFragmentTransaction.show(mFragment);
        }
        setTitle(mListStrDrawerTitles[index]);
        mFragmentTransaction.commit();
        mDrawerLayout.closeDrawer(mDrawerListView);
    }

    private void setVisibleButtonMenu(boolean visible) {
        if (mMenu != null) {
            MenuItem mMenuItem = mMenu.findItem(R.id.action_menu);
            if (mMenuItem != null) {
                mMenuItem.setVisible(visible);
            }
            MenuItem mMenuDeleteItem = mMenu.findItem(R.id.action_delete_all);
            if (mMenuDeleteItem != null) {
                mMenuDeleteItem.setVisible(visible);
            }
            MenuItem mMenuSearchItem = mMenu.findItem(R.id.action_search);
            if (mMenuSearchItem != null) {
                mMenuSearchItem.setVisible(visible);
            }
            MenuItem mMenuDistanceItem = mMenu.findItem(R.id.action_quick_distance);
            if (mMenuDistanceItem != null) {
                mMenuDistanceItem.setVisible(visible);
            }
        }
    }

    @Override
    protected void onDestroy() {
      /*  if (adView != null) {
            adView.destroy();
        }*/

        if (mListFragments != null) {
            mListFragments.clear();
            mListFragments = null;
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyData() {
        super.onDestroyData();
        ImageLoader.getInstance().stop();
        TotalDataManager.getInstance().onDestroyTrackingService(getApplicationContext());
        TotalDataManager.getInstance().onDestroy();
        //showIntertestialAds();
    }

}





