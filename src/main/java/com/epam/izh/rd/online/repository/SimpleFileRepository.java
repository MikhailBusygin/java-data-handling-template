package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.io.FileReader;

public class SimpleFileRepository implements FileRepository {

    static long countFile = 0;
    static long countDir = 0;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File dir = new File("src/main/resources/" + path);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile())
                countFile ++;
            if (file.isDirectory()) {
                countFilesInDirectory(path + "/" + file.getName());
            }
        }
        return countFile;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir = new File("src/main/resources/" + path);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                countDir++;
                countDirsInDirectory(path + "/" + file.getName());
            }
        }
        return countDir + 1;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        from = "src/main/resources/" + from;
        to = "src/main/resources/" + to;
        File file = new File(from);
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File fileEx : listFiles) {
                if (fileEx.isFile() && fileEx.getName().contains(".txt")) {
                    Path p2 = Paths.get(to + fileEx.getName());
                    try {
                        Files.copy(fileEx.toPath(), p2, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ioex) {
                        ioex.printStackTrace();
                    }

                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        path = "target/classes/" + path;
        File filePath = new File(path);
        filePath.mkdir();
        File file = new File(filePath, name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.exists();
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String dataIO = "";
        try {
            FileReader fr = new FileReader("src/main/resources/" + fileName);
            BufferedReader br = new BufferedReader(fr);
            dataIO = br.readLine();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return dataIO;
    }
}
