package lab5;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

class AAquiz {
    private static final String[] SHORT_NAMES = { 
        "A", "R", "N", "D", "C", "Q", "E", 
        "G", "H", "I", "L", "K", "M", "F", 
        "P", "S", "T", "W", "Y", "V" 
    };

    private static final String[] FULL_NAMES = { 
        "alanine", "arginine", "asparagine", 
        "aspartic acid", "cysteine", "glutamine", 
        "glutamic acid", "glycine", "histidine", 
        "isoleucine", "leucine", "lysine", 
        "methionine", "phenylalanine", "proline", 
        "serine", "threonine", "tryptophan", 
        "tyrosine", "valine" 
    };

    private int score;
    private String correct;
    private Random random;
    private long endTime;
    int currentIndex;

    public AAquiz() {
        random = new Random();
        score = 0;
        currentIndex = -1; // Initialize current index
    }

    public void resetScore() {
        score = 0;
        endTime = System.currentTimeMillis() + 30 * 1000; // Set time limit to 30 seconds
    }

    public String getNextQuestion() {
        currentIndex = random.nextInt(SHORT_NAMES.length); // Update current index
        correct = SHORT_NAMES[currentIndex];
        return "What is the one letter code for " + FULL_NAMES[currentIndex] + "?";
    }

    public boolean checkAnswer(String userAnswer) {
        if (userAnswer.equals(correct)) {
            score++;
            return true;
        }
        return false;
    }

    public String getCorrectAnswer() {
        return correct; // Return the correct abbreviation
    }

    public int getScore() {
        return score; // Return the current score
    }

    public boolean isTimeUp() {
        return System.currentTimeMillis() > endTime; // Check if the time is up
    }
}