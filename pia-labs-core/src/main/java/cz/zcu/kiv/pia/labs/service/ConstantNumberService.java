package cz.zcu.kiv.pia.labs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link NumberService} returning constant number defined in application.properties "my.constant.number" property.
 */
// register ConstantNumberService as a Spring bean named "myConstantNumberService"
@Service("myConstantNumberService")
public class ConstantNumberService implements NumberService {
    private final Number number;

    public ConstantNumberService(@Value("${my.constant.number}") Number number) {
        this.number = number;
    }

    @Override
    public Number getNumber() {
        return number;
    }
}
