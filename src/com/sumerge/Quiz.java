package com.sumerge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


//com.sumerge.Quiz: Represents a collection of questions. Should have methods to
// add questions, display questions, and calculate the score based on user responses.
public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private List<Integer> rands;
    private int idx = 0;
    private int total;
    private int score;
    private int test_size = 5;
    Scanner sc = new Scanner(System.in);  // Create a Scanner object


    //add a number at a file to store number of questions between runs or just count the lines.
    public Quiz () {
        GenerateRandoms gr = new GenerateRandoms();
        rands = gr.generate(test_size, 20);
        total = test_size;
    }

    public Quiz (int desired_size, int number_of_options) {
        GenerateRandoms gr = new GenerateRandoms();
        rands = gr.generate(desired_size, number_of_options);
        total = desired_size;
    }

    public void generate_quiz (String question_bank_path) {
        QuestionReader qr = new QuestionReader();
        List<Question> questions_result = qr.read_file(question_bank_path);
        questions = rands.stream()
                .map(questions_result::get)
                .collect(Collectors.toList());
    }


    public void add_questions(String filename, String question, String option1, String option2, String option3, String option4, String answer) throws IOException {
        Writer output = new BufferedWriter(new FileWriter(filename, true));
        StringBuilder sb = new StringBuilder();
        sb.append(question).append(option1).append(option2).append(option3).append(option4).append(answer);
        output.append(String.valueOf(sb)).append("\n");
        output.close();

    }

    //must refactor this later
//    public void display_next_question() {
    public int display_next_question() {
        if (idx < total){
            Question q = questions.get(idx);
            System.out.println("\n" + q.getQuestion() );
//            Arrays.stream(q.getChoices()).forEach(System.out::println);
            System.out.println("a. " + q.getChoices()[0]);
            System.out.println("b. " + q.getChoices()[1]);
            System.out.println("c. " + q.getChoices()[2]);
            System.out.println("d. " + q.getChoices()[3]);
            System.out.print("Answer: ");


//            update_score(q);

            idx++;
            return check_answer(q);
        }
        else return -1;
    }

//    public int get_score () {
//        return score;
//    }
//
//    public void add_point () {
//        score++;
//    }

//    private void update_score (com.sumerge.Question q) {
//        try {
//            String nl = sc.nextLine();
//            if (nl.equals("s")){
////                System.out.println("before q.add");
//                questions.add(q);
////                System.out.println("after q.add");
//                total++;
//            }
//            else if (nl.equals(q.getAnswer()) || q.getChoices()[(int) nl.charAt(0) - 97].equals(q.getAnswer())) {
//                add_point();
//                print_correct();
//            }
//
//             else {
//                print_wrong(q.getAnswer());
//            }
//        }
//        catch (Exception e) {
////            e.printStackTrace();
//            invalid_input();
//            update_score(q);
//        }
//    }
    public int check_answer(Question q) {
//        try {
//            String nl = sc.nextLine();
//            if (nl.equals("s")){
////                System.out.println("before q.add");
//                questions.add(q);
////                System.out.println("after q.add");
//                total++;
//                System.out.println(total);
//                return 0;
//            }
//            else if (Arrays.stream(q.getChoices()).anyMatch(nl::equals) == true){
//                print_wrong(q.getAnswer());
//                return 0;
//            }
//            else if (nl.equals(q.getAnswer()) || q.getChoices()[(int) nl.charAt(0) - 97].equals(q.getAnswer())) {
//                print_correct();
//                return 1;
//            }
//
//             else {
//                print_wrong(q.getAnswer());
//                return 0;
//            }
//        }
//        //need to check this. recursion might be wrong.
//        catch (Exception e) {
////            e.printStackTrace();
//            print_invalid_input();
//            return check_answer(q);
//
//        }
        try {
            String nl = sc.nextLine();

            if (nl.equalsIgnoreCase("s")) {
                q.make_repeated();
                questions.add(q);
                total++;
//                System.out.println(total);
                return 0;
            }

            // Check if the input is one of the choices
            if (!nl.equals(q.getAnswer()) && Arrays.stream(q.getChoices()).anyMatch(nl::equals)) {
                print_wrong(q.getAnswer());
                return 0;
            }

            if (nl.equalsIgnoreCase(q.getAnswer()) || q.getChoices()[(int) nl.charAt(0) - 97].equals(q.getAnswer())) {
                print_correct();
                return 1;
            }

            print_wrong(q.getAnswer());
            return 0;

        }

        catch (Exception e) {
            print_invalid_input();
            return check_answer(q);

        }
    }


    private void print_correct () {
        System.out.println("Correct!");
    }

    private void print_wrong (String answer) {
        System.out.println("Wrong!");
        System.out.println("Correct Answer: " + answer);
    }

    private void print_invalid_input () {
        System.out.println("Invalid input, try again");

    }

}
