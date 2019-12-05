package com.xiaodou.webfetch.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.jsoup.nodes.Element;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.webfetch.constants.WebFetchConstant;
import com.xiaodou.webfetch.engine.SandBoxContext;
import com.xiaodou.webfetch.model.FetchTaskModel.FetchActionModel;
import com.xiaodou.webfetch.param.DependTaskGroup;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.action.Goto.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月15日
 * @description 切入新数据页
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Goto extends IncisePoint {

  public Goto(FetchActionModel model) {
    super(model);
  }

  @Override
  public void doMain0(Element element, SandBoxContext sandBox) {
    String content = element.html();
    
    if(StringUtils.isBlank(content))
      return;
    //  1.讲 微信、 知乎、头条、的域名分别初始化。然后正则判断href的属性是否包含http/https、若有责说明是非本站访问。若没有则说明是本站访问
    
    
    
    Pattern pattern = Pattern.compile(WebFetchConstant.URL_REG);
    Matcher matcher = pattern.matcher(content);
    if (matcher.matches()) {
      String url = matcher.group();
      if(StringUtils.isBlank(url)) {
        return;
      }
      DependTaskGroup taskGroup = sandBox.getTargetTaskGroup(new Target(unique()));
      if (null != taskGroup) {
        for (FetchTask task : taskGroup.getTaskList()) {
          task.setUrl(content);
        }
      }
    }
  }
}
