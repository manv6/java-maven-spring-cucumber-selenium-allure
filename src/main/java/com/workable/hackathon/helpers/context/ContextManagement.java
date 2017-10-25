package com.workable.hackathon.helpers.context;

/**
 * Interface for contextManagement - Cache control logic for various data types and structures
 */
public interface ContextManagement {

    /**
     * Adds data into context map based on key identifier.
     *
     * @param key   identifier of data structure
     * @param value data structure to be used in context map
     */
    public void addContext(String key, Object value);

    /**
     * Removes data entry from context map based on key identifier.
     *
     * @param key identifier for data removal
     */
    public void deleteContext(String key);

    /**
     * Fetches data based on key identifier.
     *
     * @param key identifier for data fetching
     * @return data entry from context map based on key identifier
     */
    public Object getObject(String key);

}
