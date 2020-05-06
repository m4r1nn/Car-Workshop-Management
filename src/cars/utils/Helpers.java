package cars.utils;

import java.util.Calendar;
import java.util.Date;

public class Helpers {
    public static int getCurrentYear() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentYear = cal.get(Calendar.YEAR);
        return currentYear;
    }
}
