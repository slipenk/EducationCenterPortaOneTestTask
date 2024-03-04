package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.dictionary.Dictionary.ARITHMETIC_MEAN;
import static org.example.dictionary.Dictionary.DECREASING_NUMBERS;
import static org.example.dictionary.Dictionary.EMPTY_SPACE;
import static org.example.dictionary.Dictionary.EMPTY_STRING;
import static org.example.dictionary.Dictionary.FILE_INPUT_NAME;
import static org.example.dictionary.Dictionary.INCREASING_NUMBERS;
import static org.example.dictionary.Dictionary.MAXIMUM_NUMBER;
import static org.example.dictionary.Dictionary.MEDIAN;
import static org.example.dictionary.Dictionary.MINIMUM_NUMBER;
import static org.example.dictionary.Dictionary.NEW_LINE;

public class OperationsWithNumber {

    private final TreeMap<Long, Long> numberCounts = new TreeMap<>();
    private final StringBuilder currentIncreasingSequence = new StringBuilder();
    private final StringBuilder currentDecreasingSequence = new StringBuilder();
    private final Logger logger = Logger.getGlobal();
    private long totalNumbers = 0;
    private long previousIncreasingNumber = Long.MIN_VALUE;
    private long previousDecreasingNumber = Long.MAX_VALUE;
    private String longestIncreasingSequence = EMPTY_STRING;
    private String longestDecreasingSequence = EMPTY_STRING;

    public void readFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_INPUT_NAME))) {
            for (String line; (line = br.readLine()) != null; ) {
                long number = Long.parseLong(line.trim());
                numberCounts.put(number, numberCounts.getOrDefault(number, 0L) + 1);

                findSequenceOfIncreasingNumbers(number);
                findSequenceOfDecreasingNumbers(number);
            }

            checkIncreasingSequence();
            checkDecreasingSequence();

            String message = NEW_LINE + MAXIMUM_NUMBER + findMax() + NEW_LINE +
                    MINIMUM_NUMBER + findMin() + NEW_LINE +
                    MEDIAN + findMedian() + NEW_LINE +
                    ARITHMETIC_MEAN + findArithmeticMean() + NEW_LINE +
                    INCREASING_NUMBERS + longestIncreasingSequence + NEW_LINE +
                    DECREASING_NUMBERS + longestDecreasingSequence;

            logger.log(Level.INFO, message);
        }
    }

    private long findMax() {
        return numberCounts.lastKey();
    }

    private long findMin() {
        return numberCounts.firstKey();
    }

    private double findArithmeticMean() {
        long sum = 0;
        for (Map.Entry<Long, Long> entry : numberCounts.entrySet()) {
            sum += entry.getKey() * entry.getValue();
        }
        return (double) sum / totalNumbers;
    }

    public double findMedian() {
        totalNumbers = numberCounts.values().stream().mapToLong(Long::longValue).sum();
        long currentCount = 0;
        for (Map.Entry<Long, Long> entry : numberCounts.entrySet()) {
            long number = entry.getKey();
            long count = entry.getValue();
            currentCount += count;
            if (totalNumbers % 2 != 0 && currentCount > totalNumbers / 2) {
                return number;
            } else if (totalNumbers % 2 == 0 && currentCount >= totalNumbers / 2) {
                long nextNum = numberCounts.higherKey(number);
                return (number + nextNum) / 2.0;
            }
        }
        return -1;
    }

    private void findSequenceOfDecreasingNumbers(long currentNumber) {
        if (currentNumber < previousDecreasingNumber) {
            currentDecreasingSequence.append(currentNumber).append(EMPTY_SPACE);
        } else {
            checkDecreasingSequence();
            currentDecreasingSequence.setLength(0);
            currentDecreasingSequence.append(currentNumber).append(EMPTY_SPACE);
        }
        previousDecreasingNumber = currentNumber;
    }

    private void findSequenceOfIncreasingNumbers(long currentNumber) {
        if (currentNumber > previousIncreasingNumber) {
            currentIncreasingSequence.append(currentNumber).append(EMPTY_SPACE);
        } else {
            checkIncreasingSequence();
            currentIncreasingSequence.setLength(0);
            currentIncreasingSequence.append(currentNumber).append(EMPTY_SPACE);
        }
        previousIncreasingNumber = currentNumber;
    }

    private void checkDecreasingSequence() {
        if (currentDecreasingSequence.length() > longestDecreasingSequence.length()) {
            longestDecreasingSequence = currentDecreasingSequence.toString().trim();
        }
    }

    private void checkIncreasingSequence() {
        if (currentIncreasingSequence.length() > longestIncreasingSequence.length()) {
            longestIncreasingSequence = currentIncreasingSequence.toString().trim();
        }
    }
}
