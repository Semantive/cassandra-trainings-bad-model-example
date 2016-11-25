package com.semantive.generator.model;

public class UrlSimilarityScore {

    private String appKey;
    private String url1;
    private String url2;
    private Float simScore;

    public UrlSimilarityScore(String appKey, String url1, String url2, Float simScore) {
        this.appKey = appKey;
        this.url1 = url1;
        this.url2 = url2;
        this.simScore = simScore;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public Float getSimScore() {
        return simScore;
    }

    public void setSimScore(Float simScore) {
        this.simScore = simScore;
    }
}
