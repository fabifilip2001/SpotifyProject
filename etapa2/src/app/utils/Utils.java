package app.utils;

import java.util.StringTokenizer;

public final class Utils {
    public static final Integer MONTH_LIMIT = 12;
    public static final Integer MIN_YEAR_LIMIT = 1900;
    public static final Integer CURRENT_YEAR = 2023;
    public static final Integer NORMAL_MONTH_DAYS_LIMIT = 31;
    public static final Integer FEBRUARY = 2;
    public static final Integer FEBRUARY_DAY_LIMIT = 29;
    public static final Integer MAXIMUM_RESULT_SIZE = 5;
    private Utils() { }

    /**
     * Function who makes the validation for an input date
     * */
    public static String validateData(final String data) {
        StringTokenizer tokenizer = new StringTokenizer(data, "-");
        int day = Integer.parseInt(tokenizer.nextToken());
        int month = Integer.parseInt(tokenizer.nextToken());
        int year = Integer.parseInt(tokenizer.nextToken());

        if (month > Utils.MONTH_LIMIT || year < Utils.MIN_YEAR_LIMIT || year > Utils.CURRENT_YEAR
                || day > Utils.NORMAL_MONTH_DAYS_LIMIT
                || (month == Utils.FEBRUARY && day > Utils.FEBRUARY_DAY_LIMIT)) {
            return "error";
        }
        return "valid";
    }
}
