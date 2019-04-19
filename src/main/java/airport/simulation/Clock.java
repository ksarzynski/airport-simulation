package main.java.airport.simulation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class Clock {
    private Date currentTime;

    Clock() {
        this.currentTime = removeTime(new Date());
    }

    void increaseTime(Integer minutes) {
        this.currentTime = new Date(this.currentTime.getTime() + (minutes * 60 * 1000));
    }

    String getTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(this.currentTime);
    }

    void runTimer() {
        TimerTask schedule = new Schedule();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(schedule, 500, 1000);
    }

    private static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
