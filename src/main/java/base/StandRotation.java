package base;

import java.time.LocalDate;
import java.time.LocalTime;
package base;

public class StandRotation {
    public LocalDate date; // Date of assignment
    public LocalTime time; // Time of assignment
    public String station; // Station scouters are assigned to
    public Scouter[] scouters; // List of the two scouters

    public Scouter(LocalDate date, LocalTime time, String station, Scouter scoutA, Scouter scoutB) {
        this.date = date;
        this.time = time;
        this.station = station;
        this.scouters = [scoutA, scoutB];
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
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

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
