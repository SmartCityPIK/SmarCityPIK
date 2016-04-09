package com.pik.smartcity.constanst;

import android.graphics.Color;
import android.net.Uri;

import com.pik.smartcity.R;

public interface IWhereMyLocationConstants {

    String MAP_KEY = "AIzaSyDgOwrATDzJ1dbp1ZGCe-rVrJCWf0tmNWo";

    String API_KEY = "AIzaSyBAE4tAuN6u5B-c7zbWHfRqTtvv1dNpDS4";

    boolean DEBUG = false;

    boolean SHOW_ADVERTISEMENT = false;
//    public static final String ADMOB_ID_BANNER = "ADMOB_ID_BANNER";
//    public static final String ADMOB_ID_INTERTESTIAL = "ADMOB_ID_INTERTESTIAL";

    String EMAIL_CONTACT = "EMAIL_CONTACT";
    String URL_YOUR_WEBSITE = "URL_YOUR_WEBSITE";
    String URL_YOUR_FACE_BOOK = "URL_YOUR_FACE_BOOK";
    String URL_MORE_APP = "URL_MORE_APP";

    String PROVIDER_AUTHORITY = "com.pik.smartcity.provider.MySuggestionProvider";

    String FORMAT_URL_PHOTO_REF = "https://maps.googleapis.com/maps/api/place/photo?photoreference=%1$s&sensor=false&maxwidth=%2$s&maxheight=%3$s&key=%4$s";
    String FORMAT_DETAIL_LOCATION_REF = "https://maps.googleapis.com/maps/api/place/details/json?reference=%1$s&sensor=%2$s&key=%3$s";
    String FORMAT_URL_TYPE_SEARCH = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
            + "location=%1$s,%2$s&radius=%3$s&types=%4$s&sensor=%5$s&key=%6$s";
    String FORMAT_URL_TEXT_SEARCH = "https://maps.googleapis.com/maps/api/place/textsearch/json?"
            + "location=%1$s,%2$s&radius=%3$s&query=%4$s&sensor=%5$s&key=%6$s";

    String FORMAT_URL_PHOTO = "https://maps.googleapis.com/maps/api/place/photo?photoreference=%1$s&maxheight=320&maxwidth=320&sensor=false&key=%2$s";

    String FORMAT_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=%1$s&destination=%2$s&sensor=%3$s&key=%4$s&mode=%5$s";
    String FORMAT_NEXTPAGE_TYPE_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%1$s,%2$s&radius=%3$s&types=%4$s&sensor=%5$s&key=%6$s&pagetoken=%7$s";
    String FORMAT_NEXTPAGE_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?location=%1$s,%2$s&radius=%3$s&types=%4$s&sensor=%5$s&key=%6$s&pagetoken=%7$s";

    int TYPE_SEARCH_BY_TYPES = 1;
    int TYPE_SEARCH_BY_TEXT = 2;
    String URL_RATE_APP = "https://play.google.com/store/apps/details?id=%1$s";

    String TAG_HOME = "TAG_HOME";
    String TAG_ABOUT = "TAG_ABOUT";
    String TAG_MY_LOCATION = "TAG_LOCATION";
    String TAG_FAVORITE = "TAG_FAVORITE";
    String TAG_SETTINGS = "TAG_SETTINGS";

    double INVALID_VALUE = -1000;

    String KEY_START_FROM = "KEY_START_FROM";
    String START_FROM_SPLASH = "splash";
    String START_FROM_SEARCH = "search";
    String START_FROM_TOTAL_PLACE = "totalPlace";
    String START_FROM_MAIN = "main";
    String START_FROM_DETAIL = "detail";

    String PIORITY_DISTANCE = "distance";
    String PIORITY_RATING = "prominence";

    String TRAVEL_MODE_DRIVING = "driving";
    String DRIVING = "Driving";

    String TRAVEL_MODE_WALKING = "walking";
    String WALKING = "Walking ";

    String UNIT_KILOMETTER = "Kilometers";
    String UNIT_MILE = "Miles";

    float DEFAULT_ZOOM_LEVEL = 18f;
    int TIME_OUT = 5;

    int HOME_INDEX = 0;
    int MY_LOCATION_INDEX = 1;
    int FAVORITES_INDEX = 2;
    int SETTINGS_INDEX = 3;
    int ABOUT_US_INDEX = 4;

    int MAP_INDEX = 1;
    int INVALID_LOCATION = 1;

    int MAX_RADIUS = 50;
    int MIN_RADIUS = 0;
    int DEFAULT_RADIUS_MARKER = 50;
    int DEFAULT_STROKE_MARKER_WIDTH = 4;

    String KEY_NAME_FRAGMENT = "name_fragment";
    String KEY_ID_FRAGMENT = "id_fragment";
    String KEY_NAME_KEYWORD = "keyword";
    String KEY_URL = "url";

    String FILE_FAVORITE_PLACES = "favorites.dat";
    String DIR_DATA = "data";

    int MAX_DISTANCE_TO_UPDATE = 500;

    String STATUS_OK = "ok";
    String STATUS_ZERO = "ZERO_RESULTS";

    int STROKE_COLOR = Color.parseColor("#800099cc");
    int FILL_COLOR = Color.parseColor("#3233b5e5");

    float ONE_MILE = 1.60934f;

    int[] LIST_ICON_ABOUTS = {R.drawable.icon_email, R.drawable.icon_website, R.drawable.icon_facebook, R.drawable.icon_rate_me, R.drawable.icon_more_app};

    int[] LIST_TITLE_ABOUTS = {R.string.title_contact_us, R.string.title_website, R.string.title_facebook, R.string.title_rate_us, R.string.title_more_app};

    int[] LIST_CONTENT_ABOUTS = {R.string.info_contact_us, R.string.info_website, R.string.info_facebook, R.string.info_rate_us, R.string.info_more_app};

    String[] LIST_LINK_ABOUTS = {"Email", URL_YOUR_WEBSITE, URL_YOUR_FACE_BOOK, URL_RATE_APP, URL_MORE_APP};

    String DATABASE_NAME = "datas";
    String DATABASE_TABLE = "records";
    int DATABASE_VERSION = 1;

    int SUGGESTION_KEYWORD = 1;
    int SEARCH_KEYWORD = 2;
    int GET_KEYWORD = 3;
    int SAVE_KEYWORD = 4;

    String KEY_ID = "_id";
    String KEY_NAME = "name";
    String KEY_KEYWORD = "keyword";

    String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY  NOT NULL, " + KEY_NAME + " text not null, "
            + KEY_KEYWORD + " text not null);";


    Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_AUTHORITY + "/records");
    Uri CONTENT_CHECK = Uri.parse("content://" + PROVIDER_AUTHORITY + "/records/@");

}
