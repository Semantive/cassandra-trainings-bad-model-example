package com.semantive.generator.model;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

public class PageView {

    private String appKey;
    private UUID id;
    private Integer attSec;
    private Integer b;
    private Date clientTimestamp;
    private InetAddress ip;
    private String rawUrl;
    private Date receivedAt;
    private String sessionId;
    private String uid;
    private String url;
    private String useragent;

    public PageView(String appKey, UUID id, Integer attSec, Integer b, Date clientTimestamp, InetAddress ip, String rawUrl, Date receivedAt, String sessionId, String uid, String url, String useragent) {
        this.appKey = appKey;
        this.id = id;
        this.attSec = attSec;
        this.b = b;
        this.clientTimestamp = clientTimestamp;
        this.ip = ip;
        this.rawUrl = rawUrl;
        this.receivedAt = receivedAt;
        this.sessionId = sessionId;
        this.uid = uid;
        this.url = url;
        this.useragent = useragent;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getAttSec() {
        return attSec;
    }

    public void setAttSec(Integer attSec) {
        this.attSec = attSec;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Date getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(Date clientTimestamp) {
        this.clientTimestamp = clientTimestamp;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }
}

