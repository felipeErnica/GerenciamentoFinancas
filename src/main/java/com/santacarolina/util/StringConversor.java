package com.santacarolina.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Locale;

public class StringConversor {

    private static final Logger logger = LogManager.getLogger();

    public static LocalDate transformDate(String input) throws DateTimeException {
        try {

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

            return LocalDate.of(year, month, day);
        } catch (NumberFormatException | DateTimeException e) {
            logger.error(e);
            throw new DateTimeException(e.getMessage());
        }
    }

    public static String getCurrency(double input) {
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.of("pt","BR"));
        return moneyFormat.format(input);
    }

    public static Double getDoubleFromLocalFormat(String input) throws ParseException {
        input = input.replaceAll("[^\\d,.+-]", "");
        if (input.isEmpty()) return 0d;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            NumberFormat format = NumberFormat.getNumberInstance();
            return format.parse(input).doubleValue();
        }
    }

}