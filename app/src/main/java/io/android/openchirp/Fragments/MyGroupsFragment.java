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
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import io.android.openchirp.OpenChirpHelper;
import io.android.openchirp.R;

public class MyGroupsFragment extends Fragment {

    private static final String TAG = "GroupsActivity";
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

        Log.d(TAG, "Started HttpUrlConnection-Groups");

        String url = "http://openchirp.andrew.cmu.edu:7000/api/user";

        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);
        TextView groups_view = (TextView)v.findViewById(R.id.groups_view);

        try
        {
            JSONObject resp = openChirpHelper.GetOpenChirp_Object(url, mContext);
            groups_view.setText(resp.toString());
            Log.d(TAG, resp.toString());
            int length = resp.length();
            Log.d(TAG, String.valueOf(length));

            JSONArray groups = resp.getJSONArray("groups");

            for(int i=0; i<groups.length(); i++)
            {
                String name = resp.getJSONObject(String.valueOf(i)).getString("name").toString();
                Log.d(TAG, name);
                String write_access = resp.getJSONObject(String.valueOf(i)).getString("write_access").toString();
                Log.d(TAG, write_access);
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
            Log.d(TAG, e.toString());

            e.printStackTrace();
        }

        Log.d(TAG, "Ended HttpUrlConnection-Groups");

        return v;
    }
}
