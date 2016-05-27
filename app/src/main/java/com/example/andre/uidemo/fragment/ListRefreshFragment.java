package com.example.andre.uidemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andre.uidemo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 2016/5/23.
 */
public class ListRefreshFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView lv;
    private TextView textView;
    private SwipeRefreshLayout mRefreshLayout;
    private static final int REFRESH_TIME = 3000;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_fragment,container,false);
        lv = (ListView) rootView.findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,initDatas()));
        mRefreshLayout = (SwipeRefreshLayout) (SwipeRefreshLayout) rootView.findViewById(R.id.refrsh_layout);
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
                );
        mRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               mRefreshLayout.setRefreshing(false);
               Toast.makeText(getContext(), "已完成刷新!", Toast.LENGTH_SHORT).show();
           }
       },REFRESH_TIME);

    }

    private List<String> initDatas() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            datas.add(" " + i);
        }
        return datas;
    }


}
