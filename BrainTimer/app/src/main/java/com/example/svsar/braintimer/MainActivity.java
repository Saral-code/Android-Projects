package com.example.svsar.braintimer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
public class MainActivity extends AppCompatActivity {
    int correctAnswer;
    int total;
    int wins;
    CountDownTimer countDownTimer= new CountDownTimer(11000,1000) {
        @Override
        public void onTick(long l) {
            int second = (int)l/1000;
            TextView timer = (TextView) findViewById(R.id.timer);
            timer.setText(Integer.toString(second)+"s");

        }

        @Override
        public void onFinish() {
            TextView result =(TextView)findViewById(R.id.result);
            result.setText("Time's Up !");
            refreshing();
            updateScore(wins);
        }
    };
    boolean temp=false;
    Button button1,button2,button3,button4;
    public int generateNum(int max,int min){
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }
    public void updateScore(int wins){
        total++;
        TextView record = (TextView) findViewById(R.id.record);
        record.setText(Integer.toString(wins)+"/"+Integer.toString(total));

    }
    public void refreshing(){
        if (temp){
            countDownTimer.cancel();
        }
        temp=true;

        Button button1= (Button) findViewById(R.id.button1);
        Button button2= (Button) findViewById(R.id.button2);
        Button button3= (Button) findViewById(R.id.button3);
        Button button4= (Button) findViewById(R.id.button4);
        TextView question = (TextView) findViewById(R.id.question);
        int one= generateNum(200,0);
        int two= generateNum(200,0);
        correctAnswer= one+two;
        int correctButton = generateNum(4,1);
        question.setText(Integer.toString(one) + "+" + Integer.toString(two));
        if (correctButton==1) {
            button1.setText(Integer.toString(correctAnswer));
            button2.setText(Integer.toString(generateNum(200, 0)));
            button3.setText(Integer.toString(generateNum(200, 0)));
            button4.setText(Integer.toString(generateNum(200, 0)));
        }
        else if (correctButton==2) {
            button2.setText(Integer.toString(correctAnswer));
            button1.setText(Integer.toString(generateNum(200, 0)));
            button3.setText(Integer.toString(generateNum(200, 0)));
            button4.setText(Integer.toString(generateNum(200, 0)));
        }
        else if (correctButton==3) {
            button3.setText(Integer.toString(correctAnswer));
            button2.setText(Integer.toString(generateNum(200, 0)));
            button1.setText(Integer.toString(generateNum(200, 0)));
            button4.setText(Integer.toString(generateNum(200, 0)));
        }
        else if (correctButton==4) {
            button4.setText(Integer.toString(correctAnswer));
            button2.setText(Integer.toString(generateNum(200, 0)));
            button3.setText(Integer.toString(generateNum(200, 0)));
            button1.setText(Integer.toString(generateNum(200, 0)));
        }
    countDownTimer.start();
    }

    public void check(View view){
        Button button = (Button) view;
        TextView result =(TextView)findViewById(R.id.result);
        int tappedButton = Integer.parseInt(button.getTag().toString());
        int buttonText =Integer.parseInt(button.getText().toString());
        //Log.i("info",button.getText().toString());

        if (buttonText==correctAnswer){
            wins++;
            result.setText("Right !");

        }
        else {
            result.setText("Wrong !");
        }
        result.setVisibility(View.VISIBLE);
        refreshing();
        updateScore(wins);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshing();
    }
}
