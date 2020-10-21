package com.chenkj.myspringboot.netty.packet;

import com.chenkj.myspringboot.netty.Command;
import lombok.Data;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 17:38
 */
@Data
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
