package cz.zcu.kiv.pia.labs.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Location implements Serializable {
    private final BigDecimal longitude;
    private final BigDecimal latitude;

    public Location(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location(Double longitude, Double latitude) {
        this(new BigDecimal(longitude), new BigDecimal(latitude));
    }

    public Location(String longitude, String latitude) {
        this(new BigDecimal(longitude), new BigDecimal(latitude));
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location location)) return false;
        return Objects.equals(longitude, location.longitude) && Objects.equals(latitude, location.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }
}
