package com.workable.hackathon.helpers.context.cache;

import com.workable.hackathon.helpers.utils.CommonTools;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * List of properties to be cached during runtime
 */
@Service
public class RuntimeCachedData {

    @Autowired
    CommonTools commonTools;



    private final Logger slf4jLogger = LoggerFactory.getLogger(RuntimeCachedData.class);
    String jsonString;
    private String movieFile;
    WebDriver webDriver;
    String candidateName;
    boolean angelistLoggedIn = false;
    boolean dribbbleLoggedIn = false;
    boolean facebookLoggedIn = false;
    private String previousPhone;
    private String currentPhone;
    private String imageMD5;
    private String initialImageMD5;
    private String authToken;
    private String refreshToken;
    private String candidateId;
    private String accountId;
    private String userID;
    private String ngrokDomain;
    private String clipperVersion;
    private String ngrokDomainForCannedServer;
    private String uid;
    private long messageEnqueueTime;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPreviousEmail() {
        return previousEmail;
    }

    public void setPreviousEmail(String previousEmail) {
        this.previousEmail = previousEmail;
    }

    boolean searchBatchMode = false;

    String previousEmail;
    String currentEmail;

    public boolean isSearchBatchMode() {
        return searchBatchMode;
    }

    public void setSearchBatchMode(boolean searchBatchMode) {
        this.searchBatchMode = searchBatchMode;
    }

    public void setAngelistLoggedIn(boolean angelistLoggedIn) {
        this.angelistLoggedIn = angelistLoggedIn;
    }

    public void setDribbbleLoggedIn(boolean dribbbleLoggedIn) {
        this.dribbbleLoggedIn = dribbbleLoggedIn;
    }

    public void setFacebookLoggedIn(boolean facebookLoggedIn) {
        this.facebookLoggedIn = facebookLoggedIn;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getJsonString() {
        return jsonString;
    }

    public String getMovieFile() {
        return movieFile;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    String username;
    String password;
    String cvPathUploaded;
    private String cvFileName;

    public String getCvPathUploaded() {
        return cvPathUploaded;
    }

    public void setCvPathUploaded(String cvPathUploaded) {
        this.cvPathUploaded = cvPathUploaded;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCvFileName(String cvFileName) {
        this.cvFileName = cvFileName;
    }

    public String getCvFileName() {
        return cvFileName;
    }

    public void setMovieFile(String movieFile) {
        this.movieFile = movieFile;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public boolean isAngelistLoggedIn() {
        return angelistLoggedIn;
    }

    public boolean isDribbbleLoggedIn() {
        return dribbbleLoggedIn;
    }

    public boolean isFacebookLoggedIn() {
        return facebookLoggedIn;
    }

    public void setCurrentEmail(String primaryEmail) {
        this.currentEmail = primaryEmail;
    }

    public String getCurrentEmail() {
        return this.currentEmail;
    }

    public void setPreviousPhone(String previousPhone) {
        this.previousPhone = previousPhone;
    }

    public void setCurrentPhone(String currentPhone) {
        this.currentPhone = currentPhone;
    }

    public String getPreviousPhone() {
        return previousPhone;
    }

    public String getCurrentPhone() {
        return currentPhone;
    }


    public String getInitialImageMD5() {
        return this.initialImageMD5;
    }

    public String getImageMD5() {
        return this.imageMD5;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
        slf4jLogger.info("Authentication token " +this.authToken);
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserID() {
        return userID;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setNgrokDomain(String ngrokDomain) {
        this.ngrokDomain = ngrokDomain;
    }

    public String getNgrokDomain() {
        return ngrokDomain;
    }

    public String getClipperVersion() {
        return clipperVersion;
    }


    public void setClipperVersion(String clipperVersion) {
        this.clipperVersion = clipperVersion;
    }

    public String getNgrokDomainForCannedServer() {
        return ngrokDomainForCannedServer;
    }

    public void setNgrokDomainForCannedServer(String ngrokDomainForCannedServer) {
        this.ngrokDomainForCannedServer = ngrokDomainForCannedServer;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setMessageEnqueueTime(long messageEnqueueTime) {
        this.messageEnqueueTime = messageEnqueueTime;
    }

    public long getMessageEnqueueTime() {
        return messageEnqueueTime;
    }
}

