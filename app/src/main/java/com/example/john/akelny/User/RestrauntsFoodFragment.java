package com.example.john.akelny.User;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
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

    BottomNavigationView navBar;
    FrameLayout mainFrameLayout;

    public RestrauntsFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_restraunts_food, container, false);
        navBar = (BottomNavigationView) v.findViewById(R.id.main_nav);
        mainFrameLayout = (FrameLayout) v.findViewById(R.id.main_frame);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_restraunts:
                    {
                        RestrauntsFoodFragment.this.startActivity(new Intent(getActivity(), RestrauntsActivity.class));
                        return true;
                    }
                    case R.id.nav_cart:
                    {
                        RestrauntsFoodFragment.this.startActivity(new Intent(getActivity(), CartActivity.class));
                        return true;
                    }
                    case R.id.nav_orderlist:
                    {
                        RestrauntsFoodFragment.this.startActivity(new Intent(getActivity(), OrdersList.class));
                        return true;
                    }

                    case R.id.nav_myinfo:
                    {
                        RestrauntsFoodFragment.this.startActivity(new Intent(getActivity(), AccountActivity.class));
                        return true;
                    }

                    case R.id.nav_logout:
                    {
                        RestrauntsFoodFragment.this.startActivity(new Intent(getActivity(), LoginActivity.class));
                        return true;
                    }
                    default: return false;


                }
            }
        });
        return v;
    }

}
