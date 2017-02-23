package com.danylo.bufferedConsoleLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleReader implements Runnable {
    private List<String> buffer;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public ConsoleReader(List<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while(true) {
            String line = null;

            try {
               line = reader.readLine();
                if ("exit".equals(line)) {
                    reader.close();
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            synchronized (buffer) {
                buffer.add(line);
            }
        }
    }
}
