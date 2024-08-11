package com.sumerge.question;

public class Question {
    private String questionStatement;
    private final String[] choices;
    private final String correctAnswer;
    private boolean isSkipped = false;

    public Question(String questionStatement, String[] choices, String correctAnswer) {
        this.questionStatement = questionStatement;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionStatement() {
        return questionStatement;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void skipQuestion() {
        if (!isSkipped) {
            questionStatement = questionStatement + " (Skipped Question)";
            isSkipped = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(questionStatement).append(" ");
        for (String choice : choices) {
            sb.append(choice).append(" ");
        }
        sb.append(correctAnswer);
        return sb.toString();
    }
}