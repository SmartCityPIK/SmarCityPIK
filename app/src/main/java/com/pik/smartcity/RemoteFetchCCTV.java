package com.pik.smartcity;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by fahri19 on 05/04/16.
 */
public class RemoteFetchCCTV {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.lewatmana.com/v1/camera/?city__slug=jakarta-utara&format=json";

    public static JSONObject getJSON(Context context) {
        try {

            DefaultHttpClient client = new DefaultHttpClient();
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("android1", "xaiphoo9");
            provider.setCredentials(AuthScope.ANY, credentials);


            //URL url = new URL(String.format(OPEN_WEATHER_MAP_API));
            HttpGet request = new HttpGet(OPEN_WEATHER_MAP_API);
            //request.setURI(new URI(OPEN_WEATHER_MAP_API));
            HttpResponse response = client.execute(request);
/*

            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(13024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());
//absolute_url:http://lewatmana.com/cam/206/yos-sudarso-tanjung-priok/
        //    if (data.getInt("cod") != 200) {
        //        return null;
        //    }
*/
            //return data;
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
