package com.test.taxiservice.loginservice.utils;

import java.lang.reflect.InvocationTargetException;

import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfile;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NullAwareBeanUtilsBean{

    public void copyProperty(DriverProfileUpdate parent, DriverProfile anotherParent)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PropertyUtils.describe(parent).entrySet().stream()
                .filter(e -> e.getValue() != null)
                .filter(e -> ! e.getKey().equals("class"))
                .forEach(e -> {
                    try {
                        PropertyUtils.setProperty(anotherParent, e.getKey(), e.getValue());
                    } catch (Exception exception) {
                        log.error("Exception occurred in copying properties");
                    }
                });
    }

}
