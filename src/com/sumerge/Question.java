package com.sumerge;//change choices to be int or char

public class Question {
    private String question;
    private final String[] choices;
    private final String answer;
    private boolean skipped_already = false;

    public Question(String question, String[] choices, String answer) {
        this.question = question;
        this.choices = choices;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void make_repeated () {
        if (!skipped_already) {
            question = question + " (Skipped Question)";
            skipped_already = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(question).append(" ");
        for (String choice : choices) {
            sb.append(choice).append(" ");
        }
        sb.append(answer);
        return sb.toString();
    }
}
