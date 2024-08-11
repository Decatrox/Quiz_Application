package com.sumerge;

import java.awt.*;
import java.io.*;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class QuizApplication
{


    public static int new_quiz (String name, String question_bank_path) throws InterruptedException {
        Quiz quiz = new Quiz();
        User user = new User(name);
        quiz.generate_quiz(question_bank_path);
        int x = quiz.display_next_question();

        while (x != -1) {
            user.update_score(x);
            wait_for_read(1000);
            print_score(user.get_score());
            x = quiz.display_next_question();
        }
        return user.get_score();
    }

    public static void store_score (String name, int score, String out_path) throws IOException {
        Writer output = new BufferedWriter(new FileWriter(out_path, true));
        StringBuilder sb = new StringBuilder();

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(formatter);

        sb.append(name).append(", ").append(score).append(", ").append(formattedTime);
        output.append(String.valueOf(sb)).append("\n");
        output.close();
    }

    private static void print_score(int score) throws InterruptedException {
//        clearConsole();
        System.out.print("\n\n\n\n\n\n\n\n\n        Score: " + score);
    }

    //chnage it to enter?
    private static void wait_for_read (int time) throws InterruptedException {
        Thread.sleep(time);
    }

//    private static void clearConsole() {
//        // ANSI escape code to clear the screen and move the cursor to (0,0)
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//    }

    public static void main( String[] args ) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        HashMap<String, ArrayList<Integer>> user_scores_hm = new HashMap<>();

        String fp = "/Users/decatrox/OOP_Task_Java/src/resources/questions.txt";
        String out_path = "/Users/decatrox/OOP_Task_Java/src/resources/scores.csv";
        System.out.print("Name: ");
        String name = sc.nextLine();
        int score;

        while (!name.equals("exit")) {
            score = new_quiz(name, fp);
            if (user_scores_hm.containsKey(name)) {
                ArrayList<Integer> scores = new ArrayList<>(user_scores_hm.get(name));
                scores.add(score);
                user_scores_hm.put(name, scores);
            }
            System.out.println("\n\n\n\n\n\nYour final score is: " + score + "\n\n\n");
            store_score(name, score, out_path);
            System.out.println("Hope you did well " + name);
            //recap goes here if there is time
            System.out.println("Enter the name of the next student or enter exit if everyone is done");
            System.out.print("Name: ");
            name = sc.nextLine();
        }

        Desktop desktop = Desktop.getDesktop();
        File file = new File(out_path);
        desktop.open(file);





//        com.sumerge.Quiz quiz = new com.sumerge.Quiz(4, 20);
//        com.sumerge.Quiz quiz = new com.sumerge.Quiz();
//        com.sumerge.User user = new com.sumerge.User("Omar");
//        quiz.generate_quiz(fp);
//        int x = quiz.display_next_question();
//
//        while (x != -1) {
//            user.update_score(x);
//            x = quiz.display_next_question();
//        }
//
//        System.out.println(user.get_score());
//        com.sumerge.QuestionReader qqr = new com.sumerge.QuestionReader();
//        com.sumerge.Question q = qqr.createQuestionFromLine("What is the capital of France?;Paris;London;Berlin;Madrid;Paris");
//        System.out.println(q.toString());

//        List<com.sumerge.Question> lq = qqr.readFile("/Users/decatrox/OOP_Task/src/main/resources/questions.txt");
//        for (com.sumerge.Question q : lq) {
//            System.out.println(q);
//        }
//        System.out.println(System.getProperty("user.dir"));
    }
}
