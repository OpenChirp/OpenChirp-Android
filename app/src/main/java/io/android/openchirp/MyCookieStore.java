package io.android.openchirp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Bavana on 6/28/2017.
 */

class MyCookieStore extends AppCompatActivity implements CookieStore {

    String TAG = "Cookie";
    /*
     * The memory storage of the cookies
     */
    private Map<URI, List<HttpCookie>> mapCookies = new HashMap<URI, List<HttpCookie>>();
    /*
     * The instance of the shared preferences
     */
    private final SharedPreferences spePreferences;

    private Context context;

    SharedPreferences preferences_cookie;
    SharedPreferences.Editor editor_cookie;

    /*
     * @see java.net.CookieStore#add(java.net.URI, java.net.HttpCookie)
     */
    public void add(URI uri, HttpCookie cookie) {

        System.out.println("add");
        System.out.println(cookie.toString());

        List<HttpCookie> cookies = mapCookies.get(uri);
        if (cookies == null) {
            cookies = new ArrayList<HttpCookie>();
            mapCookies.put(uri, cookies);
        }
        cookies.add(cookie);

        editor_cookie = preferences_cookie.edit();

        Log.d(TAG,cookie.toString());

        editor_cookie.putString("cookie", cookie.toString());
        editor_cookie.apply();
        Log.d("Put", editor_cookie.toString());
    }

    /*
     * Constructor
     *
     * @param  ctxContext the context of the Activity
     */
    @SuppressWarnings("unchecked")
    public MyCookieStore(Context ctxContext) {

        preferences_cookie = ctxContext.getSharedPreferences("openchirp", MODE_PRIVATE);
        spePreferences = ctxContext.getSharedPreferences("CookiePrefsFile", 0);
        Map<String, ?> prefsMap = spePreferences.getAll();

        for(Map.Entry<String, ?> entry : prefsMap.entrySet()) {

            for (String strCookie : (HashSet<String>) entry.getValue()) {

                if (!mapCookies.containsKey(entry.getKey())) {

                    List<HttpCookie> lstCookies = new ArrayList<HttpCookie>();
                    lstCookies.addAll(HttpCookie.parse(strCookie));

                    try {

                        mapCookies.put(new URI(entry.getKey()), lstCookies);

                    } catch (URISyntaxException e) {

                        e.printStackTrace();

                    }

                } else {

                    List<HttpCookie> lstCookies = mapCookies.get(entry.getKey());
                    lstCookies.addAll(HttpCookie.parse(strCookie));

                    try {

                        mapCookies.put(new URI(entry.getKey()), lstCookies);
                        Log.d(TAG, lstCookies.toString());

                    } catch (URISyntaxException e) {

                        e.printStackTrace();

                    }

                }

                System.out.println(entry.getKey() + ": " + strCookie);

            }

        }

    }

    /*
     * @see java.net.CookieStore#get(java.net.URI)
     */
    public List<HttpCookie> get(URI uri) {

        List<HttpCookie> lstCookies = mapCookies.get(uri);

        if (lstCookies == null)
            mapCookies.put(uri, new ArrayList<HttpCookie>());

        return mapCookies.get(uri);

    }

    /*
     * @see java.net.CookieStore#removeAll()
     */
    public boolean removeAll() {

        mapCookies.clear();
        return true;

    }

    /*
     * @see java.net.CookieStore#getCookies()
     */
    public List<HttpCookie> getCookies() {

        Collection<List<HttpCookie>> values = mapCookies.values();

        List<HttpCookie> result = new ArrayList<HttpCookie>();
        for (List<HttpCookie> value : values) {
            result.addAll(value);
        }

        return result;

    }

    /*
     * @see java.net.CookieStore#getURIs()
     */
    public List<URI> getURIs() {

        Set<URI> keys = mapCookies.keySet();
        return new ArrayList<URI>(keys);

    }

    /*
     * @see java.net.CookieStore#remove(java.net.URI, java.net.HttpCookie)
     */
    public boolean remove(URI uri, HttpCookie cookie) {

        List<HttpCookie> lstCookies = mapCookies.get(uri);

        if (lstCookies == null)
            return false;

        return lstCookies.remove(cookie);

    }

}
