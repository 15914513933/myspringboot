package com.chenkj.myspringboot.netty.packet;

import lombok.Data;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 11:42
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     */
    public abstract Byte getCommand();
}
