package com.sumerge.util;

public class PrintUtil {
    public void printCorrect() {
        System.out.println("Correct!");
    }

    public void printWrong(String answer) {
        System.out.println("Wrong!");
        System.out.println("Correct Answer: " + answer);
    }

    public void printInvalidInput() {
        System.out.println("Invalid input, try again");
    }

    public void printScore(int score) throws InterruptedException {
        System.out.print("\n\n\n\n\n\n\n\n\n                Score: " + score);
    }

    public void printEndOfQuiz(String userName, int score, int bestScore){
        System.out.println("\n\n\n\n\n\nYour final score is: " + score);
        System.out.println("Your best score is: " + bestScore + "\n\n\n");
        System.out.println("Hope you did well " + userName);
        System.out.println("Enter the name of the next student or enter exit if everyone is done");
        System.out.print("Name: ");
    }
}
