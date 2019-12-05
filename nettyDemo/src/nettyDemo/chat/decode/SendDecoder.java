package nettyDemo.chat.decode;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class SendDecoder extends FrameDecoder {


  protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buf)
      throws Exception {

    if (buf.readableBytes() < 2) {
      return null;
    }

    buf.markReaderIndex();
    
//    int length = buf.readShort();

    byte[] blength = new byte[2];
    buf.readBytes(blength);
    int length = byteToInt(blength);
    blength = null;

    if (buf.readableBytes() < length) {
      buf.resetReaderIndex();
      return null;
    }
    byte[] newbyte=new byte[length];
    buf.readBytes(newbyte, 0, length);
    return new String(newbyte);
  }

  private int byteToInt(byte[] length) {
    int i = 0;
    i += length[1] & 0xff;
    i += (length[0] & 0xff) << 8;
    return i;
  }
}
