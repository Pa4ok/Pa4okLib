package ru.pa4ok.library.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    //yyyy.MM.dd G HH:mm:ss z

    public static final long MILL_SECOND = 1000L;
    public static final long MILL_MINUTE = MILL_SECOND * 60;
    public static final long MILL_HOUR = MILL_MINUTE * 60;
    public static final long MILL_DAY = MILL_HOUR * 24;
    public static final long MILL_WEEK = MILL_DAY * 7;

    public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd");

    public static final SimpleDateFormat STANDART_DATE_TIME = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

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
            "December",
    };

    public static final String[] MONTHS_RU = new String[] {
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
    };

    public static enum MonthEnum
    {
        JANUARY,
        FEBRUARY,
        MARCH,
        APRIL,
        MAY,
        JUNE,
        JULY,
        AUGUST,
        SEPTEMBER,
        OCTOBER,
        NOVEMBER,
        DECEMBER;

        @Override
        public String toString() {
            return MONTHS_EN[this.ordinal()];
        }

        public String en() {
            return MONTHS_EN[this.ordinal()];
        }

        public String rus() {
            return MONTHS_RU[this.ordinal()];
        }
    }
}
