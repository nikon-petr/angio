package com.angio.angiobackend.analyse.mappers;

import com.angio.angiobackend.analyse.dto.PatientDto;
import com.angio.angiobackend.analyse.entities.PatientEntity;
import com.angio.angiobackend.analyse.requests.PatientRequest;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.getConverterFactory()
                .registerConverter("simpleIsoDateConverter", new DateToStringConverter("dd-MM-yyyy"));

        // TODO remove when complete rest v2
        factory.classMap(PatientEntity.class, PatientRequest.class)
                .field("locationAddress", "address")
                .field("workAddress", "work")

                .fieldMap("bday", "bday")
                .converter("simpleIsoDateConverter")
                .mapNulls(true)
                .mapNullsInReverse(true)
                .add()

                .byDefault()
                .register();

        factory.classMap(PatientEntity.class, PatientDto.class)
                .byDefault()
                .register();
    }
}
