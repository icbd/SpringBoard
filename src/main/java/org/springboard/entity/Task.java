package org.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {

    @NotNull
    @Type(type = "uuid-char")
    private UUID uuid;

    @OneToOne
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JsonIgnore
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private Task parentTask;

    @NotBlank
    private String title;

    private String description;

    @ApiModelProperty("完成时间 (Task未完成时此值为null)")
    private ZonedDateTime completedAt;
}
