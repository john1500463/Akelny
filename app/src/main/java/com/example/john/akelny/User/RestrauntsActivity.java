package com.example.john.akelny.User;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.akelny.Admin.AddNewFood;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.example.john.akelny.R.drawable.icons8;

public class RestrauntsActivity extends Activity {
    ListView listView;
    ImageView imageview;
    TextView one;
    TextView two;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Resturant>resturants;
    ImageView imageview2;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunts);

        progressDialog = new ProgressDialog(RestrauntsActivity.this);
        progressDialog.setTitle("Loading Resturants");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        resturants= new ArrayList<Resturant>();
        listView = (ListView) findViewById(R.id.listview);
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
            }
        });

    }

    private class CustomerAdapter extends BaseAdapter{


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

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
            two.setText(String.valueOf(resturants.get(position).DeliveryFees));
            imageview2 = (ImageView) convertView.findViewById(R.id.imageView2);
            imageview2.setImageDrawable(getResources().getDrawable(R.drawable.icons8));

            if(position == resturants.size()-1){

                progressDialog.dismiss();
            }


            return convertView;
        }
    }
}
