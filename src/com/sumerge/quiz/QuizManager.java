package com.sumerge.quiz;

import com.sumerge.enums.AnswerStatus;
import com.sumerge.mapper.AnswerStatusMapper;
import com.sumerge.question.Question;
import com.sumerge.question.QuestionReader;
import com.sumerge.user.User;
import com.sumerge.util.PrintUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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


    public void newQuiz(String questionBankPath, int quizSize, User user) throws InterruptedException, IOException {
        quiz = new Quiz();
        quiz.setQuizSize(quizSize);
        generateQuiz(questionBankPath, quizSize);
        quiz.setAnswerStatus(displayNextQuestion());

        while (!quiz.getAnswerStatus().equals(AnswerStatus.STOP)) {
            user.update_score(answerStatusMapper.mapStatus(quiz.getAnswerStatus()));
            wait_for_read(1000);
            printUtil.printScore(user.get_score());
            quiz.setAnswerStatus(displayNextQuestion());
        }
    }



    private void wait_for_read (int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void generateQuiz(String questionBankPath, int quizSize) throws IOException {
        List<Question> questionsList = questionReader.readFile(questionBankPath);
        Collections.shuffle(questionsList);
        questionsList = questionsList.subList(0, quizSize);
        quiz.setQuestions(questionsList);
    }


    public void addQuestions(String filename, String question, String option1, String option2, String option3, String option4, String answer) throws IOException {
        try (Writer output = new BufferedWriter(new FileWriter(filename, true))) {
            String formattedQuestion = String.join(question, option1, option2, option3, option4);
            output.append(formattedQuestion).append("\n");
        }
    }


    public AnswerStatus displayNextQuestion() {
        if (quiz.currentQuestionNumber < quiz.getQuizSize()) {
            Question question = quiz.getCurrentQuestion();
            displayQuestion(question);
            quiz.incrementCurrentQuestionNumber();
            return checkAnswer(question);
        } else return AnswerStatus.STOP;
    }


    private void displayQuestion(Question question) {
        System.out.println("\n" + question.getQuestionStatement());
        String[] choices = question.getChoices();
        char option = 'a';

        for (String choice : choices) {
            System.out.println(option + ". " + choice);
            option++;
        }

        System.out.print("Answer: ");
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
