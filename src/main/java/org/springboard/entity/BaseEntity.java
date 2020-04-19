package org.springboard.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseEntity extends BaseEntityWithoutDeletedAt {

    private ZonedDateTime deletedAt;
}
