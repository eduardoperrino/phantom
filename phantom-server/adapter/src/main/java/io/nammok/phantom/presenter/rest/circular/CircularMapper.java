package io.nammok.phantom.presenter.rest.circular;

import io.nammok.phantom.core.domain.Circular;
import io.nammok.phantom.presenter.rest.entity.CircularResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.stereotype.Component;

@Component
public class CircularMapper {
    private final ModelMapper modelMapper;

    public CircularMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
                .setDestinationNameTransformer(NameTransformers.builder("with"))
                .setDestinationNamingConvention(NamingConventions.builder("with"));
        modelMapper.createTypeMap(Circular.class, CircularResponse.Builder.class, builderConfiguration);
        this.modelMapper = modelMapper;
    }

    public CircularResponse convertEntityToResponse(Circular entity) {
        return modelMapper.map(entity, CircularResponse.Builder.class).build();
    }
}
