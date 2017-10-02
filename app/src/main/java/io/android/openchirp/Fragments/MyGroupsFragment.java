package io.android.openchirp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.android.openchirp.CustomAdapter;
import io.android.openchirp.OpenChirpHelper;
import io.android.openchirp.R;
import io.android.openchirp.User;

public class MyGroupsFragment extends Fragment {

    private static final String TAG = "GroupsActivity";
    OpenChirpHelper openChirpHelper = new OpenChirpHelper();
    Context mContext;
    ListView lv;

    ArrayList<User> user;
    CustomAdapter adapter;

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

        View v = inflater.inflate(R.layout.list_view_groups, container, false);

        lv = (ListView) v.findViewById(R.id.list_groups);
        user = new ArrayList<>();
        adapter= new CustomAdapter(user,v.getContext());

        try
        {
            JSONObject resp = openChirpHelper.GetOpenChirp_Object(url, mContext);
            Log.d(TAG, resp.toString());
            int length = resp.length();
            Log.d(TAG, String.valueOf(length));

            JSONArray groups = resp.getJSONArray("groups");

            for(int i=0; i<groups.length(); i++)
            {

                JSONObject actor = groups.getJSONObject(i);
                String name = actor.getString("name");
                String write_access = actor.getString("write_access");
                Log.d(TAG, name);
                Log.d(TAG, write_access);

                user.add(new User(name, Boolean.valueOf(write_access))) ;
                lv.setAdapter(adapter);

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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int pos, long id)
            {
                Log.d(TAG, "Entered....");
                User u = user.get(pos);
                Toast.makeText(getActivity(), u.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "Ended HttpUrlConnection-Groups");

        return v;
    }
}
