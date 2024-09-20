package com.santacarolina.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Locale;

public class StringConversor {


    public static LocalDate transformDate(String input) throws DateTimeException {

        int lenght = 0;

        int day = 0;
        int month = 0;
        int year = 0;

        for (int i = lenght; i < input.length(); i++) {
            Character c = input.charAt(i);
            if (c.equals('-') || c.equals('/')) {
                day = Integer.parseInt(input.substring(lenght, i));
                lenght = i + 1;
                break;
            }
        }

        for (int i = lenght; i < input.length(); i++) {
            Character c = input.charAt(i);
            if (c.equals('-') || c.equals('/')) {
                month = Integer.parseInt(input.substring(lenght, i));
                lenght = i + 1;
                break;
            } else if (i == input.length() - 1) {
                month = Integer.parseInt(input.substring(lenght, i + 1));
                lenght = input.length();
            }
        }

        if (lenght < input.length()) {
            year = Integer.parseInt(input.substring(lenght));
        }

        if (year == 0) {
            year = LocalDate.now().getYear();
        } else if (year < 1000) {
            year += 2000;
        }

        return LocalDate.of(year,month,day);
    }

    public static String getCurrency(double input) {
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.of("pt","BR"));
        return moneyFormat.format(input);
    }

    public static Double getDoubleFromLocalFormat(String input) throws ParseException {
        input = input.startsWith("R$") ? input.substring(3) : input;
        if (input.isEmpty()) return 0d;
        NumberFormat format;
        if (input.contains(",")) format = NumberFormat.getNumberInstance(Locale.of("pt","BR"));
        else format = NumberFormat.getNumberInstance();
        return format.parse(input).doubleValue();
    }

}
