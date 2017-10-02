package io.android.openchirp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<User> implements View.OnClickListener{

    private ArrayList<User> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        CheckBox chk;

    }

    public CustomAdapter(ArrayList<User> data, Context context) {
        super(context, R.layout.row_list_view, data);

        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        User dataModel=(User)object;

        switch (v.getId())
        {
            case R.id.enable_item:  Log.d("clicked","checkbox");
                dataModel.toggleChecked();
                Log.d("checkbox", String.valueOf(dataModel.isChecked()));
                break;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User dataModel = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_list_view, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name_item);
            viewHolder.chk = (CheckBox) convertView.findViewById(R.id.enable_item);

            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.name.setText(dataModel.getName());
        viewHolder.chk.setChecked(dataModel.isChecked());
        viewHolder.chk.setTag(position);

        return convertView;
    }

}