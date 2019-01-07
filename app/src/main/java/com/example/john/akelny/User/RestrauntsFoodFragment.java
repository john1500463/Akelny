package com.example.john.akelny.User;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.john.akelny.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestrauntsFoodFragment extends Fragment {

    BottomNavigationBar 
    FrameLayout mainFrameLayout;

    public RestrauntsFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restraunts_food, container, false);
    }

}
