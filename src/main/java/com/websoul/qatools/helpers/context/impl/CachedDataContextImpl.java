package com.websoul.qatools.helpers.context.impl;

import com.websoul.qatools.helpers.context.ContextManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Context Implementation for all types of Cached Data
 */
@Component
public class CachedDataContextImpl implements ContextManagement {

    private final Logger slf4jLogger = LoggerFactory.getLogger(CachedDataContextImpl.class);


    /**
     * Map to put all type of CachedData types. eg initialCachedData, setupCachedData, runtimeCachedData, etc
     */
    private Map<String, Object> mapContext = new HashMap<String, Object>();

    /**
     * Adds data into context map based on key identifier.
     *
     * @param key   the key of cachedData type
     * @param value the object instance of
     */
    @Override
    public void addContext(String key, Object value) {
        mapContext.put(key, value);
    }

    /**
     * Removes data from context map based on key identifier.
     *
     * @param key identifier for data removal
     */
    @Override
    public void deleteContext(String key) {
        mapContext.remove(key);
    }

    /**
     * Fetches data based on key identifier.
     *
     * @param key identifier for data fetching
     * @return data entry from context map based on key identifier
     */
    @Override
    public Object getObject(String key) {
        return mapContext.get(key);
    }

}
