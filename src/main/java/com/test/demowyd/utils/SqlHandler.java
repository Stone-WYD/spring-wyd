package com.test.demowyd.utils;

import java.io.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: spring-wyd
 * @description: sql 处理
 * @author: Stone
 * @create: 2023-10-12 14:31
 **/
public class SqlHandler {

    public static void main(String[] args) {
        File sourceFile = new File("C:\\Users\\HP\\Desktop\\testFinal.txt");
        File targetFile = new File("C:\\Users\\HP\\Desktop\\target.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {

            String line;
            // 对第一行进行处理
            if ((line = reader.readLine()) != null) {
                String handlerLine = handleLine(line, true);
                writer.write(handlerLine);
                writer.newLine();
            }
            while ((line = reader.readLine()) != null) {
                // 对读取到的内容进行处理，这里只是简单地添加了一个前缀
                String handlerLine = handleLine(line, false);
                writer.write(handlerLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String handleLine(String line, boolean firstLine){
        String trim = line.trim();
        String[] split = trim.split("\\s");
        StringBuilder sb = new StringBuilder();

        if (firstLine) {
            // 第一行
            for (String s : split) {
                sb.append(s.trim()).append(",");
            }
        } else {
            // 不是第一行
            for (int i = 0; i < split.length; i++) {
                // 处理时间
                if (i >= 6){
                    LocalDateTime localDateTime = LocalDateTime.parse(split[i], DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    if (i == 8){
                        // 处理日期
                        sb.append(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append(",");
                    } else {
                        sb.append(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))).append(",");
                    }
                } else {
                    sb.append(split[i].trim()).append(",");
                }
            }
        }
        sb.replace(sb.length() - 1, sb.length(), "");
        return sb.toString();
    }
}
