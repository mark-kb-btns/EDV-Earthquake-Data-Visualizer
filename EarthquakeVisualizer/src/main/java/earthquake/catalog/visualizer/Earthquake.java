package earthquake.catalog.visualizer;

public class Earthquake {
    private String location;
    private double magnitude;
    private String time;
    private String date;

    public Earthquake(String location, double magnitude, String isoDate, String isoTime) {
        this.location = location;
        this.magnitude = magnitude;
        this.time = isoTime;
        this.date = isoDate;
    }

    // Getter for the location
    public String getLocation() {
        return location;
    }

    // Setter for the location
    public void setLocation(String location) {
        this.location = location;
    }

    // Getter for the magnitude
    public double getMagnitude() {
        return magnitude;
    }

    // Setter for the magnitude
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    // Getter for the time
    public String getTime() {
        return time;
    }

    // Getter for the date
    public String getDate() {
        return date;
    }

    // Setter for the time
    public void setTime(String time) {
        this.time = time;
    }
}
