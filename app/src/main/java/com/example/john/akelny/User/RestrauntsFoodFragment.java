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

    BottomNavigationView navBar;
    FrameLayout mainFrameLayout;

    public RestrauntsFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restraunts_food, container, false);
        navBar = (BottomNavigationView) findViewById(R.id.main_nav);
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_frame);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_restraunts:
                    {
                        startActivity(new Intent(RestrauntsFoodFragment.this, RestrauntsActivity.class));
                        return true;
                    }
                    case R.id.nav_cart:
                    {
                        startActivity(new Intent(RestrauntsFoodFragment.this, CartActivity.class));
                        return true;
                    }
                    case R.id.nav_orderlist:
                    {
                        startActivity(new Intent(RestrauntsFoodFragment.this, OrdersList.class));
                        return true;
                    }

                    case R.id.nav_myinfo:
                    {
                        startActivity(new Intent(RestrauntsFoodFragment.this, AccountActivity.class));
                        return true;
                    }

                    case R.id.nav_logout:
                    {
                        startActivity(new Intent(RestrauntsFoodFragment.this, LoginActivity.class));
                        return true;
                    }
                    default: return false;


                }
            }
        });
    }

}
