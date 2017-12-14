package org.folio.rest.utils;

import static java.util.Calendar.*;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.folio.rest.jaxrs.model.DaysIncluded;

public class CalendarUtils {

  public static final String DAY_PATTERN = "EEEE";

  public static DayOfWeek dayOfDate(Date inputDate) {
    DayOfWeek nameOfday = DayOfWeek.valueOf(new SimpleDateFormat(DAY_PATTERN, Locale.ENGLISH).format(inputDate).toUpperCase());
    return nameOfday;
  }

  public static List<Calendar> itarateDates(Calendar startCal, Calendar endCal, DaysIncluded daysIncluded) {
    List<Calendar> calendars = new ArrayList<>();
    Map<DayOfWeek, Boolean> daysIsIncluded = setDaysWithIncluding(daysIncluded);

    while (startCal.before(endCal)) {
      if (daysIncluded.getAllDays()) {
        Calendar tempCal = Calendar.getInstance(); //because passing by reference is not good for us now
        tempCal.setTime(startCal.getTime());
        calendars.add(tempCal);
      } else {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
          if (dayOfWeek.equals(dayOfDate(startCal.getTime())) && daysIsIncluded.get(dayOfWeek)) {
            Calendar tempCal = Calendar.getInstance(); //because passing by reference is not good for us now
            tempCal.setTime(startCal.getTime());
            calendars.add(tempCal);
            break;
          }
        }
      }
      startCal.add(DAY_OF_MONTH, 1);
    }
    return calendars;
  }

  public static Map<DayOfWeek, Boolean> setDaysWithIncluding(DaysIncluded daysIncluded) {

    Map<DayOfWeek, Boolean> daysIsIncluded = new HashMap<>();

    daysIsIncluded.put(DayOfWeek.MONDAY, daysIncluded.getMonday());
    daysIsIncluded.put(DayOfWeek.TUESDAY, daysIncluded.getTuesday());
    daysIsIncluded.put(DayOfWeek.WEDNESDAY, daysIncluded.getWednesday());
    daysIsIncluded.put(DayOfWeek.THURSDAY, daysIncluded.getThursday());
    daysIsIncluded.put(DayOfWeek.FRIDAY, daysIncluded.getFriday());
    daysIsIncluded.put(DayOfWeek.SATURDAY, daysIncluded.getSaturday());
    daysIsIncluded.put(DayOfWeek.SUNDAY, daysIncluded.getSunday());

    return daysIsIncluded;
  }
}