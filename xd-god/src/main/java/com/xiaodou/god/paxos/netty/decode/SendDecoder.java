package com.xiaodou.god.paxos.netty.decode;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.json.JsonObjectDecoder;

public class SendDecoder extends JsonObjectDecoder {  
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if (in.readableBytes() < 2) {
      return;
    }

    in.markReaderIndex();
    
//    int length = buf.readShort();

    byte[] blength = new byte[2];
    in.readBytes(blength);
    int length = byteToInt(blength);
    blength = null;

    if (in.readableBytes() < length) {
      in.resetReaderIndex();
      return;
    }
//    byte[] newbyte=new byte[length];
//    in.readBytes(newbyte, 0, length);
//    return new String(newbyte);
    super.decode(ctx, in, out);
  }

  private int byteToInt(byte[] length) {
    int i = 0;
    i += length[1] & 0xff;
    i += (length[0] & 0xff) << 8;
    return i;
  }
}
