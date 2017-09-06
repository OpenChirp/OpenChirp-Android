package io.android.openchirp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import io.android.openchirp.R;

public class CustomAdapter extends ArrayAdapter<User> implements View.OnClickListener{

    String TAG = "Custom Adapter";

    private ArrayList<User> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView device_name;
        TextView device_enable;
    }

    public CustomAdapter(ArrayList<User> data, Context context) {
        super(context, R.layout.fitlistview, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User dataModel = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fitlistview, parent, false);

            viewHolder.device_name = (TextView) convertView.findViewById(R.id.device_name);
            viewHolder.device_enable = (TextView) convertView.findViewById(R.id.device_enable);

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.device_name.setText(dataModel.getName());
        Log.d(TAG, dataModel.getName());
        viewHolder.device_enable.setText(dataModel.getEnable());
        Log.d(TAG, dataModel.getEnable());

        return convertView;
    }

}