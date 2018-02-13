package ahn.spring2018;

import android.os.AsyncTask;

/**
 * Created by Jacob on 2/12/18.
 */

public class ScarnesDice {

    private GameCallback callback;
    private int player1Score;
    private int player2Score;
    private int roundScore;
    private boolean isPlayer1Turn;
    private boolean isReset = true;

    public ScarnesDice(GameCallback callback) {
        this.callback = callback;
        reset();
    }

    public void humanTurn(boolean hold) {
        if (hold) {
            changeRound(true);
        } else {
            roll();
        }
    }

    private void computerTurn() {
        boolean canRollAgain = roll();
        if (!canRollAgain) {
            changeRound(false);
            return;
        }

        boolean willHold = this.roundScore > 50 * Math.random();
        new ComputerTask(willHold, new ComputerTask.ComputerCallback() {
            @Override
            public void didFinishWaiting(boolean didHold) {
                if (ScarnesDice.this.isReset) {
                    return;
                }

                if (didHold) {
                    ScarnesDice.this.changeRound(true);
                } else {
                    ScarnesDice.this.computerTurn();
                }
            }
        }).execute();
    }

    public void reset() {
        this.player1Score = 0;
        this.player2Score = 0;
        this.roundScore = 0;
        this.isPlayer1Turn = true;
        this.isReset = true;

        callback.onResetGame();
    }

    private void changeRound(boolean keepScore) {
        if (keepScore) {
            if (isPlayer1Turn) {
                this.player1Score += this.roundScore;
            } else {
                this.player2Score += this.roundScore;
            }
        }

        int newScore = isPlayer1Turn ? player1Score : player2Score;
        callback.onPlayerScoreChanged(isPlayer1Turn, newScore);

        this.roundScore = 0;
        this.isPlayer1Turn = !isPlayer1Turn;

        callback.turnEnded(isPlayer1Turn);

        if (!isPlayer1Turn) {
            computerTurn();
        }
    }

    private boolean roll() {
        this.isReset = false;

        int roll = 1 + (int) (Math.random() * 6);
        if (roll == 1) {
            this.roundScore = 0;
            changeRound(false);
        } else {
            this.roundScore += roll;
        }

        callback.onDiceRolled(roll, roundScore);
        return roll != 1;
    }

    public interface GameCallback {
        void turnEnded(boolean isHumanTurn);
        void onDiceRolled(int roll, int roundScore);
        void onPlayerScoreChanged(boolean human, int newScore);
        void onResetGame();
    }

    private static class ComputerTask extends AsyncTask<Void, Void, Void> {

        private boolean willHold;
        private ComputerCallback callback;

        ComputerTask(boolean willHold, ComputerCallback callback) {
            this.willHold = willHold;
            this.callback = callback;
        }

        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            this.callback.didFinishWaiting(willHold);
        }

        interface ComputerCallback {
            void didFinishWaiting(boolean didHold);
        }
    }
}
