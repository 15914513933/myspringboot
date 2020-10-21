package com.chenkj.myspringboot.netty;

import com.chenkj.myspringboot.netty.codec.PacketDecoder;
import com.chenkj.myspringboot.netty.codec.PacketEncoder;
import com.chenkj.myspringboot.netty.codec.Spliter;
import com.chenkj.myspringboot.netty.handle.client.LoginResponseHandler;
import com.chenkj.myspringboot.netty.handle.client.MessageResponseHandler;
import com.chenkj.myspringboot.netty.packet.LoginRequestPacket;
import com.chenkj.myspringboot.netty.packet.MessageRequestPacket;
import com.chenkj.myspringboot.netty.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;

/**
 * @Author
 * @Description
 * @Date 2020-09-28 11:13
 */
public class NettyClient2 {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        // 4.建立连接
        connect(bootstrap, "127.0.0.1", 8888, 3);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
                Channel channel = ((ChannelFuture) future).channel();

                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);
            } else {
                System.err.println("连接失败!");
            }
        });
    }

    private static void login(Channel channel){
        System.out.println(new Date() + ": 客户端写出数据");
        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserName("chenkj");
        loginRequestPacket.setPassword("123456");
        channel.writeAndFlush(loginRequestPacket);
    }

    private static void startConsoleThread(Channel channel) {
        Scanner sc = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    String line = sc.nextLine();
                    if (line.equals("exit")) {
                        System.exit(0);
                    }
                    String toUserId = line;
                    String message = sc.nextLine();
                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    messageRequestPacket.setToUserId(toUserId);
                    messageRequestPacket.setMessage(message);
                    channel.writeAndFlush(messageRequestPacket);
                }else{
                    System.out.print("输入用户名登录: ");
                    String username = sc.nextLine();
                    loginRequestPacket.setUserName(username);

                    // 密码使用默认的
                    loginRequestPacket.setPassword("pwd");

                    // 发送登录数据包
                    channel.writeAndFlush(loginRequestPacket);
                    waitForLoginResponse();
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
