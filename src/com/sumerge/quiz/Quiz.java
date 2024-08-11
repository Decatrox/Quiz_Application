package com.sumerge.quiz;

import com.sumerge.enums.AnswerStatus;
import com.sumerge.question.Question;

import java.util.ArrayList;
import java.util.List;


public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private int quizSize;
    private AnswerStatus answerStatus;
    int currentQuestionNumber;


    public Question getCurrentQuestion() {
        return questions.get(currentQuestionNumber);
    }

    public void incrementCurrentQuestionNumber() {
        currentQuestionNumber++;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void incrementQuizSize() {
        quizSize++;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getQuizSize() {
        return quizSize;
    }

    public void setQuizSize(int quizSize) {
        this.quizSize = quizSize;
    }

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }

}
