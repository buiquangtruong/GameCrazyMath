package com.truongkl.gamecrazymath;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Truong KL on 5/23/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtScore;
    private TextView txtTime;
    private TextView txtQuestion;
    private ImageView imgTrue;
    private ImageView imgFalse;

    private int num1;
    private int num2;
    private int trueAnswer;
    private int answer;
    private int score;
    private int time;
    private Timer timer;
    private boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComs();
    }

    private void initComs() {
        txtScore = (TextView) findViewById(R.id.txt_score);
        txtTime = (TextView) findViewById(R.id.txt_time);
        txtQuestion = (TextView) findViewById(R.id.txt_question);
        imgTrue = (ImageView) findViewById(R.id.img_true);
        imgFalse = (ImageView) findViewById(R.id.img_false);

        score = 0;
        time = 5;
        gameOver = false;
        makeQuestion();

        imgTrue.setOnClickListener(this);
        imgFalse.setOnClickListener(this);

        timer = new Timer();
        timer.execute();
    }

    @Override
    public void onClick(View v) {
        if (!gameOver){
            switch (v.getId()) {
                case (R.id.img_true):
                    checkQuestion(v);
                    break;

                case (R.id.img_false):
                    checkQuestion(v);
                    break;

                default:
                    break;
            }
        }

    }

    private void makeQuestion() {
        Random r = new Random();

        num1 = r.nextInt(100);
        num2 = r.nextInt(100);
        trueAnswer = num1 + num2;
        if (num1 % 2 == 0) {
            answer = trueAnswer;
        } else {
            answer = trueAnswer + r.nextInt(5) + 1;
        }

        txtQuestion.setText(num1 + " + " + num2 + " = " + answer);
    }

    private void checkQuestion(View view) {
        if (view.getId() == R.id.img_true) {
            if (answer == trueAnswer) {
                score++;
                txtScore.setText("Your Score: " + score);
                makeQuestion();

                time = 5;
                Toast.makeText(this, "Exactly!", Toast.LENGTH_SHORT).show();
            } else {
                gameOver = true;
                //Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show();
        }
        } else if (view.getId() == R.id.img_false) {
            if (answer != trueAnswer) {
                score++;
                txtScore.setText("Your Score: " + score);
                makeQuestion();

                time=5;
                Toast.makeText(this, "Exactly!", Toast.LENGTH_SHORT).show();
            } else {
                gameOver = true;
                //Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class Timer extends AsyncTask<Void, Integer, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            while (time >=0 && !gameOver){
                publishProgress(time);
                time--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            txtTime.setText("Time: " + String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            gameOver = true;
            Toast.makeText(MainActivity.this, "You Lose!", Toast.LENGTH_SHORT).show();
        }
    }

}
