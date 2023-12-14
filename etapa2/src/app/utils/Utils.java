package app.utils;

import java.util.StringTokenizer;

public class Utils {
    public static String validateData(String data) {
        StringTokenizer tokenizer = new StringTokenizer(data, "-");
        int day = Integer.parseInt(tokenizer.nextToken());
        int month = Integer.parseInt(tokenizer.nextToken());
        int year = Integer.parseInt(tokenizer.nextToken());

        if (month > 12 || year < 1900 || year > 2023 || day > 31 || (month == 2 && day > 29))
            return "error";
        return "valid";
    }
}
