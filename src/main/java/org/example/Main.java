package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sun.javafx.logging.PulseLogger.incrementCounter;

public class Main {
    public static AtomicInteger threeCount = new AtomicInteger();
    ;
    public static AtomicInteger fourCount = new AtomicInteger();
    ;
    public static AtomicInteger fiveCount = new AtomicInteger();
    ;

    public static void main(String[] args) throws InterruptedException {


        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread palindrome = new Thread(() -> {
            for (String text : texts) {
                int length = text.length();
                int j = 0;
                int forward = 0;
                int backward = length - 1;
                while (backward > forward) {
                    char forwardChar = text.charAt(forward++);
                    char backwardChar = text.charAt(backward--);
                    if (forwardChar != backwardChar) {
                        j = 0;
                        break;
                    }
                    j = 1;
                }
                if (j == 1) {
                    if (text.length() == 3) threeCount.incrementAndGet();
                    else if (text.length() == 4) fourCount.incrementAndGet();
                    else if (text.length() == 5) fiveCount.incrementAndGet();
                }
            }

        });
        palindrome.start();
        Thread sameWord = new Thread(() -> {
            for (String text : texts) {
                int i = 0;
                int j = 0;
                while (i < text.length() - 1) {
                    if (text.charAt(i) == text.charAt(i + 1)) {
                        j = 1;
                        i++;
                    } else {
                        j = 0;
                        break;
                    }
                }
                if (j == 1) {
                    if (text.length() == 3) threeCount.incrementAndGet();
                    else if (text.length() == 4) fourCount.incrementAndGet();
                    else if (text.length() == 5) fiveCount.incrementAndGet();
                }
            }

        });
        sameWord.start();
        Thread heightWord = new Thread(() -> {
            for (String text : texts) {
                int i = 0;
                int j = 0;
                while (i < text.length() - 1) {
                    if (text.charAt(i) < text.charAt(i + 1)) {
                        j = 1;
                    } else {
                        j = 0;
                        break;
                    }
                }

                if (j == 1) {
                    if (text.length() == 3) threeCount.incrementAndGet();
                    else if (text.length() == 4) fourCount.incrementAndGet();
                    else if (text.length() == 5) fiveCount.incrementAndGet();
                }
            }

        });
        heightWord.start();
        palindrome.join();
        sameWord.join();
        heightWord.join();
        System.out.println("Красивых слов с длиной 3: " + threeCount.get());
        System.out.println("Красивых слов с длиной 3: " + fourCount.get());
        System.out.println("Красивых слов с длиной 3: " + fiveCount.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}