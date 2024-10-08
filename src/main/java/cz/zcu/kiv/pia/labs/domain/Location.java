package cz.zcu.kiv.pia.labs.domain;

import java.math.BigDecimal;

public class Location {
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
}
