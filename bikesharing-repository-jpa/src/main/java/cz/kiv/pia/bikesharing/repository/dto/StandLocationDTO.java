package cz.kiv.pia.bikesharing.repository.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Bike stand location.
 */
@Embeddable
public class StandLocationDTO {
    /**
     * Longitude in defined coordinates system.
     */
    @Column
    private String longitude;
    /**
     * Latitude in defined coordinates system.
     */
    @Column
    private String latitude;

    public StandLocationDTO() {
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
