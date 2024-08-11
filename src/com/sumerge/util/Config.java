package com.sumerge.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();
    private static final String workingDir = System.getProperty("user.dir");
    static {
        try (FileInputStream input = new FileInputStream(workingDir + "/src/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Configuration file not found or cannot be loaded");
        }
    }

    public static String getQuestionBankPath() {
        return workingDir + properties.getProperty("questionBankPath");
    }

    public static String getOutputFilePath() {
        return workingDir + properties.getProperty("outputFilePath");
    }

    public static int getQuizSize() {
        return Integer.parseInt(properties.getProperty("quizSize"));
    }

    public static int getWaitTime() {
        return Integer.parseInt(properties.getProperty("waitTime"));
    }
}
