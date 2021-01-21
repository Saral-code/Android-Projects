package com.example.svsar.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    public void displayText(View view) throws UnsupportedEncodingException {
        DownloadTask task = new DownloadTask();
        String encodedCityName="";
        EditText editView = (EditText) findViewById(R.id.editText);
        try {
            // it converts cities/text with space to URL acceptable format
            encodedCityName = URLEncoder.encode(editView.getText().toString(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        task.execute("https://openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid=439d4b804bc8187953eb36d2a8c26a02");
//this is done to hide the keyboard when we click in the button
        InputMethodManager mgr =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editView.getWindowToken(),0);


    }

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection= null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data!=-1){
                    char current = (char) data;
                    result+=current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                TextView textView = (TextView) findViewById(R.id.textView);
                JSONObject jsonObject=new JSONObject(s);
                String weatherInfo= jsonObject.getString("weather");
                JSONObject tempInfo= jsonObject.getJSONObject("main");
                Log.i("weather",weatherInfo);
                Log.i("temp",tempInfo.getString("temp"));
                JSONArray arr= new JSONArray(weatherInfo);
                String message="";
                for (int i=0;i<arr.length();i++){
                    JSONObject object= arr.getJSONObject(i);
                    String main =object.getString("main");
                    String description =  object.getString("description");
                    if ((!main.equals("")) && (!description.equals(""))){
                        message=message + main + " : " + description +"\n";
                    }
                }
                message = message + "Temperature : "+ tempInfo.getString("temp");
                textView.setText(message);

            } catch (Exception e) {
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("NO CITY FOUND");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
