package ru.pa4ok.library.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd");

    public static String getYear() {
        return YEAR_FORMAT.format(new Date());
    }

    public static String getMonth() {
        return MONTH_FORMAT.format(new Date());
    }

    public static String getDay() {
        return DAY_FORMAT.format(new Date());
    }

    public static final String[] MONTHS_EN = new String[] {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    public static final String[] MONTHS_RU = new String[] {
            "январь",
            "февраль",
            "март",
            "апрель",
            "май",
            "июнь",
            "июль",
            "август",
            "сентябрь",
            "октябрь",
            "ноябрь",
            "декабрь"
    };
}
