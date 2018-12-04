package cn.wsq.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WSServer {
	/*
	* 单例模式
	* */
	private static class SingletionWSServer {
		static final WSServer instance = new WSServer();
	}
	
	public static WSServer getInstance() {
		return SingletionWSServer.instance;
	}
	
	private EventLoopGroup mainGroup;//是一个处理I0操作的多线程事件循环
	private EventLoopGroup subGroup;
	private ServerBootstrap server;//是一个设置服务器的帮助程序类
	private ChannelFuture future;
	
	public WSServer() {
		mainGroup = new NioEventLoopGroup();
		subGroup = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(mainGroup, subGroup)
			.channel(NioServerSocketChannel.class)//在这里，我们指定使用NioServerSocketChannel用来实例化new的类channel来接受传入连接
			.childHandler(new WSServerInitialzer());//此处指定的处理程序将始终由新接受的计算器进行评估Channel。这ChannelInitializer是一个特殊的处理程序，旨在帮助用户配置新的Channel
	}
	
	public void start() {
		this.future = server.bind(8088);
//		Log.info("netty websocket server 启动完毕...");
		System.out.println("netty websocket server 启动完毕...");
	}
}
