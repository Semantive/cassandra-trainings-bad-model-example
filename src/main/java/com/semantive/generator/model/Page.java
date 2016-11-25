package com.semantive.generator.model;

import java.util.Date;

public class Page {

    private String cleanedUrl;
    private String appKey;
    private String attributes;
    private Date createdAt;
    private String imageUrl;
    private String keywords;
    private String rawHtml;
    private Boolean recommendable;
    private String content;
    private String contentTruncated;
    private String title;
    private String url;

    public Page(String cleanedUrl, String appKey, String attributes, Date createdAt, String imageUrl, String keywords, String rawHtml, Boolean recommendable, String content, String contentTruncated, String title, String url) {
        this.cleanedUrl = cleanedUrl;
        this.appKey = appKey;
        this.attributes = attributes;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
        this.keywords = keywords;
        this.rawHtml = rawHtml;
        this.recommendable = recommendable;
        this.content = content;
        this.contentTruncated = contentTruncated;
        this.title = title;
        this.url = url;
    }

    public String getCleanedUrl() {
        return cleanedUrl;
    }

    public void setCleanedUrl(String cleanedUrl) {
        this.cleanedUrl = cleanedUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRawHtml() {
        return rawHtml;
    }

    public void setRawHtml(String rawHtml) {
        this.rawHtml = rawHtml;
    }

    public Boolean getRecommendable() {
        return recommendable;
    }

    public void setRecommendable(Boolean recommendable) {
        this.recommendable = recommendable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentTruncated() {
        return contentTruncated;
    }

    public void setContentTruncated(String contentTruncated) {
        this.contentTruncated = contentTruncated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
