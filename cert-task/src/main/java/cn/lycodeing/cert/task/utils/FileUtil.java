package cn.lycodeing.cert.task.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {


    public static String getCert(String path) throws IOException {
        return readFileAsString(path);
    }


    public static String readFileAsString(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                // 保留换行符
                contentBuilder.append(currentLine).append("\n");
            }
        }
        return contentBuilder.toString();
    }
}
