package io.android.openchirp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.android.openchirp.CustomAdapter;
import io.android.openchirp.CustomAdapterShortcuts;
import io.android.openchirp.OpenChirpHelper;
import io.android.openchirp.R;
import io.android.openchirp.User;
import io.android.openchirp.UserShortcuts;

public class MyShortcutsFragment extends Fragment {

    private static final String TAG = "ShortcutsActivity";
    OpenChirpHelper openChirpHelper = new OpenChirpHelper();
    Context mContext;
    ListView lv;

    ArrayList<UserShortcuts> user;
    CustomAdapterShortcuts adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = container.getContext();

        Log.d(TAG, "Started HttpUrlConnection-Shortcuts");

        String url = "http://openchirp.andrew.cmu.edu:7000/api/user/shortcuts";

        View v = inflater.inflate(R.layout.list_view_shortcuts, container, false);

        lv = (ListView) v.findViewById(R.id.list_shortcuts);
        user = new ArrayList<>();
        adapter= new CustomAdapterShortcuts(user,v.getContext());

        try
        {
            JSONArray resp = openChirpHelper.GetOpenChirp_Array(url, mContext);
            Log.d(TAG, resp.toString());
            int length = resp.length();
            Log.d(TAG, String.valueOf(length));

            for(int i=0; i<length; i++)
            {
                final String name= resp.getJSONObject(i).getString("name").toString();

                user.add(new UserShortcuts(name));
                lv.setAdapter(adapter);


            }
        }
        catch (ExecutionException | InterruptedException | JSONException e)
        {
            e.printStackTrace();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int pos, long id)
            {
                Log.d(TAG, "Entered....");
                UserShortcuts u = user.get(pos);
                Toast.makeText(getActivity(), u.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "Ended HttpUrlConnection-Devices");



        return v;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//
//        super.onActivityCreated(savedInstanceState);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.heroes, android.R.layout.simple_list_item_1);
//        setListAdapter(adapter);
//
//
//    }
}


