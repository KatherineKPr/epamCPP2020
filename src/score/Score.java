package score;

import javafx.scene.Parent;
import javafx.scene.control.Label;

public class Score {
    public static final int TARGET_SCORE = 7000;
    protected int score = 0;

    public void getTargetScore(Parent root) {
        Label targetScore = (Label) root.lookup("#targetScore");
        targetScore.setText(String.valueOf(TARGET_SCORE));
    }

    public void updateScore(Parent root) {
        Label gameScore = (Label) root.lookup("#gameScore");
        gameScore.setText(String.valueOf(score));
    }

    public void incrementScore() {
        score++;
    }


}