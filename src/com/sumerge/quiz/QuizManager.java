package com.sumerge.quiz;

import com.sumerge.enums.AnswerStatus;
import com.sumerge.mapper.AnswerStatusMapper;
import com.sumerge.question.Question;
import com.sumerge.question.QuestionReader;
import com.sumerge.user.User;
import com.sumerge.util.Config;
import com.sumerge.util.PrintUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class QuizManager {
    private final AnswerStatusMapper answerStatusMapper = new AnswerStatusMapper();
    private Quiz quiz;
    private final QuestionReader questionReader = new QuestionReader();
    private final Scanner scanner = new Scanner(System.in);
    private final PrintUtil printUtil = new PrintUtil();
    private final int waitTime = Config.getWaitTime();


    public void newQuiz(String questionBankPath, int quizSize, User user) throws InterruptedException, IOException {
        quiz = new Quiz();
        quiz.setQuizSize(quizSize);
        generateQuiz(questionBankPath, quizSize);
        displayNextQuestion();

        while (!quiz.getAnswerStatus().equals(AnswerStatus.STOP)) {
            user.update_score(answerStatusMapper.mapStatus(quiz.getAnswerStatus()));
            waitBetweenQuestions(waitTime);
            printUtil.printScore(user.get_score());
            displayNextQuestion();
        }
    }

    private void waitBetweenQuestions(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void generateQuiz(String questionBankPath, int quizSize) throws IOException {
        List<Question> questionsList = questionReader.readFile(questionBankPath);
        Collections.shuffle(questionsList);
        questionsList = questionsList.subList(0, quizSize);
        quiz.setQuestions(questionsList);
    }

    public void displayNextQuestion() {
        if (quiz.currentQuestionNumber < quiz.getQuizSize()) {
            Question question = quiz.getCurrentQuestion();
            System.out.print(question);
            quiz.incrementCurrentQuestionNumber();
            checkAnswer(question);
        }
        else {
            quiz.setAnswerStatus(AnswerStatus.STOP);
        }
    }

    public AnswerStatus checkAnswer(Question question) {
        String playerInput = scanner.nextLine().trim().toLowerCase();
        if (isValidLetter(playerInput)){
            playerInput = question.getChoices()[(int) playerInput.charAt(0) - 97].toLowerCase();
        }

        if (playerInput.equals("skip")) {
            question.skipQuestion();
            quiz.addQuestion(question);
            quiz.incrementQuizSize();
            quiz.setAnswerStatus(AnswerStatus.SKIP);
        }
        else if (playerInput.equals(question.getCorrectAnswer().toLowerCase())){
            printUtil.printCorrect();
            quiz.setAnswerStatus(AnswerStatus.CORRECT);
        }
        else if (!Arrays.stream(question.getChoices())
                .map(String::toLowerCase)
                .toList()
                .contains(playerInput)){
            printUtil.printInvalidInput();
            quiz.setAnswerStatus(AnswerStatus.INVALID);
            return checkAnswer(question);
        }
        else{
            printUtil.printWrong(question.getCorrectAnswer());
            quiz.setAnswerStatus(AnswerStatus.WRONG);
        }

        return quiz.getAnswerStatus();
    }

    private Boolean isValidLetter(String s) {
        if (s.length() == 1) {
            char c = s.charAt(0);
            return c >= 'a' && c <= 'd';
        }
        return false;
    }
}
