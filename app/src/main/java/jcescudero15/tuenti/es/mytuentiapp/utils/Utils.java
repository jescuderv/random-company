package jcescudero15.tuenti.es.mytuentiapp.utils;

import android.location.Location;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    private final static double TUENTI_OFFICE_LONGITUDE = 40.420331;
    private final static double TUENTI_OFFICE_LATITUDE = -3.702246;


    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String dateFormat(String date) {
        // Get readable input date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'",
                Locale.getDefault());
        CharSequence ago = "";
        try {
            long time = simpleDateFormat.parse(date).getTime();
            long now = System.currentTimeMillis();
            ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.DAY_IN_MILLIS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ago.toString();
    }

    public static boolean containsMatchText(String matchText, String text) {
        // Check if text contain matchText
        return text.toLowerCase(Locale.getDefault()).contains(matchText);
    }

    public static Location getCurrentLocation() {
        // Get fake current location
        Location currentLocation = new Location("");
        currentLocation.setLongitude(TUENTI_OFFICE_LONGITUDE);
        currentLocation.setLatitude(TUENTI_OFFICE_LATITUDE);
        return currentLocation;
    }
}
