package io.android.openchirp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.SecureRandom;

import io.android.openchirp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment {

    public NewFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new, container, false);
        TextView dev_eui_text = (TextView)v.findViewById(R.id.dev_eui);
        TextView app_eui_text = (TextView)v.findViewById(R.id.app_eui);
        TextView app_id_text = (TextView)v.findViewById(R.id.app_id);


        SecureRandom dev_eui = new SecureRandom();
        byte dev_eui_bytes[] = new byte[8];
        dev_eui.nextBytes(dev_eui_bytes);

        SecureRandom app_eui = new SecureRandom();
        byte app_eui_bytes[] = new byte[8];
        app_eui.nextBytes(app_eui_bytes);

        SecureRandom app_id = new SecureRandom();
        byte app_id_bytes[] = new byte[16];
        app_id.nextBytes(app_id_bytes);
        for(int i=0; i<16; i++) {
        app_id_text.setText(app_id_bytes[i]);
        }

        // Inflate the layout for this fragment
        return v;
    }
}
