package score;

import javafx.scene.Parent;
import javafx.scene.control.Label;

public class Score {

    protected static final int TARGET_SCORE = 100;
    public int score = 0;

    public void getTargetScore(Parent root) {
        Label targetScore = (Label) root.lookup("#targetScore");
        targetScore.setText(String.valueOf(TARGET_SCORE));
    }

    public void updateScore(Parent root) {
        Label gameScore = (Label) root.lookup("#gameScore");
        gameScore.setText(String.valueOf(score));
    }

    public void updateReplayScore(Parent root, String score) {
        Label gameScore = (Label) root.lookup("#gameScore");
        gameScore.setText(String.valueOf(score));
    }

    public void incrementScore() {
        score++;
        if (score>TARGET_SCORE){
            score=TARGET_SCORE;
        }
    }

    public static int getTargetScore() {
        return TARGET_SCORE;
    }

    public int getScore() {
        return score;
    }
    public String getStringScore() {
        String stringScore="";
        stringScore+=score;
        return stringScore;
    }
}
