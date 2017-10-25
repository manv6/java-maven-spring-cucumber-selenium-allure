package com.workable.hackathon.helpers.config;


import com.workable.hackathon.helpers.drivers.browsers.BrowserCachedData;
import com.workable.hackathon.helpers.context.cache.InitialCachedData;
import com.workable.hackathon.helpers.context.cache.RuntimeCachedData;
import com.workable.hackathon.helpers.context.impl.CachedDataContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configuration Initializer
 */
@Configuration
public class ConfigInitializer {

    private final Logger slf4jLogger = LoggerFactory.getLogger(ConfigInitializer.class);

    @Autowired
    CachedDataContextImpl cachedDataContext;

    /**
     * Initializes all required setup
     */
    @PostConstruct
    public void initialize() {
        this.initObjects();
    }

    /**
     * Instantiates all cached Objects and puts them into cache context map.
     */
    private void initObjects() {
        slf4jLogger.info(printArt());
        cachedDataContext.addContext("initialCachedData", new InitialCachedData());
        cachedDataContext.addContext("runtimeCachedData", new RuntimeCachedData());
        cachedDataContext.addContext("browserCachedData", new BrowserCachedData());
        slf4jLogger.info("Object: initialCachedData initialized");
        slf4jLogger.info("Object: runtimeCachedData initialized");
        slf4jLogger.info("Object: browserCachedData initialized");

    }

    public String printArt() {
        return "\n _____          _     ___             __           ____   ___  _ _____ \n" +
                "/__   \\___  ___| |_  / __\\___  _ __  / _|         |___ \\ / _ \\/ |___  |\n" +
                "  / /\\/ _ \\/ __| __|/ /  / _ \\| '_ \\| |_   _____    __) | | | | |  / / \n" +
                " / / |  __/\\__ \\ |_/ /__| (_) | | | |  _| |_____|  / __/| |_| | | / /  \n" +
                " \\/   \\___||___/\\__\\____/\\___/|_| |_|_|           |_____|\\___/|_|/_/   \n" +
                "                                                                       \n" +
                "                  _         _   _                                      \n" +
                "  /\\  /\\__ _  ___| | ____ _| |_| |__   ___  _ __                       \n" +
                " / /_/ / _` |/ __| |/ / _` | __| '_ \\ / _ \\| '_ \\                      \n" +
                "/ __  / (_| | (__|   < (_| | |_| | | | (_) | | | |                     \n" +
                "\\/ /_/ \\__,_|\\___|_|\\_\\__,_|\\__|_| |_|\\___/|_| |_|                     \n" +
                "                                                                       \n" +
                " _____          _   _                                                  \n" +
                "/__   \\___  ___| |_(_)_ __   __ _                                      \n" +
                "  / /\\/ _ \\/ __| __| | '_ \\ / _` |                                     \n" +
                " / / |  __/\\__ \\ |_| | | | | (_| |                                     \n" +
                " \\/   \\___||___/\\__|_|_| |_|\\__, |                                     \n" +
                "                            |___/                                      \n" +
                "   ___                                            _                    \n" +
                "  / __\\ __ __ _ _ __ ___   _____      _____  _ __| | __                \n" +
                " / _\\| '__/ _` | '_ ` _ \\ / _ \\ \\ /\\ / / _ \\| '__| |/ /                \n" +
                "/ /  | | | (_| | | | | | |  __/\\ V  V / (_) | |  |   <                 \n" +
                "\\/   |_|  \\__,_|_| |_| |_|\\___| \\_/\\_/ \\___/|_|  |_|\\_\\                \n" +
                "                                                                       ";

    }

}