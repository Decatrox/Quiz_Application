package com.sumerge.question;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionReader {

    public List<Question> readFile(String filePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream
                    .map(QuestionReader::convertLineToQuestion)
                    .collect(Collectors.toList());
        }
    }

    public static Question convertLineToQuestion(String line) {
        List<String> parts = List.of(line.split(";"));
        String question = parts.get(0);
        String[] choices = parts.subList(1, 5).toArray(new String[0]);
        String answer = parts.get(5);

        return new Question(question, choices, answer);
    }
}