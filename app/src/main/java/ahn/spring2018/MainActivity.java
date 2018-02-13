package ahn.spring2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ScarnesDice.GameCallback {

    private TextView tvPlayer1Score;
    private TextView tvPlayer2Score;
    private TextView tvRoundScore;
    private TextView tvCurrentPlayer;
    private ImageView imageView;
    private ScarnesDice diceGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tvPlayer1Score = findViewById(R.id.tv_player1_score);
        this.tvPlayer2Score = findViewById(R.id.tv_player2_score);
        this.tvRoundScore = findViewById(R.id.tv_round_score);
        this.tvCurrentPlayer = findViewById(R.id.tv_current_player);
        this.imageView = findViewById(R.id.image_view);

        this.diceGame = new ScarnesDice(this);
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

    @Override
    public void turnEnded(boolean isHumanTurn) {
        if (isHumanTurn) {
            tvCurrentPlayer.setText("Human Turn");
            findViewById(R.id.btn_roll).setEnabled(true);
            findViewById(R.id.btn_hold).setEnabled(true);
        } else {
            tvCurrentPlayer.setText("Computer Turn");
            findViewById(R.id.btn_roll).setEnabled(false);
            findViewById(R.id.btn_hold).setEnabled(false);
        }
    }

    @Override
    public void onDiceRolled(int roll, int roundScore) {
        int diceResource = getDiceImage(roll);
        imageView.setImageResource(diceResource);
        tvRoundScore.setText(String.valueOf(roundScore));
    }

    @Override
    public void onPlayerScoreChanged(boolean human, int newScore) {
        if (human) {
            this.tvPlayer1Score.setText(String.valueOf(newScore));
        } else {
            this.tvPlayer2Score.setText(String.valueOf(newScore));
        }
    }

    @Override
    public void onResetGame() {
        tvCurrentPlayer.setText("Player 1");
        tvRoundScore.setText(String.valueOf(0));
        tvPlayer1Score.setText(String.valueOf(0));
        tvPlayer2Score.setText(String.valueOf(0));

        findViewById(R.id.btn_roll).setEnabled(true);
        findViewById(R.id.btn_hold).setEnabled(true);
    }

    public void rollDice(View view) {
        diceGame.humanTurn(false);
    }

    public void holdScore(View view) {
        diceGame.humanTurn(true);
    }

    public void resetGame(View view) {
        diceGame.reset();
    }
}
