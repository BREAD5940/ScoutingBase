package base;

import java.time.LocalDate;
import java.time.LocalTime;

public class StandRotation {
    public LocalDate date; // Date of assignment
    public LocalTime startTime; // Time of assignment
    public LocalTime endTime;
    public String station; // Station scouters are assigned to
    public Scouter[] scouters; // List of the two scouters

    public StandRotation(LocalDate date, LocalTime startTime, LocalTime endTime, String station, Scouter[] scouters) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.station = station;
        this.scouters = scouters;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Scouter[] getScouters() {
        return scouters;
    }

    public String getStation() {
        return station;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setScouters(Scouter[] scouters) {
        this.scouters = scouters;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
