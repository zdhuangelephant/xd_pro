package com.xiaodou.protocolnetty.netty.encode;

import com.xiaodou.standard.protocol.MessageAble;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SendEncoder extends MessageToByteEncoder<MessageAble> {

  @Override
  protected void encode(ChannelHandlerContext ctx, MessageAble msg, ByteBuf out) throws Exception {
    
    if (msg.uniqueMessageName()!=null) {
      byte head = "\t".getBytes()[1];
      byte tail = "\t".getBytes()[0];
       
      byte[] name = msg.uniqueMessageName().getBytes();
      byte[] body = msg.messageContent().getBytes();
      
      int headLength = name.length;
      int bodyLength = body.length;
      
      byte[] nameToByte = intToByte(headLength);
      byte[] bodyToByte = intToByte(bodyLength);
      
      out.writeByte(head);
      out.writeBytes(nameToByte);
      out.writeBytes(name);
      out.writeBytes(bodyToByte);
      out.writeBytes(body);
      out.writeByte(tail);
    }
    
  }
  
  private byte[] intToByte(int length) {
    byte[] blength = new byte[2];
    blength[1] = (byte) (length & 0xff);
    blength[0] = (byte) (length >> 8 & 0xff);
    return blength;
  }
  
}
