package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.dictionary.Dictionary.EXECUTION_TIME;
import static org.example.dictionary.Dictionary.NEW_LINE;
import static org.example.dictionary.Dictionary.SECONDS;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getGlobal();
        try {
            long start = System.currentTimeMillis();
            new OperationsWithNumber().readFile();
            long end = System.currentTimeMillis();
            double time = (end - start) / 1000.0;
            logger.log(Level.INFO, NEW_LINE + EXECUTION_TIME + time + SECONDS);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

