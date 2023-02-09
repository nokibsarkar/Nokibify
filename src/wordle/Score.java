package src.wordle;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {
    public Date date;
    public int score;
    public String target;
    public Score(String target, int score) {
        this.target = target;
        this.score = score;
        date = new Date();
    }
    public Date getDate() {
        return date;
    }
    public int getScore() {
        return score;
    }
    public String getTarget() {
        return target;
    }
}
