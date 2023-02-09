package src.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordBox {
    private String[] database;

    private Random randomGenerator = new Random();
    private String currentGuess;

    WordBox() {
        File f = new File("src/wordle/words.csv");
        // load the words from the file into the database
        Scanner sc;
        try {
            sc = new Scanner(f);
            String line = sc.nextLine();
            database = line.split(",");
            // System.out.println(database);
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        chooseRandomWord();
    }

    public void chooseRandomWord() {
        // choose a random word from the database
        int index = randomGenerator.nextInt(database.length);
        currentGuess = database[index].toUpperCase();
    }

    public String getCurrentGuess() {
        return currentGuess;
    }

    public String[] matchGuess(String word, String same, String position, String nonexists) {
        // return an array of colors that match the word
        // same = same letter, different position
        // position = same letter, same position
        // nonexists = letter does not exist in the word
        System.out.println("Current guess: " + currentGuess);
        System.out.println("Word: " + word);
        String[] colors = new String[word.length()];
        ArrayList<Character> guessed = new ArrayList<Character>();
        for (int i = 0; i < currentGuess.length(); i++) {
            guessed.add((Character) currentGuess.charAt(i));
        }
        for (int i = 0; i < word.length(); i++) {
            char curchar = word.charAt(i);
            if (curchar == currentGuess.charAt(i)) {
                colors[i] = same;
                guessed.remove((Character) curchar);
            }
        }
        for (int i = 0; i < word.length(); i++) {
            char curchar = word.charAt(i);
            if (curchar == currentGuess.charAt(i)) {

            } else if (guessed.contains(curchar)) {
                colors[i] = position;
                guessed.remove((Character) curchar);
            } else {
                colors[i] = nonexists;
            }
        }
        return colors;
    }
}
