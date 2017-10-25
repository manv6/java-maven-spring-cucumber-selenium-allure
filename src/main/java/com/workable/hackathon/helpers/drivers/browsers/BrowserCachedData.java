package com.workable.hackathon.helpers.drivers.browsers;

import org.springframework.stereotype.Service;


/**
 * Created by manolisvlastos on 11/07/16.
 */
@Service
public class BrowserCachedData {
    String videoLink;

    String windowHandle;

    String userEmail;
    String userId;
    String accountId;
    String memberId;
    private String extension = "default";

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getWindowHandle() {
        return windowHandle;
    }

    public void setWindowHandle(String windowHandle) {
        this.windowHandle = windowHandle;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public void setLoggedInUserEmail(String loggedInUserEmail) {
        userEmail = loggedInUserEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getExtension() {
        return extension;
    }
}
