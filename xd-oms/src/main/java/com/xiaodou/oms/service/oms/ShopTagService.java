package com.xiaodou.oms.service.oms;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.oms.dao.order.ShopTagDao;
import com.xiaodou.oms.entity.order.ShopTag;
import com.xiaodou.oms.vo.result.ResultType;
import com.xiaodou.oms.vo.result.order.ShopTagVO;

@Service
public class ShopTagService {
  @Resource
  ShopTagDao shopTagDao;
  
  public ShopTagVO createTag(String type,String productType){
    ShopTagVO vo = new ShopTagVO(ResultType.SUCCESS);
    String tag = UUID.randomUUID().toString();
    ShopTag shopTag = new ShopTag();
    shopTag.setTag(tag);
    shopTag.setType(type);
    shopTag.setProductType(productType);
    ShopTag tagRes = shopTagDao.addEntity(shopTag);
    vo.setTag(tagRes.getTag());
    return vo;
  }
  
  public ShopTag queryTagForUpdate(String tag){
    ShopTag shopTag = new ShopTag();
    shopTag.setTag(tag);
    return shopTagDao.findEntityByTagForUpdate(shopTag);
  }
  
  public boolean updateTagStatus(String tag,String status){
    ShopTag shopTag = new ShopTag();
    shopTag.setTag(tag);
    shopTag.setStatus(status);
    return shopTagDao.updateEntity(shopTag);
  }
  
}
