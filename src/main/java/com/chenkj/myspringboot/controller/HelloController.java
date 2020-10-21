package com.chenkj.myspringboot.controller;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author
 * @Description
 * @Date 2020-08-14 17:04
 */
@RestController
@RequestMapping("/api")
public class HelloController {
    private RateLimiter rateLimiter = RateLimiter.create(10);

    /**
     * 本地缓存
     */
    private final LoadingCache<String, Long> lastModifiedCache = CacheBuilder
            .newBuilder()
            .maximumSize(200)
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .build(
                    new CacheLoader<String, Long>() {
                        @Override
                        public Long load(String key) throws Exception {
                            return System.currentTimeMillis();
                        }
                    }
            );


    /**
     * 限流
     * @param name
     * @return
     */
    @GetMapping("/sayHello")
    public String sayHello(String name) {
        boolean tryAcquire = rateLimiter.tryAcquire(100, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            System.out.println("请求过多，降级处理");
            return "请求过多，降级处理";
        }
        System.out.println(name);
        return "hello " + name;
    }

    /**
     * 浏览器缓存  还有（eTag）
     * @param ifModifiedSince
     * @return
     * @throws Exception
     */

    @GetMapping("/cache")
    public ResponseEntity<String> cache(@RequestHeader(value = "If-Modified-Since", required = false) Date ifModifiedSince) throws Exception {
        long lastModifiedMills = lastModifiedCache.get("lastModified") / 1000 * 1000;
        long now = System.currentTimeMillis() / 1000 * 1000;
        long maxAge = 20;
        if (ifModifiedSince != null && ifModifiedSince.getTime() == lastModifiedMills) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setExpires(now + maxAge * 1000);
            httpHeaders.setDate(now);
            httpHeaders.setCacheControl("max-age=" + maxAge);
            return new ResponseEntity<String>(httpHeaders, HttpStatus.NOT_MODIFIED);
        }
        String helloWorld = "hello world";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl("max-age=" + maxAge);
        httpHeaders.setExpires(now + maxAge * 1000);
        httpHeaders.setLastModified(lastModifiedMills);
        httpHeaders.setDate(now);
        return new ResponseEntity<String>(helloWorld, httpHeaders, HttpStatus.OK);
    }

}
