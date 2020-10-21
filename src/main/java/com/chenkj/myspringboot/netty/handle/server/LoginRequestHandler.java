package com.chenkj.myspringboot.netty.handle.server;

import com.chenkj.myspringboot.netty.Session;
import com.chenkj.myspringboot.netty.codec.PacketCodeC;
import com.chenkj.myspringboot.netty.packet.LoginRequestPacket;
import com.chenkj.myspringboot.netty.packet.LoginResponsePacket;
import com.chenkj.myspringboot.netty.util.LoginUtil;
import com.chenkj.myspringboot.netty.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 18:58
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        ctx.channel().writeAndFlush(login(ctx, loginRequestPacket));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        super.channelInactive(ctx);
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            String userId = UUID.randomUUID().toString();
            LoginUtil.markAsLogin(ctx.channel());
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setSuccess(true);
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
        }
        return loginResponsePacket;
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
