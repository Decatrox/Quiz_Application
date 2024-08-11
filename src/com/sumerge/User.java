package com.sumerge;//• com.sumerge.User: Represents a user taking the quiz. Should have attributes for the user's name and methods to record their answers and track their progress.

//if there is time add a recap after the quiz displaying mistakes.
public class User {
    private final String name;
    private int score;
//    Scanner sc = new Scanner(System.in);  // Create a Scanner object


    public User(String name) {
        this.name = name;
    }

    public void update_score (int n) {
        if (n > 0 ) add_point();
    }

    public void add_point () {
        score++;
    }

    public int get_score () {
        return score;
    }


}
