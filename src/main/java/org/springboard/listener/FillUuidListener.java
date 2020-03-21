package org.springboard.listener;


import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.UUID;

@Component
public class FillUuidListener {

    @PrePersist
    protected void fillUuidBeforeCreate(Object entity) throws Exception {
        try {
            if (PropertyUtils.getProperty(entity, "uuid") == null) {
                PropertyUtils.setProperty(entity, "uuid", UUID.randomUUID().toString());
            }
        } catch (Exception e) {
            throw new Exception("No uuid field found on " + entity.getClass().getName());
        }
    }
}
