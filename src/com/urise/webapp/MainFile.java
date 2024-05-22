package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        MainFile mainFile = new MainFile();
        mainFile.printDir(new File("."));
    }

    void printDir(File dir) throws IOException {
        if (!dir.exists()) {
            System.out.println("Путь " + dir.getCanonicalPath() + " не найден.");
            return;
        }
        if (dir.isDirectory()) {
            System.out.println(dir.getCanonicalPath());
            for (File file : dir.listFiles()) {
                printDir((file));
            }
        } else System.out.println(dir.getCanonicalPath());
    }
}
