package com.xiaodou.webfetch.action;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.jsoup.nodes.Element;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.webfetch.engine.SandBoxContext;
import com.xiaodou.webfetch.model.FetchTaskModel.FetchActionModel;

/**
 * @name @see com.xiaodou.webfetch.action.Collect.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 收集行为
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Collect extends IncisePoint {

  /** splitSep 切割符 */
  private String splitSep = StringUtils.EMPTY;
  /** split 是否切割 */
  private boolean split = false;
  /** storeKey 保存key */
  private List<String> storeKey = Lists.newArrayList();

  public Collect(FetchActionModel model) {
    super(model);
    this.split = model.isSplit();
    this.splitSep = model.getSplitSep();
    this.storeKey = model.getStoreKey();
  }

  @Override
  public void doMain0(Element element, SandBoxContext sandBox) {
    String content = element.html();
    if (split) {
      String[] contents = content.split(splitSep);
      for (int i = 0; i < contents.length; i++) {
        saveField(i, contents[i], sandBox);
      }
    } else {
      saveField(0, content, sandBox);
    }
  }

  private void saveField(int index, String value, SandBoxContext sandBox) {
    if (storeKey.size() < index + 1) {
      return;
    }
    sandBox.saveField(storeKey.get(index), value);
  }

}
