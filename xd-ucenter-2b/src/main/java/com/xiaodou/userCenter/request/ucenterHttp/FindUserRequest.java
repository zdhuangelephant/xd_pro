package com.xiaodou.userCenter.request.ucenterHttp;

import java.util.List;

import lombok.Data;

@Data
public class FindUserRequest {

  private List<String> xdUniqueIdList;

}
