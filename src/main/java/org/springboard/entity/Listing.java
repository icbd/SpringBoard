package org.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springboard.listener.FillUuidListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(FillUuidListener.class)
public class Listing extends BaseEntity {

    @NotBlank
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Product project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User creator;

    private String title;

    private String description;

    private LocalDateTime deletedAt;
}
