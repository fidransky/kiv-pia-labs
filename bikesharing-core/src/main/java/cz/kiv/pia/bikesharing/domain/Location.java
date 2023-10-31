package cz.kiv.pia.bikesharing.domain;

import java.util.Objects;

/**
 * Common class representing location as a pair of coordinates.
 */
public class Location {
    /**
     * Longitude in defined coordinates system.
     */
    private final Double longitude;
    /**
     * Latitude in defined coordinates system.
     */
    private final Double latitude;

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location(String longitude, String latitude) {
        this.longitude = parseCoordinate(longitude);
        this.latitude = parseCoordinate(latitude);
    }

    private static Double parseCoordinate(String coordinate) {
        return Double.parseDouble(coordinate.replaceAll("N|E|S|W", ""));
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return Objects.equals(longitude, location.longitude) && Objects.equals(latitude, location.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
