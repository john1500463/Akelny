package com.example.john.akelny.User;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.john.akelny.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestrauntsFoodFragment extends Fragment {


    public RestrauntsFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_restraunts_food, container, false);

        return v;
    }

}
