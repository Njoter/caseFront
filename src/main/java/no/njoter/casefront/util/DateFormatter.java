package no.njoter.casefront.util;

import java.time.LocalDateTime;

public class DateFormatter {

    public static String ddmmyyyyFormat(LocalDateTime localDateTime) {
        String day = "" + localDateTime.getDayOfMonth();
        String month = convertMonthValueToString(localDateTime.getMonthValue());
        String year = "" + localDateTime.getYear();
        String hour = "" + localDateTime.getHour();
        String minute = "" + localDateTime.getMinute();
        String seconds = "" + localDateTime.getSecond();

        return day + " " + month + " " + year + " - " + hour + ":" + minute + ":" + seconds;
    }

    private static String convertMonthValueToString(int monthValue) {
        String month = "";
        switch (monthValue) {
            case 1 -> month = "Jan";
            case 2 -> month = "Feb";
            case 3 -> month = "Mar";
            case 4 -> month = "Apr";
            case 5 -> month = "Mai";
            case 6 -> month = "Jun";
            case 7 -> month = "Jul";
            case 8 -> month = "Aug";
            case 9 -> month = "Sep";
            case 10 -> month = "Okt";
            case 11 -> month = "Nov";
            case 12 -> month = "Des";
        }
        return month;
    }
}
