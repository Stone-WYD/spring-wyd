package com.test.demowyd.wyd.spring_netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: spring-wyd
 * @description: netty 服务器
 * @author: Stone
 * @create: 2023-10-18 15:04
 **/
@Slf4j
@Component
public class NettyServer {

    @PostConstruct
    public void init(){
        new Thread(this::start).start();
    }

    private void start(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) {
                            channel.pipeline().addLast(new LoggingHandler());
                            channel.pipeline().addLast(new HttpServerCodec());
                            channel.pipeline().addLast(new SimpleChannelInboundHandler<DefaultHttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, DefaultHttpRequest msg) {
                                    log.debug("{}", msg.uri());
                                    QueryStringDecoder decoder = new QueryStringDecoder(msg.uri());

                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                    byte[] bytes = ("<h1>hello,world! port:8888" + "</h1>").getBytes();
                                    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
                                    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
                                    response.content().writeBytes(bytes);
                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        }catch ( Exception e){
            log.error("Server error:" , e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
