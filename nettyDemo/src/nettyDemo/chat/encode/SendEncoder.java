package nettyDemo.chat.encode;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;

public class SendEncoder extends SimpleChannelDownstreamHandler {

  @Override
  public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    ByteBuffer headBuffer = ByteBuffer.allocate(2);
    ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
    dataBuffer.writeBytes(e.getMessage().toString().getBytes());
    int readableBytes = dataBuffer.readableBytes();
    headBuffer.put(intToByte(readableBytes));
    headBuffer.flip();

    ChannelBuffer totalBuffer = ChannelBuffers.dynamicBuffer();
    totalBuffer.writeBytes(headBuffer);
    totalBuffer.writeBytes(dataBuffer);
    Channels.write(ctx, e.getFuture(), totalBuffer);

  }

  private byte[] intToByte(int length) {
    byte[] blength = new byte[2];
    blength[1] = (byte) (length & 0xff);
    blength[0] = (byte) (length >> 8 & 0xff);
    return blength;
  }

}
