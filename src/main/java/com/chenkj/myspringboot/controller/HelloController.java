package com.chenkj.myspringboot.controller;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.sun.tools.javadoc.Start;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author
 * @Description
 * @Date 2020-08-14 17:04
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class HelloController {
    private RateLimiter rateLimiter = RateLimiter.create(10);

    private static final ThreadLocal CURRENT_USER = new ThreadLocal<>();

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
     *
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
        return "你好 " + name;
    }

    /**
     * 浏览器缓存  还有（eTag）
     *
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


    /**
     * ThreadLocal使用  tomcat 使用线程池
     * @param userId
     * @return
     */
    @GetMapping("wrong")
    public Map wrong(@RequestParam("userId") Integer userId) {
        //设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + CURRENT_USER.get();
        //设置用户信息到ThreadLocal
        CURRENT_USER.set(userId);
        //设置用户信息之后再查询一次ThreadLocal中的用户信息
        String after = Thread.currentThread().getName() + ":" + CURRENT_USER.get();
        //汇总输出两次查询结果
        Map result = new HashMap();
        result.put("before", before);
        result.put("after", after);
        return result;
    }

    @GetMapping("right")
    public Map right(@RequestParam("userId") Integer userId) {
        //设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + CURRENT_USER.get();
        //设置用户信息到ThreadLocal
        CURRENT_USER.set(userId);
        try{
            //设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + CURRENT_USER.get();
            //汇总输出两次查询结果
            Map result = new HashMap();
            result.put("before", before);
            result.put("after", after);
            return result;
        }finally {
            CURRENT_USER.remove();
        }
    }
}
