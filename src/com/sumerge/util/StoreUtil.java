package com.sumerge.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StoreUtil {
    public void storeScore(String name, int score, String out_path) throws IOException {
        Writer output = new BufferedWriter(new FileWriter(out_path, true));
        StringBuilder sb = new StringBuilder();

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(formatter);

        sb.append(name).append(", ").append(score).append(", ").append(formattedTime);
        output.append(String.valueOf(sb)).append("\n");
        output.close();
    }
}
