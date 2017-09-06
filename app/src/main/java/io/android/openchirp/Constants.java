package io.android.openchirp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Bavana on 7/19/2017.
 */

 class Constants {

    String TAG = "Constants";

    String getIDToken(Context mContext){
        SharedPreferences preferences = mContext.getSharedPreferences("openchirp", Context.MODE_PRIVATE);
        String id_token = preferences.getString("id_token", "0");
        Log.d(TAG+TAG, id_token);
        return id_token;
    }

    String getCookie(Context mContext){
        SharedPreferences preferences = mContext.getSharedPreferences("openchirp", Context.MODE_PRIVATE);
        String cookie = preferences.getString("cookie", "0");
        Log.d(TAG, cookie);
        return cookie;
    }

    SharedPreferences.Editor getEditor(Context mContext){
        SharedPreferences preferences_cookie = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences_cookie.edit();
    }

}
