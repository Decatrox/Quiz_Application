package com.sumerge.quiz;

import com.sumerge.enums.AnswerStatus;
import com.sumerge.mapper.AnswerStatusMapper;
import com.sumerge.question.Question;
import com.sumerge.question.QuestionReader;
import com.sumerge.user.User;
import com.sumerge.util.Config;
import com.sumerge.util.PrintUtil;

import java.io.IOException;
import java.util.*;


public class QuizManager {
    private static final char MIN_CHOICE = 'a';
    private static final char MAX_CHOICE = 'd';
    private final AnswerStatusMapper answerStatusMapper;
    private Quiz quiz;
    private final QuestionReader questionReader;
    private final Scanner scanner;
    private final PrintUtil printUtil;
    private final int waitTime = Config.getWaitTime();


    public QuizManager(AnswerStatusMapper answerStatusMapper, QuestionReader questionReader, Scanner scanner, PrintUtil printUtil) {
        this.answerStatusMapper = answerStatusMapper;
        this.questionReader = questionReader;
        this.scanner = scanner;
        this.printUtil = printUtil;
    }


    public void newQuiz(String questionBankPath, int quizSize, User user) throws InterruptedException, IOException {
        quiz = new Quiz();
        quiz.setQuizSize(quizSize);
        generateQuizQuestions(questionBankPath, quizSize);
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

    public void generateQuizQuestions(String questionBankPath, int quizSize) throws IOException {
        List<Question> allQuestions = questionReader.readFile(questionBankPath);
        Set<Integer> indices = new HashSet<>();
        Random random = new Random();

        while (indices.size() < quizSize) {
            int randomIndex = random.nextInt(allQuestions.size());
            indices.add(randomIndex);
        }

        List<Question> selectedQuestions = indices.stream()
                .map(allQuestions::get)
                .toList();

        quiz.setQuestions(selectedQuestions);
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

    public void checkAnswer(Question question) {
        String playerInput;
        do {
            playerInput = scanner.nextLine().trim().toLowerCase();
            if (isValidLetter(playerInput)) {
                playerInput = question.getChoices()[(int) playerInput.charAt(0) - 97].toLowerCase();
            }
            //skip question
            if (playerInput.equals("skip")) {
                handleSkippedQuestion(question);
            }
            //correct answer
            else if (playerInput.equals(question.getCorrectAnswer().toLowerCase())) {
                handleCorrectAnswer();
            }
            //Invalid input
            else if (!Arrays.stream(question.getChoices()).map(String::toLowerCase)
                    .toList().contains(playerInput)) {
                handleInvalidInput();
                checkAnswer(question);
            }
            //wrong answer
            else {
                handleWrongAnswer(question);
            }
        }
        while (quiz.getAnswerStatus() == AnswerStatus.INVALID);
    }

    private void handleSkippedQuestion(Question question) {
        question.skipQuestion();
        quiz.addQuestion(question);
        quiz.incrementQuizSize();
        quiz.setAnswerStatus(AnswerStatus.SKIP);
    }

    private void handleCorrectAnswer() {
        printUtil.printCorrect();
        quiz.setAnswerStatus(AnswerStatus.CORRECT);
    }

    private void handleInvalidInput() {
        printUtil.printInvalidInput();
        quiz.setAnswerStatus(AnswerStatus.INVALID);
    }

    private void handleWrongAnswer(Question question) {
        printUtil.printWrong(question.getCorrectAnswer());
        quiz.setAnswerStatus(AnswerStatus.WRONG);
    }

    private Boolean isValidLetter(String s) {
        if (s.length() == 1) {
            char c = s.charAt(0);
            return c >= MIN_CHOICE && c <= MAX_CHOICE;
        }
        return false;
    }
}
