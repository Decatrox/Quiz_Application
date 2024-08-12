package com.sumerge.util;

public class PrintUtil {
    public void printCorrect() {
        System.out.println("Correct!\n");
    }

    public void printWrong(String correctAnswer) {
        System.out.printf("Wrong!\nCorrect Answer: %s\n\n", correctAnswer);
    }

    public void printInvalidInput() {
        System.out.println("Invalid input, try again.\n");
    }

    public void printScore(int score) {
        System.out.printf("\n\n\n\n\n\n\n\n\n                Score: %d\n", score);
    }

    public void printEndOfQuiz(String userName, int score, int bestScore) {
        System.out.printf("""






                        Your final score is: %d
                        Your best score is: %d


                        Hope you did well, %s

                        Enter the name of the next student or enter 'exit' if everyone is done.
                        Name:\s"""
                , score, bestScore, userName);
    }
}
