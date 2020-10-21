package com.chenkj.myspringboot.netty.packet;

import com.chenkj.myspringboot.netty.Command;
import lombok.Data;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 11:46
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
