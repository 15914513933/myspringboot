package com.chenkj.myspringboot.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;
import lombok.Data;

/**
 * @Author
 * @Description
 * @Date 2020-09-30 11:02
 */
public class JsonUtil {
    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.disableHtmlEscaping();
    }

    @Data
    static class Person{
        private String id;
        private String name;
        Person(){}
        Person(String id,String name){
            this.id = id;
            this.name = name;
        }
    }

    public static String object2Json(Object object) {
        return gsonBuilder.create().toJson(object);
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    public static void main(String[] args) {
        String json = object2Json(new Person("123","chenkj"));
        System.out.println(json);

        Person person = json2Object(json, Person.class);
        System.out.println(person.getName());

    }
}
