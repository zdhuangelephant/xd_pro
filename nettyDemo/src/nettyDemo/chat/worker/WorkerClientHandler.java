package nettyDemo.chat.worker;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class WorkerClientHandler extends SimpleChannelHandler {

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		String content = (String) e.getMessage();
		System.out.println(""+ new Date().toLocaleString() + "\n" + content);
	}

}