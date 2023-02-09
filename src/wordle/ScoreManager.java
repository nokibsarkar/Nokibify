package src.wordle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import src.MyLogger;

public abstract class ScoreManager {
    private final static String fileName = "src/wordle/score.ser";
    private static ArrayList<Score> scores = null;
    private static int maxScore = 0;
    private static int minScore = 0;
    private static int length = 0;
    public static ArrayList<Score> readScores() {
        if(scores != null)
            return scores;
        scores = new ArrayList<Score>();
        // read from the file
        ArrayList<Score> temp = new ArrayList<Score>();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                // Read the file into a TreeMap of students where the key is the roll number
                temp = (ArrayList<Score>) in.readObject();
                in.close();
                fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            MyLogger.log(e);
        }
        scores.addAll(temp);
        length = scores.size();
        if (length > 0) {
            maxScore = scores.get(0).getScore();
            minScore = scores.get(length - 1).getScore();
        }
        return scores;
    }
    synchronized public static int getLength() {
        return length;
    }
    synchronized public static int getMaxScore() {
        return maxScore;
    }
    synchronized public static int getMinScore() {
        return minScore;
    }
    synchronized private static void save(){
        // write to the file
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(scores);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fileName);
        } catch (IOException i) {
            MyLogger.log(i);
        }
    }
    synchronized public static void addScore(int score, String target){
        readScores();
        Score newScore = new Score(target, score);
        System.out.println(scores);
        scores.add(newScore);
        length++;
        if (score > maxScore) {
            maxScore = score;
        }
        if (score < minScore) {
            minScore = score;
        }
        save();
    }
}
