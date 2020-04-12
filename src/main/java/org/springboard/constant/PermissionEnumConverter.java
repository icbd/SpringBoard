package org.springboard.constant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PermissionEnumConverter implements AttributeConverter<PermissionEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PermissionEnum permissionEnum) {
        return permissionEnum.getCode();
    }

    @Override
    public PermissionEnum convertToEntityAttribute(Integer code) {
        return Stream.of(PermissionEnum.values())
                     .filter(e -> code.equals(e.getCode()))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }
}
