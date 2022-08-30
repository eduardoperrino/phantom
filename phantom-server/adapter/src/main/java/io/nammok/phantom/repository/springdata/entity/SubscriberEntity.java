package io.nammok.phantom.repository.springdata.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"name", "email"})
@Builder(builderClassName = "Builder", setterPrefix = "with", toBuilder = true)
@Entity

public class SubscriberEntity {
    @Id
    String id;
    String name;
    String email;
}
