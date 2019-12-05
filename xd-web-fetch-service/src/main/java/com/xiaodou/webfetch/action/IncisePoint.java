package com.xiaodou.webfetch.action;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.webfetch.data.Data;
import com.xiaodou.webfetch.engine.SandBoxContext;
import com.xiaodou.webfetch.model.FetchTaskModel.FetchActionModel;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.unique.IUnique;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.incise.IncisePoint.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月7日
 * @description 基本切割点
 * @version 1.0
 */
public class IncisePoint implements IUnique {
  public IncisePoint(FetchActionModel model) {
    this.dependAction = new Target(model.getDependActionId());
    this.inciseRule = model.getInciseRule();
    this.supporter = new Target(model.getSupporterFieldId());
    this.needLoop = model.getNeedLoop();
    this.result = new Target(model.getTargetFieldId());
  }

  /** dependAction 依赖动作 */
  private Target dependAction;
  /** supporter 载体数据集 */
  private Target supporter;
  /** inciseRule 切割规则 */
  private String inciseRule;
  /** needLoop 是否需要轮训,默认不需要 */
  private Boolean needLoop = Boolean.FALSE;
  /** result 切割结果集 */
  private Target result;

  public Target getDependAction() {
    return dependAction;
  }

  public String getInciseRule() {
    return inciseRule;
  }
  
  private Target target;

  @Override
  public String unique() {
    return target.unique();
  }

  public void doMain(FetchTask task, SandBoxContext sandBox) {
    /*
     * Data data = sandBox.getTargetData(supporter); if (null == data || !data.hasData()) return;
     * Elements select = data.getData().select(inciseRule); if (needLoop) { for (Element element :
     * select) { sandBox.registTargetElements(result, element); doMain0(element, sandBox); } } else
     * { sandBox.registTargetElements(result, select.first()); doMain0(select.first(), sandBox); }
     */

    Data data = sandBox.getTargetData(supporter);
    if (null == data || !data.hasData()) return;

    // 获取"文章"切拼接url, 用于获取文章里列表

    Elements select = data.getData().select(inciseRule);
    if (needLoop) {
      for (Element element : select) {
        sandBox.registTargetElements(result, element);
        doMain0(element, sandBox);
      }
    } else {
      sandBox.registTargetElements(result, select.first());
      doMain0(select.first(), sandBox);
    }
  }



  public void doMain0(Element element, SandBoxContext sandBox) {
    if(null != sandBox && element.hasText()) {
      String pathname = element.select("li").get(3).select("a").attr("href");
      if(StringUtils.isBlank(pathname)) { return;}
      String url = "https://www.zhihu.com" + pathname;
      registTaskGroup(sandBox, url);
    }
  }

  private void registTaskGroup(SandBoxContext sandBox, String url) {
    // TODO Auto-generated method stub
    
  }


 
}
