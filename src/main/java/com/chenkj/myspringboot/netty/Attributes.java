package com.chenkj.myspringboot.netty;

import io.netty.util.AttributeKey;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 17:43
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
