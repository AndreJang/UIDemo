package com.example.andre.uidemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andre.uidemo.R;

/**
 * Created by Andre on 2016/5/23.
 */
public class Fragment3 extends Fragment {
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment3,container,false);
        textView = (TextView) rootView.findViewById(R.id.tv);

        textView.setText("3");
        return rootView;
    }

}
