package com.example.aravindarc.seconds;

import android.text.Html;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class DateDifferenceHelper {

    private static Date birthDate;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public enum type { ALL, MONTHS, DAYS, HOURS, MINUTES, SECONDS, LOVE }

    private static type currentType = type.ALL;

    DateDifferenceHelper(String birthDateString) {

        try {
            birthDate = simpleDateFormat.parse(birthDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentType(type currentType) {
        this.currentType = currentType;
    }

    public String getDifference() {

        Date d = new Date();

        /*
        try {
            d = simpleDateFormat.parse("2019/05/12 14:21:0");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */

        DateTime dt1 = new DateTime(birthDate);
        DateTime dt2 = new DateTime(d);

        String returnString = "";

        long years = Years.yearsBetween(dt1, dt2).getYears();
        long months = Months.monthsBetween(dt1, dt2).getMonths() % 12;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        calendar.set(Calendar.DATE, dt1.getDayOfMonth());
        calendar.set(Calendar.MONTH, dt2.getMonthOfYear()-1);
        calendar.set(Calendar.YEAR, dt2.getYear());
        if(months != 0 || (dt2.getMonthOfYear() != dt1.getMonthOfYear()
                && dt2.getDayOfMonth() == dt1.getDayOfMonth()))
            calendar.add(Calendar.MONTH, -1);

        DateTime dt3 = new DateTime(calendar.getTime());

        long days = Days.daysBetween(dt3, dt2).getDays();

        if(days < 0)
            days = 30 + days;


        if(days > 29) {
            if(days - 30 == 0 && dt2.getSecondOfDay() >= dt3.getSecondOfDay()) {
                if(dt2.getDayOfMonth() - dt3.getDayOfMonth() == 0)
                    days %= 30;
                else
                    days %= 31;
            }
            else {
                days %= 31;
            }
        }

        long hours = Hours.hoursBetween(dt1, dt2).getHours() % 24;
        long minutes = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
        long seconds = Seconds.secondsBetween(dt1, dt2).getSeconds() % 60;

        switch (currentType) {
            case ALL:
                returnString += (years == 0 ? "" : years + (years == 1 ?  " year\n" : " years\n"))
                        + (months == 0 ? "" : months + (months == 1 ? " month\n" : " months\n"))
                        + (days == 0 ? "" : days + (days == 1 ? " day\n" : " days\n"))
                        + (hours == 0 ? "" : hours + (hours == 1 ? " hour\n" : " hours\n"))
                        + (minutes == 0 ? "" : minutes + (minutes == 1 ? " minute\n" : " minutes\n"))
                        + (seconds == 0 ? "" : seconds + (seconds == 1 ? " second\n" : " seconds\n"));
                break;

            case MONTHS:
                returnString += Months.monthsBetween(dt1, dt2).getMonths() + " months\n"
                        + (days == 0 ? "" : days + (days == 1 ? " day\n" : " days\n"))
                        + (hours == 0 ? "" : hours + (hours == 1 ? " hour\n" : " hours\n"))
                        + (minutes == 0 ? "" : minutes + (minutes == 1 ? " minute\n" : " minutes\n"))
                        + (seconds == 0 ? "" : seconds + (seconds == 1 ? " second\n" : " seconds\n"));
                break;

            case DAYS:
                returnString += Days.daysBetween(dt1, dt2).getDays() + " days\n"
                        + (hours == 0 ? "" : hours + (hours == 1 ? " hour\n" : " hours\n"))
                        + (minutes == 0 ? "" : minutes + (minutes == 1 ? " minute\n" : " minutes\n"))
                        + (seconds == 0 ? "" : seconds + (seconds == 1 ? " second\n" : " seconds\n"));
                break;

            case HOURS:
                returnString += Hours.hoursBetween(dt1, dt2).getHours() + " hours\n"
                        + (minutes == 0 ? "" : minutes + (minutes == 1 ? " minute\n" : " minutes\n"))
                        + (seconds == 0 ? "" : seconds + (seconds == 1 ? " second\n" : " seconds\n"));
                break;

            case MINUTES:
                returnString += Minutes.minutesBetween(dt1, dt2).getMinutes() + " minutes\n"
                        + (seconds == 0 ? "" : seconds + (seconds == 1 ? " second\n" : " seconds\n"));
                break;

            case SECONDS:
                returnString += Seconds.secondsBetween(dt1, dt2).getSeconds() + " seconds\n";
                break;

            case LOVE:
                returnString += Html.fromHtml("\u221E").toString() + " " + Html.fromHtml("\u2764").toString();;

        }

        if(months == 0 && days == 0 && hours == 0 && minutes == 0)
            returnString += "\nHappy Birthday Kiruko\n" + Html.fromHtml("\uD83D\uDE0A");

        return returnString;
    }
}
