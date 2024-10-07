package cz.zcu.kiv.pia.labs.service;

/**
 * Implementation of {@link NumberService} returning constant number.
 */
public class ConstantNumberService implements NumberService {
    private final Number number;

    public ConstantNumberService(Number number) {
        this.number = number;
    }

    @Override
    public Number getNumber() {
        return number;
    }
}
