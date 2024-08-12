package com.sumerge;

import com.sumerge.mapper.AnswerStatusMapper;
import com.sumerge.question.QuestionReader;
import com.sumerge.quiz.QuizManager;
import com.sumerge.user.User;
import com.sumerge.util.Config;
import com.sumerge.util.PrintUtil;
import com.sumerge.util.StoreUtil;

import java.awt.Desktop;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class QuizApplication
{

    public static void main( String[] args ) throws IOException, InterruptedException {
        final Scanner scanner = new Scanner(System.in);
        final AnswerStatusMapper answerStatusMapper = new AnswerStatusMapper();
        final QuestionReader questionReader = new QuestionReader();
        final PrintUtil printUtil = new PrintUtil();
        final StoreUtil storeUtil = new StoreUtil();
        final QuizManager quizManager = new QuizManager(answerStatusMapper, questionReader, scanner, printUtil);

        HashMap<String, ArrayList<Integer>> user_scores_hm = new HashMap<>();
        String questionBankPath = Config.getQuestionBankPath();
        String outputFilePath = Config.getOutputFilePath();
        int quizSize = Config.getQuizSize();
        int score;
        System.out.print("Name: ");
        String userName = scanner.nextLine();

        while (!userName.equals("exit")) {
            User user = new User(userName);
            quizManager.newQuiz(questionBankPath, quizSize, user);
            score = user.get_score();
            user_scores_hm.computeIfAbsent(userName, nothing -> new ArrayList<>()).add(score);
            int maxScore = user_scores_hm.get(userName).stream().max(Integer::compareTo).orElse(0);
            printUtil.printEndOfQuiz(userName, score, maxScore);
            storeUtil.storeScore(userName, score, outputFilePath);
            userName = scanner.nextLine();
        }

        Desktop desktop = Desktop.getDesktop();
        File file = new File(outputFilePath);
        desktop.open(file);
    }
}
