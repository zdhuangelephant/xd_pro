package com.xiaodou.protocolnetty.netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.xiaodou.protocolnetty.message.SimpleMessage;

/**
 * @name SendDecoder CopyRright (c) 2018 by
 *       <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月23日
 * @description netty所用解码decode
 * @version 1.0
 */
public class SendDecoder extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
     SimpleMessage message = new SimpleMessage();
    
    if (in.readableBytes()>3) {
       return;
    }
    //head
    in.readByte();
    in.markReaderIndex();

    // nameLength
    byte[] headLengthByte = new byte[2];
    
    //name
    int headLength = byteToInt(headLengthByte);
    in.readBytes(headLength);
    if (in.readableBytes()>headLength) {
      byte[] head = new byte[headLength];
      String messageName = new String(head);
      message.setUniqueMessageName(messageName);
    }
    
    //体长度
    byte[] bodyLengthByte = new byte[2];
    int bodyLength = byteToInt(bodyLengthByte);
    in.readBytes(bodyLength);
    
    //消息体
    if (in.readableBytes()> bodyLength) {
      byte[] body = new byte[bodyLength];
      String content = new String(body);
      message.setMessageContent(content);
    }
    
    //tail
    in.readByte();
    out.add(message);
  }

  private int byteToInt(byte[] length) {
    int i = 0;
    i += length[1] & 0xff;
    i += (length[0] & 0xff) << 8;
    return i;
  }
}
