package com.chenkj.myspringboot.netty;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 11:45
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
}
