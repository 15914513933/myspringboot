package com.chenkj.myspringboot.netty.handle.client;

import com.chenkj.myspringboot.netty.packet.LoginRequestPacket;
import com.chenkj.myspringboot.netty.packet.LoginResponsePacket;
import com.chenkj.myspringboot.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 18:58
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println("你的userId:" + loginResponsePacket.getUserId());
            System.out.println(new Date() + ": 客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

}
