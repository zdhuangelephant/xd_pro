package com.xiaodou.message.service.message;

import com.xiaodou.push.agent.enums.TargetType;

public class EnumMain {
public static void main(String[] args) {
  TargetType targetType = TargetType.IOS;
  System.out.println(targetType.getTypeCode());
}
}
