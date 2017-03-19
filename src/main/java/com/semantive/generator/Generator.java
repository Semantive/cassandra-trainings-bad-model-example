package com.semantive.generator;

import com.datastax.driver.core.utils.UUIDs;
import com.semantive.generator.model.Page;
import com.semantive.generator.model.PageView;
import com.semantive.generator.model.UrlSimilarityScore;

import java.net.InetAddress;
import java.util.*;

public class Generator {

    private static Integer APPKEY_LENGTH = 4;
    private static Integer URL_LENGTH = 16;

    private static String CONSONANTS = "qwrtpsdfghjklzxcvbnm";
    private static String VOWELS = "eyuioa";

    private Random random;


    public Generator() {
        this.random = new Random();
    }

    public Integer intFromRange(Integer min, Integer max) {
        return random.nextInt(max - min) + min;
    }

    private String genString(int l) {
        StringBuilder result = new StringBuilder();
        String alphabets[] = { CONSONANTS, VOWELS };

        for(int i = 0; i < l; ++ i) {
            result.append(alphabets[i % 2].charAt(this.random.nextInt(alphabets[i % 2].length())));
        }


        return result.toString();
    }

    private String genAppKey() {
        return genString(APPKEY_LENGTH);
    }

    public Set<String> appKeys(int n) {
        Set<String> results = new HashSet<>(n);

        for(int i = 0; i < n; ++ i)
            results.add(genAppKey());

        return results;
    }

    private Page genPage(String appKey) {
        return new Page(
            genString(URL_LENGTH),
            appKey,
            genString(16),
            new Date(),
            genString(16),
            genString(16),
            genString(16),
            random.nextBoolean(),
            genString(4096),
            genString(1024),
            genString(64),
            genString(URL_LENGTH)
        );
    }

    public Set<Page> pages(Set<String> appKeys, int n) {
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
            genString(URL_LENGTH),
            new Date(),
            genString(16),
            genString(16),
            genString(URL_LENGTH),
            genString(64)
        );
    }

    public Set<PageView> views(Set<String> appKeys, int n) {
        Set<PageView> results = new HashSet<>(n * appKeys.size());

        for(int i = 0; i < n; ++ i)
            for(String appKey: appKeys)
                results.add(genView(appKey));

        return results;
    }

    private UrlSimilarityScore genUrlSimilarityScore(String appKey) {
        return new UrlSimilarityScore(
            appKey,
            genString(URL_LENGTH),
            genString(URL_LENGTH),
            random.nextFloat()
        );
    }

    public Set<UrlSimilarityScore> scores(Set<String> appKeys, int n) {
        Set<UrlSimilarityScore> results = new HashSet<>(n * appKeys.size());

        for(int i = 0; i < n; ++ i)
            for(String appKey: appKeys)
                results.add(genUrlSimilarityScore(appKey));

        return results;
    }
}
