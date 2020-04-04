package org.springboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springboard.model.ErrorMessage;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {
    @Builder.Default
    private ZonedDateTime timestamp = ZonedDateTime.now();

    @Builder.Default
    private List<ErrorMessage> errors = new ArrayList<>();
}
