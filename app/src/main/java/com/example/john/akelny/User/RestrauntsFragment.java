package com.example.john.akelny.User;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestrauntsFragment extends Fragment {


    public RestrauntsFragment() {
        // Required empty public constructor
    }
    ListView listView;
    ImageView imageview;
    TextView one;
    TextView two;
    private MaterialSearchBar searchBar;
    Fragment fragment;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Resturant> resturants;
    ImageView imageview2;
    FrameLayout mainFrameLayout;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_restraunts, container, false);
        resturants= new ArrayList<Resturant>();
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading Restraunts");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        listView = (ListView) v.findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurants");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Resturant x = snapshot.getValue(Resturant.class);

                    resturants.add(x);

                }
                CustomerAdapter customerAdapter = new CustomerAdapter();
                listView.setAdapter(customerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new RestrauntsFoodFragment();
                Bundle arguments = new Bundle();
                arguments.putString( "RName" , resturants.get(position).ResuturantName);
                arguments.putString("DeliveryFees",resturants.get(position).DeliveryFees);
                fragment.setArguments(arguments);
                fragmentTransaction.replace(R.id.fragment_container, fragment, "rest");
                fragmentTransaction.addToBackStack("rest");
                fragmentTransaction.commit();

            }
        });

        return v;
    }
    private class CustomerAdapter extends BaseAdapter {


        @Override
        public int getCount() {

            return resturants.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlisthistory,null);
            imageview = convertView.findViewById(R.id.imageView);
            InputStream srt = null;
            Bitmap bitmap = null;
            try {
                srt = new URL(resturants.get(position).Logo).openStream();
                bitmap = BitmapFactory.decodeStream(srt);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageview.setImageBitmap(bitmap);
            one=(TextView) convertView.findViewById(R.id.textView);
            one.setText(resturants.get(position).ResuturantName);
            two=(TextView) convertView.findViewById(R.id.textView2);
            two.setText(String.valueOf("Delivery Fees: "+ resturants.get(position).DeliveryFees));
            imageview2 = (ImageView) convertView.findViewById(R.id.imageView2);
            imageview2.setImageDrawable(getResources().getDrawable(R.drawable.icons8));
            if(position == resturants.size()-1){
                progressDialog.dismiss();

            }

            return convertView;
        }
    }

}
