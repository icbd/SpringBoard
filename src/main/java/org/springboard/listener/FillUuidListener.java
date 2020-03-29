package org.springboard.listener;


import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.UUID;

/**
 * 如果 uuid 可写(存在且可写), 并且 uuid 尚未赋值, 则自动填充 uuid
 */
@Component
public class FillUuidListener {

    private static final String UUID_FIELD = "uuid";

    @PrePersist
    protected void fillUuidBeforeCreate(Object entity) throws Exception {
        if (PropertyUtils.isWriteable(entity, UUID_FIELD) &&
                PropertyUtils.getProperty(entity, UUID_FIELD) == null) {

            PropertyUtils.setProperty(entity, UUID_FIELD, UUID.randomUUID().toString());
        }
    }
}
