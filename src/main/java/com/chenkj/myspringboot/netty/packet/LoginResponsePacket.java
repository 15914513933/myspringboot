package com.chenkj.myspringboot.netty.packet;

import com.chenkj.myspringboot.netty.Command;
import lombok.Data;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 14:29
 */
@Data
public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;
    private String userId;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
