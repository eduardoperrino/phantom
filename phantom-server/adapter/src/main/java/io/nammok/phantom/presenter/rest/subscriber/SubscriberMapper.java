package io.nammok.phantom.presenter.rest.subscriber;

import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.presenter.rest.entity.SubscriberResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.stereotype.Component;

@Component
public class SubscriberMapper {
    private final ModelMapper modelMapper;

    public SubscriberMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
                .setDestinationNameTransformer(NameTransformers.builder("with"))
                .setDestinationNamingConvention(NamingConventions.builder("with"));
        modelMapper.createTypeMap(Subscriber.class, SubscriberResponse.Builder.class, builderConfiguration);
        this.modelMapper = modelMapper;
    }

    public SubscriberResponse convertEntityToResponse(Subscriber entity) {
        return modelMapper.map(entity, SubscriberResponse.Builder.class).build();
    }
}
