package com.danylo.bufferedConsoleLogger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<String> buffer = new ArrayList<>();
        Thread consoleReader = new Thread(new ConsoleReader(buffer));
        Thread fileAppender = new Thread(new FileAppender(buffer));
        consoleReader.start();
        fileAppender.start();
        consoleReader.join();
        fileAppender.interrupt();
    }
}
