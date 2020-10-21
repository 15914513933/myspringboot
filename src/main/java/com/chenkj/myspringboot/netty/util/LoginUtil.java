package com.chenkj.myspringboot.netty.util;

import com.chenkj.myspringboot.netty.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 17:44
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
