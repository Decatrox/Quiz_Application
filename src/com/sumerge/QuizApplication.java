package com.sumerge;

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
        Scanner sc = new Scanner(System.in);
        HashMap<String, ArrayList<Integer>> user_scores_hm = new HashMap<>();
        QuizManager quizManager = new QuizManager();
        String questionBankPath = Config.getQuestionBankPath();
        String outputFilePath = Config.getOutputFilePath();
        PrintUtil printUtil = new PrintUtil();
        StoreUtil storeUtil = new StoreUtil();
        int quizSize = Config.getQuizSize();
        System.out.print("Name: ");
        String userName = sc.nextLine();
        int score;

        while (!userName.equals("exit")) {
            User user = new User(userName);
            quizManager.newQuiz(questionBankPath, quizSize, user);
            score = user.get_score();
            user_scores_hm.computeIfAbsent(userName, nothing -> new ArrayList<>()).add(score);
            int maxScore = user_scores_hm.get(userName).stream().max(Integer::compareTo).orElse(0);
            printUtil.printEndOfQuiz(userName, score, maxScore);
            storeUtil.storeScore(userName, score, outputFilePath);
            userName = sc.nextLine();
        }

        Desktop desktop = Desktop.getDesktop();
        File file = new File(outputFilePath);
        desktop.open(file);
    }
}
