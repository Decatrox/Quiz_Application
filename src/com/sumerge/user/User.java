package com.sumerge.user;

//if there is time add a recap after the quiz displaying mistakes.
public class User {
    private int score;


    public User(String name) {
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
