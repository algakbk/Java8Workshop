import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Because of several problems, there are alternatives to the old java.util.Date and java.util.Calendar classes.
 */
public class C_06_DateAndTimeAPI {

    /*
        There are two main problems with the old Date API:
        1. Objects of type Date and Calendar are mutable, there always are setters. This causes them to be not
           thread-safe.
        2. Concepts of date and time were not separated.

        Both issues were resolved in the new API. Here's an overview of the new classes:

         java.time.Instant      = point in time, represented in milliseconds. Good for machines, bad for humans.
         java.time.LocalDate    = date - human-readable, without time zone
         java.time.LocalTime    = time - human-readable, without time zone.
         java.time.LocalDateTime = LocalDate + LocalTime without time zone
         java.time.ZonedDateTime = LocalDate + LocalTime with time zone
         java.time.YearMonth     = year + month
         java.time.MonthDay      = month + day
         java.time.Year          = year
     */

    @Test
    public void examples() {

        Instant instant = Instant.now();
        System.out.println("Epoch second: " + instant.getEpochSecond());
        LocalTime localTime = LocalTime.now();
        System.out.println("LocalTime: " + localTime);
        LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate: " + localDate);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime: " + localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("ZonedDateTime: " + zonedDateTime);
        YearMonth yearMonth = YearMonth.now();
        System.out.println("YearMonth: " + yearMonth);
        MonthDay monthDay = MonthDay.now();
        System.out.println("MonthDay: " + monthDay);
        Year year = Year.now();
        System.out.println("Year: " + year);
    }

    /**
     * Date and Time objects are immutable, i.e. they cannot be altered. If you want to express another date or time,
     * you have to create a new object.
     */
    @Test
    public void immutable() {
        LocalDate birthday = LocalDate.of(2015, 7, 19);
        // No setter methods to change LocalDate! Only possible to create new objects like this:
        LocalDate birthdayIn2016 = birthday.withYear(2016);

        // Hence, objects are thread-safe.
    }

    /**
     * The new API allows to represent a day and a month independent from the year. This way, recurrent events
     * such as birthdays and christmas can be expressed.
     */
    @Test
    public void recurrentEvents() {

        // Java 7: always in a specific year:
        GregorianCalendar d1 = new GregorianCalendar();
        d1.set(GregorianCalendar.YEAR, 2015);
        d1.set(GregorianCalendar.MONTH, 7);
        d1.set(GregorianCalendar.DAY_OF_MONTH, 19);
        Date stevensBirthdayIn2015 = d1.getTime();

        // Java 8: Stevens birthday in every year:
        MonthDay d2 = MonthDay.of(7, 19);

        // ... can be used in every year:
        LocalDate localDate = d2.atYear(2015);
        System.out.println(localDate.getDayOfWeek());
    }

    @Test
    public void period() {
        LocalDate now = LocalDate.now();
        LocalDate birthday = LocalDate.of(1984, 07, 19);

        Period age = Period.between(birthday, now);
        long ageDays = ChronoUnit.DAYS.between(birthday, now);

        System.out.println("I'm " + age.getYears() + " years, " + age.getMonths() + " months and " + age.getDays()
                + " old. That's " + ageDays + " days in total.");
    }

    @Test
    public void calculatingTimeAndDate() {

        // Simplified methods for calculating time and date and doing math:

        LocalTime now = LocalTime.now();
        System.out.println(now);
        LocalTime beforeTwoHours = now.minusHours(2);
        System.out.println(beforeTwoHours);
        LocalTime inTwoHours = now.plusHours(2);
        System.out.println(inTwoHours);
        LocalTime withOneOClock = now.withHour(1);
        System.out.println(withOneOClock);

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalDate beforeTwoDays = localDate.minusDays(2);
        System.out.println(beforeTwoDays);
        LocalDate inTwoDays = localDate.plusDays(2);
        System.out.println(inTwoDays);
        LocalDate withFirstDayOfMonth = localDate.withDayOfMonth(1);
        System.out.println(withFirstDayOfMonth);
    }

    @Test
    public void temporalAdjuster() {

        // temporal adjuster = implementation of strategy design pattern for "modifying" a temporal object, i.e.
        // creating a new one.

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Last day of year: " + now.with(TemporalAdjusters.lastDayOfYear()));
        System.out.println("First day of next year: " + now.with(TemporalAdjusters.firstDayOfNextYear()));

        TemporalAdjuster nextOddDayTemporalAdjuster = new TemporalAdjuster() {
            @Override
            public Temporal adjustInto(Temporal temporal) {
                LocalDate localDate = LocalDate.from(temporal);

                int day = localDate.getDayOfMonth();
                if (day % 2 == 0) {
                    localDate = localDate.plusDays(1);
                } else {
                    localDate = localDate.plusDays(2);
                }

                return temporal.with(localDate);
            }
        };

        System.out.println("Next odd day: " + now.with(nextOddDayTemporalAdjuster));
    }

    @Test
    public void timezones() {
        /*
        Java 7:
        java.util.TimeZone
        = designator ("Europe/Berlin")
        + Offset to Greenwich/UTC-time ("+02:00")
        + rules for when summer and winter time change.

        Java 8: separation of concerns:
        ZoneId + ZoneOffset + ZoneRules
         */

        // todo some examples, please!
    }

    @Test
    public void calendarSystems() {
        // todo: ISO, japan, ThaiBuddhist etc
    }


    @Test
    public void parsing() {
        // todo
//        DateTimeFormatter f
    }
}
