package com.danylo.bufferedConsoleLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class FileAppender implements Runnable {
    private List<String> buffer;
    private File log = new File("c:/temp/consolelog.log");

    public FileAppender(List<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try (FileWriter writer = new FileWriter(log, true)) {
                Thread.sleep(15 * 1000);
                append(writer);
            } catch (IOException e) {
                throw new RuntimeException("log writing error:", e);
            } catch (InterruptedException e) {
                onInterrupt();
                return;
            }
        }

    }

    private void append(FileWriter writer) throws IOException {
        synchronized (buffer) {
            Iterator<String> iterator = buffer.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                writer.write(line);
                writer.write("\r\n");
                iterator.remove();
            }
        }
    }

    private void onInterrupt() {
        try (FileWriter writer = new FileWriter(log, true)) {
            append(writer);
        } catch (IOException e) {
            throw new RuntimeException("log writing error:", e);
        }
    }
}
