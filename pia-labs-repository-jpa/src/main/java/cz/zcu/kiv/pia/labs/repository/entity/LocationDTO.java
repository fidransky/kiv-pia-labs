package cz.zcu.kiv.pia.labs.repository.entity;

import cz.zcu.kiv.pia.labs.domain.Location;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class LocationDTO {
    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    public LocationDTO() {
    }

    public LocationDTO(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    public LocationDTO(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
