package com.sumerge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionReader {

    public List<Question> read_file(String filePath) {

        List<Question> list = new ArrayList<>();
//        String fileName = "c://lines.txt";
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            list = stream
                    .map(QuestionReader::create_question_from_line)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;

    }

    public static Question create_question_from_line(String line) {
        List<String> parts = Stream.of(line.split(";"))
                .collect(Collectors.toList());

        String question = parts.get(0);
        String[] choices = parts.subList(1, 5).toArray(new String[0]);
        String answer = parts.get(5);
        return new Question(question, choices, answer);
    }



}
