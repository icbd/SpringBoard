package org.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Listing extends BaseEntity {

    @NotNull
    @Type(type = "uuid-char")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Product project;

    @ManyToOne
    @JsonIgnore
    private User creator;

    private String title;

    private String description;
}
