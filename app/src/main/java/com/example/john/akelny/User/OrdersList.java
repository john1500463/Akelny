package com.example.john.akelny.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.akelny.R;

public class OrdersList extends Activity {
    ListView listView;
    ImageView imageview;
    TextView one;
    TextView two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        listView = (ListView) findViewById(R.id.listview);
        CustomerAdapter customerAdapter = new CustomerAdapter();
        listView.setAdapter(customerAdapter);

    }

    private class CustomerAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 10;
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
            imageview.setImageResource(R.drawable.mage);
            one=(TextView) convertView.findViewById(R.id.textView);
            one.setText(String.valueOf(position));


            return convertView;
        }
    }
}
