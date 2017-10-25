package com.websoul.qatools.helpers.config;


import com.websoul.qatools.helpers.context.cache.InitialCachedData;
import com.websoul.qatools.helpers.context.cache.RuntimeCachedData;
import com.websoul.qatools.helpers.context.impl.CachedDataContextImpl;
import com.websoul.qatools.helpers.drivers.browsers.BrowserCachedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Configuration Initializer
 */
@Configuration
public class ConfigInitializer {

    private final Logger slf4jLogger = LoggerFactory.getLogger(ConfigInitializer.class);

    @Autowired
    CachedDataContextImpl cachedDataContext;

    @Value("#{properties['art_file']}")
    private String artFile;

    /**
     * Initializes all required setup
     */
    @PostConstruct
    public void initialize() throws IOException {
        this.initObjects();
    }

    /**
     * Instantiates all cached Objects and puts them into cache context map.
     */
    private void initObjects() throws IOException {
        slf4jLogger.info("\n");
        slf4jLogger.info(printArt());
        slf4jLogger.info("\n");
        cachedDataContext.addContext("initialCachedData", new InitialCachedData());
        cachedDataContext.addContext("runtimeCachedData", new RuntimeCachedData());
        cachedDataContext.addContext("browserCachedData", new BrowserCachedData());
        slf4jLogger.info("Object: initialCachedData initialized");
        slf4jLogger.info("Object: runtimeCachedData initialized");
        slf4jLogger.info("Object: browserCachedData initialized");
    }

    public String printArt() throws IOException {
        if (new File(artFile).exists())
            return new String(Files.readAllBytes(new File(artFile).toPath()), Charset.forName("UTF-8"));
        else
            return  "";
    }

}