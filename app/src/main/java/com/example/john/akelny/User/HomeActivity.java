package com.example.john.akelny.User;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;



import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static com.example.john.akelny.User.RestrauntsFoodFragment.Carttt;
import static com.example.john.akelny.User.RestrauntsFoodFragment.resDelFees;

public class HomeActivity extends Activity implements MaterialSearchBar.OnSearchActionListener{
    ListView listView;
    ImageView imageview;
    TextView one;
    TextView two;
    private MaterialSearchBar searchBar;
    Fragment fragment;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Resturant>resturants;
    ImageView imageview2;
    BottomNavigationView navBar;
    FrameLayout mainFrameLayout;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setHint("Custom hint");
        searchBar.setSpeechMode(true);
        searchBar.setOnSearchActionListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new RestrauntsFragment();
        Bundle arguments = new Bundle();
        arguments.putString( "John" , "j");
        fragment.setArguments(arguments);

        fragmentTransaction.replace(R.id.fragment_container, fragment, "rest");
        fragmentTransaction.addToBackStack("rest");
        fragmentTransaction.commit();


        navBar = (BottomNavigationView) findViewById(R.id.main_nav);
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_frame);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_restraunts:
                    {
                        FragmentManager fragmentManager = getFragmentManager();;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new RestrauntsFragment(), "rest");
                        fragmentTransaction.addToBackStack("rest");
                        fragmentTransaction.commit();
                        return true;
                    }
                    case R.id.nav_cart:
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("CartItems", Carttt);
                        bundle.putString("DelFees",resDelFees);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Fragment fragment = new CartFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fragment, "rest");

                        fragmentTransaction.addToBackStack("rest");
                        fragmentTransaction.commit();
                        return true;
                    }
                    case R.id.nav_orderlist:
                    {
                        FragmentManager fragmentManager = getFragmentManager();;
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new OrderListFragment(), "rest");
                        fragmentTransaction.addToBackStack("rest");
                        fragmentTransaction.commit();
                        return true;
                    }

                    case R.id.nav_myinfo:
                    {
                        startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                        return true;
                    }

                    case R.id.nav_logout:
                    {
                        SharedPreferences sharedPreferences =getSharedPreferences("User", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Username","");
                        editor.apply();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        return true;
                    }
                    default: return false;


                }
            }
        });


    }

    @Override
    public void onSearchStateChanged(boolean b) {
        String state = b ? "enabled" : "disabled";
        Toast.makeText(HomeActivity.this, "Search " + state, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Toast.makeText(this,"Searching "+ text.toString()+" ......",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                Toast.makeText(HomeActivity.this, "Button Nav " , Toast.LENGTH_SHORT).show();
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                Toast.makeText(HomeActivity.this, "Speech " , Toast.LENGTH_SHORT).show();
        }
    }



}
