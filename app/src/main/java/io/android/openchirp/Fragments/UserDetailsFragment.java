package io.android.openchirp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import io.android.openchirp.OpenChirpHelper;
import io.android.openchirp.R;

public class UserDetailsFragment extends Fragment {

    private static final String TAG = "UserActivity";
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

//        String url = "http://iot.andrew.cmu.edu:10010/api/user";
        String url = "http://openchirp.andrew.cmu.edu:7000/api/user";

        View v = inflater.inflate(R.layout.fragment_user_details, container, false);
        TextView set_name = (TextView)v.findViewById(R.id.set_name);
        TextView set_email = (TextView)v.findViewById(R.id.set_email);

        try
        {
            JSONObject resp = openChirpHelper.GetOpenChirp_Object(url, mContext);

            String name = resp.getString("name");
            String email = resp.getString("email");
            set_name.setText(name);
            set_email.setText(email);

            Log.d(TAG, resp.toString());

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
