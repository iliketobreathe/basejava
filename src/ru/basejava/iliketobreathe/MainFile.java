package ru.basejava.iliketobreathe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File path2 = new File("C:\\Users\\Александр\\basejava\\src\\ru\\basejava\\iliketobreathe");
        printDirsAndFiles(path2);
    }

    public static void printDirsAndFiles(File path) {
        File[] fileList = path.listFiles();
        if (fileList != null) {
            for (File f : fileList) {
                if (f.isFile()) {
                    System.out.println("FIle: " + f.getName());
                }
                else if (f.isDirectory()) {
                    System.out.println("----------------------------");
                    System.out.println("Directory: " + f.getName());
                    printDirsAndFiles(f);
                    System.out.println("----------------------------");
                }
            }
        }
    }
}
