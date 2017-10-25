package com.websoul.qatools.helpers.drivers.browsers;

import com.websoul.qatools.helpers.listeners.ReportingWebDriverEventListener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Static factory for browser instantiation using one of the following drivers:
 * Safari
 * Chrome
 * Firefox
 * Remote
 */
@Service("BrowserFactory")
public class BrowserFactory {

    private final Logger slf4jLogger = LoggerFactory.getLogger(BrowserFactory.class);

    @Value("#{properties['chrome_driver_path']}")
    private String chromeDriverPath;

    @Value("#{properties['gecko_driver_path']}")
    private String geckoDriverPath;

    @Value("#{properties['chrome_extension_path']}")
    private String chrome_extension_path;

    @Value("#{properties['chrome_profile_path']}")
    private String chrome_profile_path;

    @Value("#{properties['chrome_extension_upacked_path']}")
    private String chrome_extension_unpack_path;

    @Value("#{properties['chrome_extension_name']}")
    private String chrome_extension_name;

    @Value("#{properties['platform_name']}")
    private String platform_name;

    @Value("#{properties['firefox_profile']}")
    private String firefox_profile;

    @Value("#{properties['remote_browser_name']}")
    private String browser_name;

    @Value("#{properties['remote_browser_version']}")
    private String browser_version;

    @Value("#{properties['gridlastic_host']}")
    private String gridlastic_host;

    @Value("#{properties['record_video']}")
    private String record_video;

    @Value("#{properties['video_s3_path']}")
    private String video_s3_path;

    private RemoteWebDriver driver;

    @Autowired
    BrowserCachedData browserCachedData;

    String videoLink;


    /**
     * Static factory for Safari browser instantiation
     *
     * @return Instance of webDriver for Safari
     */
    private WebDriver createSafariDriver() {
        SafariOptions options = new SafariOptions().setUseCleanSession(true);
        return new SafariDriver(options);
    }

    /**
     * Static factory for Chrome browser instantiation
     *
     * @return Instance of webDriver for Chrome
     */
    private WebDriver createChromeDriver() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList("--window-size=1920,1080"));
        if (chrome_profile_path != null)
            options.addArguments("user-data-dir=" + chrome_profile_path);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        // video record
        if (record_video.equalsIgnoreCase("true")) {
            //TO DO VIDEO RECORDER FOR LOCAL TEST EXECUTION
        }

        return new ChromeDriver();

    }

    /**
     * Static factory for Firefox browser instantiation
     *
     * @return Instance of webDriver for Firefox
     */
    private WebDriver createFirefoxDriver() {
        ProfilesIni allProfiles = new ProfilesIni();
        if (firefox_profile != null && !firefox_profile.isEmpty()) {
            FirefoxOptions ffoptions = new FirefoxOptions();
            return new FirefoxDriver(ffoptions);
        } else {
            return new FirefoxDriver();
        }
    }


    /**
     * Static factory for Remote browser instantiation
     *
     * @return Instance of webDriver for Remote
     */
    private RemoteWebDriver createRemoteDriver() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (platform_name.equalsIgnoreCase("win7")) {
            capabilities.setPlatform(Platform.VISTA);
        }
        if (platform_name.equalsIgnoreCase("win8")) {
            capabilities.setPlatform(Platform.WIN8);
        }
        if (platform_name.equalsIgnoreCase("win8_1")) {
            capabilities.setPlatform(Platform.WIN8_1);
        }
        if (platform_name.equalsIgnoreCase("linux")) {
            capabilities.setPlatform(Platform.LINUX);
        }
        if (platform_name.equalsIgnoreCase("win10")) {
            capabilities.setPlatform(Platform.WIN10);
        }
        capabilities.setBrowserName(browser_name);
        capabilities.setVersion(browser_version);

        // video record
        if (record_video.equalsIgnoreCase("true")) {
            capabilities.setCapability("video", "True"); // NOTE: "True" is a case sensitive string, not boolean.
        } else {
            capabilities.setCapability("video", "False"); // NOTE: "False" is a case sensitive string, not boolean.
        }

        if (browser_name.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (chrome_extension_path != null) {
                String directUrl = chrome_extension_path;
                options.addExtensions(new File(directUrl));
            }

            // On Linux start-maximized does not expand browser window to max screen size. Always set a window size.
            if (platform_name.equalsIgnoreCase("linux")) {
                options.addArguments(Arrays.asList("--window-size=1920,1080"));
            } else {
                options.addArguments(Arrays.asList("--start-maximized"));
            }
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }


        driver = new RemoteWebDriver(new URL(gridlastic_host), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        if (platform_name.equalsIgnoreCase("linux") && browser_name.equalsIgnoreCase("firefox")) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }


        if (record_video.equalsIgnoreCase("true")) {
            videoLink = video_s3_path + ((RemoteWebDriver) driver).getSessionId();
            browserCachedData.setVideoLink(videoLink);
            slf4jLogger.info("Test Video: " + video_s3_path + ((RemoteWebDriver) driver).getSessionId());
        }
        return driver;
    }


    /**
     * Initialization of driver for the below setup properties:
     * 1. Timeout
     * 2. Browser position
     */
    private static void initBrowserSetup(WebDriver driver) {

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        // driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
    }

    /**
     * Instantiates webDriver based on input browser
     *
     * @return Instance of webDriver based on input browser
     */
    public WebDriver initBrowser(Browsers browser) throws IOException {
        WebDriver driver;
        switch (browser) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                driver = createChromeDriver();
                break;
            case SAFARI:
                driver = createSafariDriver();
                break;
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", geckoDriverPath);
                driver = createFirefoxDriver();
                break;
            case OPERA:
                driver = createOperaDriver();
                break;
            case EDGE:
                System.setProperty("webdriver.gecko.driver", geckoDriverPath);
                driver = createEdgeDriver();
                break;
            case REMOTE:
                driver = createRemoteDriver();
                break;
            default:
                System.setProperty("webdriver.gecko.driver", geckoDriverPath);
                driver = createFirefoxDriver();
                break;
        }
        EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
        eventDriver.register(new ReportingWebDriverEventListener());
        initBrowserSetup(eventDriver);
        CucumberTestContext.setWebDriver(driver);
        return eventDriver;
    }

    /**
     * Static factory for Edge browser instantiation
     *
     * @return Instance of webDriver for Edge
     */
    private WebDriver createEdgeDriver() {
        DesiredCapabilities capability = DesiredCapabilities.edge();
        capability.setBrowserName("MicrosoftEdge");
        capability.setPlatform(Platform.WIN10);
        driver = new EdgeDriver(capability);
        return driver;
    }

    /**
     * Static factory for Opera browser instantiation
     *
     * @return Instance of webDriver for Opera
     */
    private WebDriver createOperaDriver() {
        OperaOptions options = new OperaOptions();
        driver = new OperaDriver(options);
        return  driver;
    }
}
