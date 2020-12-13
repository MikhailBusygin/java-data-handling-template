package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String dataEx = "";
        char[] chEx;
        int group1;
        int group2;
        try {
            FileReader fr = new FileReader("src/main/resources/sensitive_data.txt");
            BufferedReader br = new BufferedReader(fr);
            dataEx = br.readLine();
            chEx = dataEx.toCharArray();
            Pattern pattern = Pattern.compile("\\d{4}\\s(\\d{4})\\s(\\d{4})\\s\\d{4}");
            Matcher matcher = pattern.matcher(dataEx);
            while (matcher.find()) {
                group1 = matcher.start(1);
                group2 = matcher.start(2);
                for (int i = 0; i < chEx.length; i++) {
                    if (i == group1 || i == group2) {
                        for (int j = i; j < i + 4; j++) {
                            chEx[j] = '*';
                        }
                        i = i + 3;
                    }
                }
            }
            dataEx = String.valueOf(chEx);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return dataEx;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String dataIO = "";
        try {
            FileReader fr = new FileReader("src/main/resources/sensitive_data.txt");
            BufferedReader br = new BufferedReader(fr);
            dataIO = br.readLine().replaceAll("\\W\\Wpayment_amount\\W", String.valueOf(paymentAmount).replaceFirst("\\W.", ""));
            dataIO = dataIO.replaceAll("\\W\\Wbalance\\W", String.valueOf(balance).replaceFirst("\\W.", ""));
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return dataIO;
    }
}
