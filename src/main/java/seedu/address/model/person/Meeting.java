package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's meetings in the address book.
 */
public class Meeting implements Comparable<Meeting> {
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructor for Meeting
     *
     * @param start start date and time of meeting
     * @param end   end date and time of meeting
     */
    public Meeting(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Default Constructor that generates an empty meeting
     */
    public Meeting() {
        this.start = LocalDateTime.MAX;
        this.end = LocalDateTime.MAX;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Checks if there are timing clashes between this meeting and m
     * Returns true if timings do clash, false otherwise
     *
     * @param m Meeting to be compared with
     */
    public boolean checkTimeClash(Meeting m) {
        //if m starts and end at the same time
        if (m.start.isEqual(this.start) && m.end.isEqual(this.end)) {
            return true;
        }
        //if m ends at this.start
        if (m.end.isEqual(this.start)) {
            return true;
        }
        //if m ends in between
        if (m.end.isAfter(this.start) & m.end.isBefore(this.end)) {
            return true;
        }
        //if m starts at the same time
        if (m.start.isEqual(this.start)) {
            return true;
        }
        //if m starts in between
        if (m.start.isAfter(this.start) & m.start.isBefore(this.end)) {
            return true;
        }
        //if m starts at the end
        if (m.start.isEqual(this.end)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Meeting otherMeeting) {
        if (this.start.isBefore(otherMeeting.start)) {
            return -1;
        } else if (this.start.equals(otherMeeting.start)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Meeting)) {
            return false;
        }
        Meeting mt = (Meeting) o;
        return mt.start.equals(this.start) && mt.end.equals(this.end);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "No Meeting!";
        }

        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("eeee MMMM d u HH:mm");
        String startDnT = start.format(customFormat);
        String endDnT = end.format(customFormat);

        return "(Starts at: " + startDnT + " Ends at: " + endDnT + ")";
    }

    @Override
    public int compareTo(Meeting otherMeeting) {
        if (this.start.isBefore(otherMeeting.start)) {
            return -1;
        } else if (this.start.equals(otherMeeting.start)) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isEmpty() {
        return this.start.isEqual(LocalDateTime.MAX)
            && this.end.isEqual(LocalDateTime.MAX);
    }

    public boolean isCorrectPeriod() {
        return end.isBefore(start);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
