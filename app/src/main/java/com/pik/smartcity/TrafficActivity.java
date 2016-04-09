package com.pik.smartcity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.pik.smartcity.adapter.DrawerAdapter;
import com.pik.smartcity.constanst.IWhereMyLocationConstants;
import com.pik.smartcity.dataMng.TotalDataManager;
import com.pik.smartcity.object.HomeSearchObject;
import com.pik.smartcity.object.KeywordObject;
import com.pik.smartcity.provider.MySuggestionDAO;
import com.ypyproductions.utils.DBLog;
import com.ypyproductions.utils.DirectionUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * MainActivity.java
 *
 * @author :DOBAO
 * @Email :dotrungbao@gmail.com
 * @Skype :baopfiev_k50
 * @Phone :+84983028786
 * @Date :Nov 26, 2013
 * @project :WhereMyLocation
 * @Package :com.ypyproductions.wheremylocation
 */
public class TrafficActivity extends DBFragmentActivity implements IWhereMyLocationConstants, OnMapReadyCallback {


    public static final String TAG = TrafficActivity.class.getSimpleName();
    public DisplayImageOptions mImgOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mListStrDrawerTitles;
    private DrawerAdapter mDrawerAdapter;
    private ArrayList<Fragment> mListFragments = new ArrayList<Fragment>();
    private int mCurrentIndex = HOME_INDEX;
    private Menu mMenu;
    private String mStartFrom;
    //private AdView adView;
    private Uri mQueryUri;
    private SearchView searchView;
    private GoogleMap mMap;

    private MenuItem menuSearchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        setNoDialog(true);
        Intent mIntent = getIntent();
        if (mIntent != null) {
            mStartFrom = mIntent.getStringExtra(KEY_START_FROM);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        handleIntent(getIntent());
    }
/*
    private void setUpLayoutAdmob() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_ad);
        boolean b = SHOW_ADVERTISEMENT;
        if (b) {
            adView = new AdView(this);
            adView.setAdUnitId(ADMOB_ID_BANNER);
            adView.setAdSize(AdSize.SMART_BANNER);

            layout.addView(adView);
            AdRequest mAdRequest = new AdRequest.Builder().build();
            adView.loadAd(mAdRequest);
        } else {
            layout.setVisibility(View.GONE);
        }
    }
*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent mIntent = new Intent(TrafficActivity.this, WeatherActivity.class);
            mIntent.putExtra(KEY_START_FROM, START_FROM_MAIN);

            DirectionUtils.changeActivity(TrafficActivity.this, R.anim.slide_in_from_right, R.anim.slide_out_to_left, true, mIntent);

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            processSearchData(TYPE_SEARCH_BY_TEXT, query, query, true);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            mQueryUri = intent.getData();
            if (mQueryUri != null) {
                String keyword = intent.getStringExtra(SearchManager.EXTRA_DATA_KEY);

                KeywordObject mKeywordObject = TotalDataManager.getInstance().getKeyWordObject(keyword);
                String realName = "";
                if (mKeywordObject != null) {
                    realName = mKeywordObject.getName();
                }
                processSearchData(TYPE_SEARCH_BY_TYPES, keyword, realName, false);
            }
        }
    }

    private void processSearchData(int type, String query, String realname, boolean isAllowAddRecent) {
        HomeSearchObject mHomeSearchObject = TotalDataManager.getInstance().findHomeSearchObject(query);
        if (mHomeSearchObject != null) {
            TotalDataManager.getInstance().setSelectedObject(mHomeSearchObject);
        } else {
            TotalDataManager.getInstance().setSelectedObject(0);
            mHomeSearchObject = TotalDataManager.getInstance().getListHomeSearchObjects().get(0);
            if (mHomeSearchObject != null) {
                mHomeSearchObject.setKeyword(query);
                mHomeSearchObject.setType(type);
                mHomeSearchObject.setRealName(realname.toUpperCase(Locale.US));
                if (isAllowAddRecent) {
                    KeywordObject mKeywordObject = MySuggestionDAO.getPrivateData(this, query);
                    DBLog.d(TAG, "==============>mKeywordObject=" + mKeywordObject);
                    if (mKeywordObject == null) {
                        KeywordObject mKeywordObject2 = new KeywordObject(query, query);
                        MySuggestionDAO.insertData(this, mKeywordObject2);
                    }
                }
            }
        }
        Intent mIntent = new Intent(this, MainSearchActivity.class);
        DirectionUtils.changeActivity(this, R.anim.slide_in_from_right, R.anim.slide_out_to_left, true, mIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker and move the camera
        LatLng pik = new LatLng(-6.111880, 106.752616);
        mMap.addMarker(new MarkerOptions().position(pik).title("Pantai Indah Kapuk"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pik, 14.0f));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        // Enable / Disable traffic status
        googleMap.setTrafficEnabled(true);

        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(true);

    }

    public interface OnSearchListener {
        void onSearch(HomeSearchObject mHomeSearchObject);
    }

}
