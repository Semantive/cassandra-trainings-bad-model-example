package com.semantive.generator;

import com.datastax.driver.core.utils.UUIDs;
import com.semantive.generator.model.Page;
import com.semantive.generator.model.PageView;
import com.semantive.generator.model.UrlSimilarityScore;

import java.net.InetAddress;
import java.util.*;

class Generator {

    private static Integer APPKEY_LENGTH = 16;
    private static Integer URL_LENGTH = 32;

    private static String DEFAULT_ALPHABET = "qwertyuiopasdfghjklzxcvbnm1234567890";

    private Random random;


    Generator() {
        this.random = new Random();
    }

    Integer intFromRange(Integer min, Integer max) {
        return random.nextInt(max - min) + min;
    }

    private String genString(int l, String alphabet) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < l; ++ i)
            result.append(alphabet.charAt(this.random.nextInt(alphabet.length())));

        return result.toString();
    }

    private String genAppKey() {
        return genString(APPKEY_LENGTH, DEFAULT_ALPHABET);
    }

    Set<String> appKeys(int n) {
        Set<String> results = new HashSet<>(n);

        for(int i = 0; i < n; ++ i)
            results.add(genAppKey());

        return results;
    }

    private Page genPage(String appKey) {
        return new Page(
            genString(URL_LENGTH, DEFAULT_ALPHABET),
            appKey,
            genString(16, DEFAULT_ALPHABET),
            new Date(),
            genString(16, DEFAULT_ALPHABET),
            genString(16, DEFAULT_ALPHABET),
            genString(16, DEFAULT_ALPHABET),
            random.nextBoolean(),
            genString(4096, DEFAULT_ALPHABET),
            genString(1024, DEFAULT_ALPHABET),
            genString(64, DEFAULT_ALPHABET),
            genString(URL_LENGTH, DEFAULT_ALPHABET)
        );
    }

    Set<Page> pages(Set<String> appKeys, int n) {
        Set<Page> results = new HashSet<>(n * appKeys.size());

        for(int i = 0; i < n; ++ i)
            for(String appKey: appKeys)
                results.add(genPage(appKey));

        return results;
    }

    private PageView genView(String appKey) {
        return new PageView(
            appKey,
            UUIDs.timeBased(),
            random.nextInt(1024),
            random.nextInt(16),
            new Date(),
            InetAddress.getLoopbackAddress(),
            genString(URL_LENGTH, DEFAULT_ALPHABET),
            new Date(),
            genString(16, DEFAULT_ALPHABET),
            genString(16, DEFAULT_ALPHABET),
            genString(URL_LENGTH, DEFAULT_ALPHABET),
            genString(64, DEFAULT_ALPHABET)
        );
    }

    Set<PageView> views(Set<String> appKeys, int n) {
        Set<PageView> results = new HashSet<>(n * appKeys.size());

        for(int i = 0; i < n; ++ i)
            for(String appKey: appKeys)
                results.add(genView(appKey));

        return results;
    }

    private UrlSimilarityScore genUrlSimilarityScore(String appKey) {
        return new UrlSimilarityScore(
            appKey,
            genString(URL_LENGTH, DEFAULT_ALPHABET),
            genString(URL_LENGTH, DEFAULT_ALPHABET),
            random.nextFloat()
        );
    }

    Set<UrlSimilarityScore> scores(Set<String> appKeys, int n) {
        Set<UrlSimilarityScore> results = new HashSet<>(n * appKeys.size());

        for(int i = 0; i < n; ++ i)
            for(String appKey: appKeys)
                results.add(genUrlSimilarityScore(appKey));

        return results;
    }
}
