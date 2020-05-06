package employees.utils;

import java.util.Calendar;
import java.util.Date;

public class Helpers {

    // check if birth date and employ date are valid
    public static boolean checkDate(Date birthDate, Date employDate) {
        Date currentDate = new Date();

        // birth and employ dates must not be from future
        if (employDate.after(currentDate)) {
            return false;
        }
        if (birthDate.after(currentDate)) {
            return false;
        }

        // employee must be at least 18 years old
        Calendar cal = Calendar.getInstance();
        cal.setTime(employDate);
        int year1 = cal.get(Calendar.YEAR);
        int day1 = cal.get(Calendar.DAY_OF_YEAR);

        cal.setTime(birthDate);
        int year2 = cal.get(Calendar.YEAR);
        int day2 = cal.get(Calendar.DAY_OF_YEAR);

        if (year1 - year2 < 18 || year1 - year2 == 18 && day1 < day2) {
            return false;
        }

        return true;
    }
}
