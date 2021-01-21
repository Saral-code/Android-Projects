package com.example.svsar.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class notes_edit extends AppCompatActivity {
    EditText editText;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);
        intent = getIntent();
        String data = intent.getStringExtra("data") ;
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(data);
    }

    @Override
    public void onBackPressed() {
        int index =intent.getIntExtra("index",-1);
        MainActivity.notes.remove(index);
        MainActivity.notes.add(index,editText.getText().toString());

        super.onBackPressed();
    }
}
