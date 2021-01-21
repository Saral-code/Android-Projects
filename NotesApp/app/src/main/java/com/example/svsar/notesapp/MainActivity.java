package com.example.svsar.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static  ArrayList<String> notes;
    ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences ;
    int temp;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.adding :
                notes.add("New Note");
                arrayAdapter.notifyDataSetChanged();
                try {
                    sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(notes)).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return false;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.svsar.notesapp", Context.MODE_PRIVATE);

        ListView listView = (ListView) findViewById(R.id.listview);
        notes = new ArrayList<String>();
        if (notes.size()==0) {
            notes.add("Example Note");
        }
        try {
            notes =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }


        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),notes_edit.class);
                intent.putExtra("data",notes.get(i).toString());
                intent.putExtra("index",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                temp =i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(temp);
                                arrayAdapter.notifyDataSetChanged();
                                try {
                                    sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(notes)).apply();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

        }

    @Override
    protected void onResume() {
        arrayAdapter.notifyDataSetChanged();
        try {
            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(notes)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }
}
