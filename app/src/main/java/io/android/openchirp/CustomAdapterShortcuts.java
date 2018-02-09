package io.android.openchirp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static io.android.openchirp.R.layout.row_list_view_shortcuts;


public class CustomAdapterShortcuts extends ArrayAdapter<UserShortcuts> implements View.OnClickListener{

    private ArrayList<UserShortcuts> dataSet;
    Context mContext;
    OpenChirpHelper openChirpHelper = new OpenChirpHelper();
    Constants constants = new Constants();

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        Button run;

    }

    public CustomAdapterShortcuts(ArrayList<UserShortcuts> data, Context context) {
        super(context, row_list_view_shortcuts, data);

        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        UserShortcuts dataModel=(UserShortcuts)object;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserShortcuts dataModel = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(row_list_view_shortcuts, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name_item);
            viewHolder.run = (Button) convertView.findViewById(R.id.run_item);

            Button run_button = (Button) convertView.findViewById(R.id.run_item);
            run_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "Started HttpUrlConnection");

                    String cookie = constants.getCookie(mContext);
                    String url = "http://openchirp.andrew.cmu.edu:7000/api/device/590b8ab0c1d7ee2313ee421c/command/590ceff843fc916594746b16";

                    JSONObject postMessage = new JSONObject();
                    try {
                        postMessage.put("cookie", cookie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("Shortcuts-Resp", postMessage.toString());

                    JSONObject resp = null;
                    try {
                        resp = openChirpHelper.PostOpenChirp_New(url, postMessage, mContext);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Log.d("Shortcuts-Resp", e.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("Shortcuts-Resp", e.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Shortcuts-Resp", e.toString());
                    }
                    Log.d("Shortcuts-Resp", resp.toString());
                    Log.d(TAG, "Ended HttpUrlConnection");

                }
            });
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.name.setText(dataModel.getName());
        viewHolder.run.setClickable(true);

        return convertView;
    }

}