package com.example.svsar.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Intent intenti;
    static ArrayList<String> places;
    static ArrayList<String> raw;
    ArrayAdapter arrayAdapter;
    LocationManager locationManager;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences =this.getSharedPreferences("com.example.svsar.memorableplaces", Context.MODE_PRIVATE);

        places = new ArrayList<String>();
        raw = new ArrayList<String>();
        try {
            places =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            raw =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("raw",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView =(ListView) findViewById(R.id.listview);
        if (places.size()==0){
        places.add("Add a place..");}
        //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("dkmddkldk",Integer.toString(i));
                if (i==0) {
                    intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
                else {
                   intenti = new Intent(getApplicationContext(), MapsActivity2.class);
                    intenti.putExtra("index",i);
                    startActivity(intenti);
                }

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        try {
            sharedPreferences.edit().putString("places",ObjectSerializer.serialize(places)).apply();
            sharedPreferences.edit().putString("raw",ObjectSerializer.serialize(raw)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

        arrayAdapter.notifyDataSetChanged();
    }


}
