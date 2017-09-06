package io.android.openchirp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import io.android.openchirp.OpenChirpHelper;
import io.android.openchirp.R;

public class MyDevicesFragment extends Fragment {

    private static final String TAG = "DevicesActivity";
    OpenChirpHelper openChirpHelper = new OpenChirpHelper();
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = container.getContext();

        Log.d(TAG, "Started HttpUrlConnection-User");

        String url = "http://openchirp.andrew.cmu.edu:7000/api/user/mydevices";

        View v = inflater.inflate(R.layout.fragment_my_devices, container, false);
        TextView devices_view = (TextView)v.findViewById(R.id.devices_view);

        try
        {
            JSONArray resp = openChirpHelper.GetOpenChirp_Array(url, mContext);
            devices_view.setText(resp.toString());
            Log.d(TAG, resp.toString());
            int length = resp.length();
            Log.d(TAG, String.valueOf(length));

            for(int i=0; i<length; i++)
            {
                String name = resp.getJSONObject(i).getString("name").toString();
                Log.d(TAG, name);
                String enabled = resp.getJSONObject(i).getString("enabled").toString();
                Log.d(TAG, enabled);
            }
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        Log.d(TAG, "Ended HttpUrlConnection-User");

        return v;
    }
}
