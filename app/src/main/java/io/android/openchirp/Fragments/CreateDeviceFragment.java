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

public class CreateDeviceFragment extends Fragment {

    private static final String TAG = "CreateDeviceActivity";
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

        Log.d(TAG, "Started HttpUrlConnection-Devices");

        View v = inflater.inflate(R.layout.fragment_create_device, container, false);

        final TextView device_name = (TextView) v.findViewById(R.id.device_name);

        Button createDeviceButton = (Button) v.findViewById(R.id.create_device_button);
        createDeviceButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String url = "http://openchirp.andrew.cmu.edu:7000/api/device";

                final JSONObject postMessage = new JSONObject();

                try {
                    postMessage.put("name", "Lab Door");
                    postMessage.put("location_id", "58d2cf268113446f5c8c28bc");
                    Log.d("postMessage", postMessage.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject resp = openChirpHelper.PostOpenChirp_New(url, postMessage, mContext);
                    Log.d(TAG, resp.toString());

                }

                catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, e.toString());

                }

                Log.d(TAG, "Ended HttpUrlConnection-Devices");

            }
        });



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


