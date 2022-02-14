package com.app.quizoofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreBoard extends AppCompatActivity {

    String score,correct,wrong;
    TextView score2,correct2,wrong2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_bord);
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.themedarkcolor));
        }

        score = getIntent().getStringExtra("points");
        score2 = findViewById(R.id.score);
        score2.setText(score+"pt");

        correct = getIntent().getStringExtra("correct");
        correct2 = findViewById(R.id.correctscore);
        correct2.setText(correct);

        wrong = getIntent().getStringExtra("wrong");
        wrong2 = findViewById(R.id.wrongscore);
        wrong2.setText(wrong);



    }
}