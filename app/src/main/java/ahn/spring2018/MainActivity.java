package com.txcsmad.madspring2018;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imgDice;
    TextView tvRoundScore;

    private int currentRoundScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imgDice = findViewById(R.id.img_dice);
        this.tvRoundScore = findViewById(R.id.tv_round_score);
    }

    @DrawableRes
    private int getDiceImage(int roll) {
        switch (roll) {
            case 1:
                return R.drawable.dice1;
            case 2:
                return R.drawable.dice2;
            case 3:
                return R.drawable.dice3;
            case 4:
                return R.drawable.dice4;
            case 5:
                return R.drawable.dice5;
            case 6:
                return R.drawable.dice6;

            default:
                return R.drawable.dice1;
        }
    }


    public void rollDice(View view) {
        int randomRoll = (int) (Math.random() * 6) + 1;
        int imageRes = getDiceImage(randomRoll);

        if (randomRoll == 1) {
            this.currentRoundScore = 0;
        } else {
            this.currentRoundScore += randomRoll;
        }

        tvRoundScore.setText(String.valueOf(this.currentRoundScore));
        imgDice.setImageResource(imageRes);
    }
}
