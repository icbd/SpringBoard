package org.springboard.listener;


import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.UUID;

@Component
public class FillUuidListener {

    private static final String UUID_FIELD = "uuid";

    @PrePersist
    protected void fillUuidBeforeCreate(Object entity) throws Exception {
        if (PropertyUtils.isWriteable(entity, UUID_FIELD) &&
                PropertyUtils.getProperty(entity, "uuid") == null) {
            PropertyUtils.setProperty(entity, "uuid", UUID.randomUUID().toString());
        }
    }
}
