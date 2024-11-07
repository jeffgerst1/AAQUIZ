package lab5;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class guiquiz extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField answerField = new JTextField();
    private JTextArea outputArea = new JTextArea();
    private JButton quizStartButton = new JButton("Start 30 Second Amino Acid Quiz");
    private JLabel questionLabel = new JLabel("  Press 'Start' to begin the quiz");
    private JLabel timerLabel = new JLabel("Time remaining: 30 seconds");
    private AAquiz quiz;
    private int timeRemaining = 30;  // Quiz duration in seconds
    private Timer timer;
    private int correctCount = 0;
    private int totalQuestionsAsked = 0;

    public guiquiz() {
        super("Amino Acid Single-letter Abbreviation Quiz");
        quiz = new AAquiz();
        setLocationRelativeTo(null);
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Set up output area with auto-scrolling
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Set up question and timer labels with larger font
        Font labelFont = new Font("SansSerif", Font.BOLD, 16); // Change size as desired
        questionLabel.setFont(labelFont);
        timerLabel.setFont(labelFont);

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.add(timerLabel, BorderLayout.WEST);
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        getContentPane().add(questionPanel, BorderLayout.NORTH);

        // Set up answer input field and start button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(answerField, BorderLayout.CENTER);
        inputPanel.add(quizStartButton, BorderLayout.EAST);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // Set up ActionListener for start button
        quizStartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startTimedQuiz();
            }
        });

        setVisible(true);
    }

    private void startTimedQuiz() {
        quiz.resetScore();
        correctCount = 0;
        totalQuestionsAsked = 0;
        timeRemaining = 30;
        outputArea.setText("");
        timerLabel.setText("Time remaining: " + timeRemaining + " seconds");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (timeRemaining > 0) {
                        timerLabel.setText("Time remaining: " + timeRemaining + " seconds  ");
                        timeRemaining--;
                    } else {
                        endQuiz();
                    }
                });
            }
        }, 0, 1000);

        quizStartButton.setEnabled(false);
        answerField.setEnabled(true);
        displayNextQuestion(); // Start the first question
    }

    private void displayNextQuestion() {
        if (timeRemaining <= 0) {
            endQuiz();
            return;
        }

        String question = quiz.getNextQuestion();
        questionLabel.setText("Question " + (totalQuestionsAsked + 1) + ": " + question);
        totalQuestionsAsked++;

        // Clear previous ActionListeners to avoid duplicates
        for (ActionListener al : answerField.getActionListeners()) {
            answerField.removeActionListener(al);
        }

        answerField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userAnswer = answerField.getText().trim().toUpperCase();
                answerField.setText("");

                if (quiz.checkAnswer(userAnswer)) {
                    correctCount++;
                    outputArea.append("Correct! Score: " + correctCount + "/" + totalQuestionsAsked + "\n");
                } else {
                    outputArea.append("Incorrect. The correct answer was " + quiz.getCorrectAnswer() + ". Score: " + correctCount + "/" + totalQuestionsAsked + "\n");
                }

                displayNextQuestion(); // Ask the next question after answering
            }
        });
    }

    private void endQuiz() {
        timer.cancel(); // Cancel the timer
        timerLabel.setText("Time remaining: 0 seconds");
        outputArea.append("Time's up! Final Score: " + correctCount + " out of " + totalQuestionsAsked + "\n");
        quizStartButton.setEnabled(true);
        questionLabel.setText("  Quiz finished!");
        answerField.setEnabled(false); // Disable input
    }

    public static void main(String[] args) {
        new guiquiz();
    }
}
