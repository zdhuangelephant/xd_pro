package com.xiaodou.node.paxos.netty.encode;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SendEncoder extends MessageToByteEncoder<String> {

  @Override  
  protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {  
      ByteBuffer headBuffer = ByteBuffer.allocate(2);
      byte[] bytes = msg.getBytes();
      ByteBuffer dataBuffer = ByteBuffer.allocate(bytes.length);
      dataBuffer.put(bytes);
      headBuffer.put(intToByte(bytes.length));
      headBuffer.flip();

      out.writeBytes(headBuffer);
      out.writeBytes(dataBuffer);
      ctx.flush();
  }
  
  private byte[] intToByte(int length) {
    byte[] blength = new byte[2];
    blength[1] = (byte) (length & 0xff);
    blength[0] = (byte) (length >> 8 & 0xff);
    return blength;
  }

}
