package cz.zcu.kiv.pia.labs.converter.rest;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.model.DamageDTO;
import org.springframework.core.convert.converter.Converter;

public class DamageConverter implements Converter<Damage, DamageDTO> {
    @Override
    public DamageDTO convert(Damage source) {
        return new DamageDTO()
                .id(source.getId())
                .description(source.getDescription())
                // other mappings here
                ;
    }
}
