package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {

    int depth = 0;

    public static void main(String[] args) throws IOException {
        MainFile mainFile = new MainFile();
        mainFile.printDir(new File("."));
    }


    void printWithTabs(String string) {
        System.out.println();
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        System.out.print(string);
    }

    void printDir(File dir) throws IOException {

        if (!dir.exists()) {
            System.out.println("Путь " + dir.getName() + " не найден.");
            return;
        }
        if (dir.isDirectory()) {
            printWithTabs(dir.getCanonicalPath());
            depth++;
            for (File file : dir.listFiles()) {
                printDir((file));
            }
            depth--;
        } else {
            printWithTabs(dir.getName());
        }
    }


}
