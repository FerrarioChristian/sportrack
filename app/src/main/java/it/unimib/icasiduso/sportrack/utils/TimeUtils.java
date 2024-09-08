package it.unimib.icasiduso.sportrack.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import it.unimib.icasiduso.sportrack.App;
import it.unimib.icasiduso.sportrack.R;

public class TimeUtils {
    public static String getDayNameFromDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            return "Error";
        }

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return App.getInstance().getString(R.string.sunday);
            case Calendar.MONDAY:
                return App.getInstance().getString(R.string.monday);
            case Calendar.TUESDAY:
                return App.getInstance().getString(R.string.tuesday);
            case Calendar.WEDNESDAY:
                return App.getInstance().getString(R.string.wednesday);
            case Calendar.THURSDAY:
                return App.getInstance().getString(R.string.thursday);
            case Calendar.FRIDAY:
                return App.getInstance().getString(R.string.friday);
            case Calendar.SATURDAY:
                return App.getInstance().getString(R.string.saturday);
            default:
                return "Error";
        }
    }

    public static String getDateFromDayIndex(int dayIndex, int offset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().minusDays(offset - dayIndex);
        return startDate.format(formatter);
    }

    public static String convertMsToTime(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;

        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
