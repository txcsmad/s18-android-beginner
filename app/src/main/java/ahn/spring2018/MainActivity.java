package ahn.spring2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
    }

    private int getDiceImage(int number) {
        switch(number) {
            case 1: return R.drawable.dice1;
            case 2: return R.drawable.dice2;
            case 3: return R.drawable.dice3;
            case 4: return R.drawable.dice4;
            case 5: return R.drawable.dice5;
            case 6: return R.drawable.dice6;

            default: return R.drawable.dice1;
        }
    }


    public void rollDice(View view) {
        int roll = 1 + (int) (Math.random() * 6);

        int diceResource = getDiceImage(roll);

        imageView.setImageResource(diceResource);
    }
}
