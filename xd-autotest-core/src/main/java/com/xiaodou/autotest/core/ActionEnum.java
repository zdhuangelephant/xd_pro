package com.xiaodou.autotest.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.autotest.core.datasource.mysql.MysqlDao;
import com.xiaodou.autotest.core.model.PreSet;
import com.xiaodou.autotest.core.protocol.AbstractHttpProxy;
import com.xiaodou.autotest.core.protocol.JsonHttpProxy;
import com.xiaodou.autotest.core.protocol.NormalHttpProxy;
import com.xiaodou.autotest.core.protocol.SoapHttpProxy;
import com.xiaodou.autotest.core.proxy.AbstractApiProxy;
import com.xiaodou.autotest.core.proxy.HttpApiProxy;
import com.xiaodou.autotest.core.proxy.ThriftApiProxy;
import com.xiaodou.autotest.core.vo.SandBoxContext;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.server.mapi.engine.enums.ActionEnum.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 注册Action用枚举
 * @version 1.0
 */
public class ActionEnum {

  /** minArrayLength 最小数组长度 */
  private static Integer minArrayLength = 2;

  /**
   * @description Api执行状态
   * @version 1.0
   */
  public enum ApiStatus {
    /** Excuted 已执行 */
    Excuted,
    /** UnExcute 未执行 */
    UnExcute
  }

  /**
   * @description 方法关键字
   * @version 1.0
   */
  public enum Method {
    /** get GET方法 */
    get,
    /** post POST方法 */
    post,
    /** put PUT方法 */
    put,
    /** delete DELETE方法 */
    delete
  }

  /**
   * @description 传输数据格式
   * @version 1.0
   */
  public enum Format {
    /** normal 普通文本格式 */
    normal(NormalHttpProxy.class),
    /** json json格式 */
    json(JsonHttpProxy.class),
    /** soap soap格式 */
    soap(SoapHttpProxy.class);
    Format(Class<? extends AbstractHttpProxy> proxyType) {
      this.proxyType = proxyType;
    }

    private Class<? extends AbstractHttpProxy> proxyType;

    public Class<? extends AbstractHttpProxy> getProxyType() {
      return proxyType;
    }

  }

  /**
   * @description API支持的协议类型
   * @version 1.0
   */
  public enum Protocol {
    /** http HTTP协议 */
    http(HttpApiProxy.class),
    /** https HTTPS协议 */
    https(HttpApiProxy.class),
    /** thrift THRIFT协议 */
    thrift(ThriftApiProxy.class);
    Protocol(Class<? extends AbstractApiProxy> proxyType) {
      this.proxyType = proxyType;
    }

    private Class<? extends AbstractApiProxy> proxyType;

    public Class<? extends AbstractApiProxy> getProxyType() {
      return proxyType;
    }
  }

  /**
   * @description 变量生效范围
   * @version 1.0
   */
  public enum VarScope {
    /** global 全局范围 */
    global {
      @Override
      public void setVar(SandBoxContext sandBox, String name, String value) {
        if (null == sandBox) {
          return;
        }
        if (StringUtils.isBlank(name)) {
          return;
        }
        sandBox.setGlobalParam(name, value);
      }
    },
    /** part 局部范围 */
    part {
      @Override
      public void setVar(SandBoxContext sandBox, String name, String value) {
        if (null == sandBox) {
          return;
        }
        if (StringUtils.isBlank(name)) {
          return;
        }
        sandBox.setParam(name, value);
      }
    };
    public abstract void setVar(SandBoxContext sandBox, String name, String value);
  }

  /**
   * @description 内置方法
   * @version 1.0
   */
  public enum Function {
    /** uuid_s 生成UUID_String方法 */
    uuid_s("uuid_s()") {
      @Override
      public String value() {
        return UUID.randomUUID().toString();
      }
    },
    /** uuid_t 生成UUID_Timestamp方法 */
    uuid_t("uuid_s()") {
      @Override
      public String value() {
        return Long.toString(UUID.randomUUID().timestamp());
      }
    };
    Function(String name) {
      this.name = name;
    }

    /** name 方法名 */
    private String name;

    public String getName() {
      return name;
    }

    public abstract String value();
  }

  /**
   * @description 数据源类型
   * @version 1.0
   */
  public enum DataSource {
    /** Local 内存数据源 */
    Local {
      @Override
      public void setValue(String assignment, PreSet preSet, SandBoxContext sandBox) {
        if (StringUtils.isNotBlank(assignment)) {
          if (Function.uuid_s.getName().equals(assignment)) {
            preSet.getScope().setVar(sandBox, preSet.getKey(),
                preSet.getDataType().formatData(Function.uuid_s.value()));
          } else if (Function.uuid_t.getName().equals(assignment)) {
            preSet.getScope().setVar(sandBox, preSet.getKey(),
                preSet.getDataType().formatData(Function.uuid_t.value()));
          } else {
            preSet.getScope().setVar(sandBox, preSet.getKey(),
                preSet.getDataType().formatData(assignment));
          }
        }
      }
    },
    /** Func JS方法库数据源 */
    Func {
      @Override
      public void setValue(String assignment, PreSet preSet, SandBoxContext sandBox) {
        if (StringUtils.isBlank(assignment)) {
          return;
        }
        Object value = sandBox.getFuncVal(assignment);
        if (null == value) {
          return;
        }
        preSet.getScope().setVar(sandBox, preSet.getKey(),
            preSet.getDataType().formatData(value.toString()));
      }
    },
    /** Mysql Mysql数据源 */
    Mysql {
      @Override
      public void setValue(String assignment, PreSet preSet, SandBoxContext sandBox) {
        String[] assignments = assignment.split(ActionConstant.SEPERATOR_DATASOURCE);
        if (assignments.length < minArrayLength) {
          return;
        }
        List<String> keyList = ActionTool.getAutoRegistKey(assignments[1]);
        Map<String, String> map = MysqlDao.get(assignments[0], assignments[1]);
        preSet.getScope().setVar(sandBox, preSet.getKey(), map.get(preSet.getKey()));
        if (null == keyList || keyList.size() == 0) {
          return;
        }
        for (String key : keyList) {
          String value = map.get(String.format(ActionConstant.DEFAULT_AUTOREGIST_TMP, key));
          if (StringUtils.isNotBlank(value)) {
            preSet.getScope().setVar(sandBox, key, value);
          }
        }
      }
    },
    /** Redis Redis数据源 */
    Redis {
      @Override
      public void setValue(String assignment, PreSet preSet, SandBoxContext sandBox) {
        if (StringUtils.isNotBlank(assignment)) {
          preSet.getScope().setVar(sandBox, preSet.getKey(),
              preSet.getDataType().formatData(assignment));
        }
      }
    },
    /** Mongo Mongo数据源 */
    Mongo {
      @Override
      public void setValue(String assignment, PreSet preSet, SandBoxContext sandBox) {
        if (StringUtils.isNotBlank(assignment)) {
          preSet.getScope().setVar(sandBox, preSet.getKey(),
              preSet.getDataType().formatData(assignment));
        }
      }
    },
    /** Rabbitmq Rabbitmq数据源 */
    Rabbitmq {
      @Override
      public void setValue(String assignment, PreSet preSet, SandBoxContext sandBox) {
        if (StringUtils.isNotBlank(assignment)) {
          preSet.getScope().setVar(sandBox, preSet.getKey(),
              preSet.getDataType().formatData(assignment));
        }
      }
    };
    public abstract void setValue(String assignment, PreSet preSet, SandBoxContext sandBox);
  }

  /**
   * @description 参数类型
   * @version 1.0
   */
  public enum ParamType {
    /** HeaderParam Head头参数 */
    HeaderParam,
    /** QueryParam Query查询参数 */
    QueryParam
  }

  /**
   * @description 数据格式
   * @version 1.0
   */
  public enum DataFormat {
    /** jSon json数据 */
    jSon {
      @Override
      public Object format(String data) {
        try {
          if (StringUtils.isJsonNotBlank(data)) {
            return JSONObject.parseObject(data);
          }
        } catch (Exception e) {}
        return data;
      }
    },
    /** NameValuePair 参数键值对 */
    NameValuePair {
      @Override
      public Object format(String data) {
        try {
          if (StringUtils.isJsonBlank(data)) {
            return data;
          }
          JSONObject object = JSONObject.parseObject(data);
          if (object.isEmpty()) {
            return data;
          }
          List<Entry<String, Object>> entryList = Lists.newArrayList(object.entrySet());
          NameValuePair[] params = new NameValuePair[entryList.size()];
          for (int i = 0; i < entryList.size(); i++) {
            params[i] =
                new NameValuePair(entryList.get(i).getKey(), entryList.get(i).getValue().toString());
          }
          return params;
        } catch (Exception e) {}
        return data;
      }
    },
    /** Text 文本 */
    Text {
      @Override
      public Object format(String data) {
        return data;
      }
    };
    public abstract Object format(String data);
  }

  /**
   * @description 支持的数据类型
   * @version 1.0
   */
  public enum DataType {
    /** sTring 字符串类型 */
    sTring {
      @Override
      public String formatData(String data) {
        if (Function.uuid_s.getName().equals(data)) {
          return Function.uuid_s.value();
        } else if (Function.uuid_t.getName().equals(data)) {
          return Function.uuid_t.value();
        }
        return data;
      }

      @Override
      public Object getData(String testValue) {
        return testValue;
      }

      @Override
      public String formatData(Object data) {
        if (null == data) {
          return null;
        }
        return data.toString();
      }
    },
    /** iNteger 整数类型 */
    iNteger {
      @Override
      public String formatData(String data) {
        if (StringUtils.isNumeric(data)) {
          return data;
        }
        return null;
      }

      @Override
      public Object getData(String testValue) {
        if (StringUtils.isNumeric(testValue)) {
          return Integer.parseInt(testValue);
        }
        return null;
      }

      @Override
      public String formatData(Object data) {
        if (null == data) {
          return null;
        }
        return formatData(data.toString());
      }
    },
    /** dOuble 双精度浮点数类型 */
    dOuble {
      @Override
      public String formatData(String data) {
        if (StringUtils.isNumeric(data)) {
          return ActionConstant.D_FORMAT.format(Double.parseDouble(data));
        }
        return null;
      }

      @Override
      public Object getData(String testValue) {
        if (StringUtils.isNumeric(testValue)) {
          return Double.parseDouble(testValue);
        }
        return null;
      }

      @Override
      public String formatData(Object data) {
        if (null == data) {
          return null;
        }
        return formatData(data.toString());
      }
    },
    /** lIst 列表类型 */
    lIst {
      @Override
      public String formatData(String data) {
        if (StringUtils.isBlank(data)) {
          return null;
        }
        return FastJsonUtil.toJson(Lists.newArrayList(data.split(ActionConstant.SEPERATOR_VALUE)));
      }

      @Override
      public Object getData(String testValue) {
        if (StringUtils.isJsonBlank(testValue)) {
          return null;
        }
        return JSONObject.parse(testValue);
      }

      @Override
      public String formatData(Object data) {
        if (null == data) {
          return null;
        }
        if (data instanceof List) {
          return FastJsonUtil.toJson(data);
        }
        return data.toString();
      }
    },
    /** mAp Map类型 */
    mAp {
      @Override
      public String formatData(String data) {
        if (StringUtils.isBlank(data)) {
          return null;
        }
        HashMap<String, String> valueMap = Maps.newHashMap();
        for (String pair : data.split(ActionConstant.SEPERATOR_VALUE)) {
          if (StringUtils.isBlank(pair) || pair.indexOf(ActionConstant.SEPERATOR_PAIR) == -1) {
            continue;
          }
          String[] pairs = pair.split(ActionConstant.SEPERATOR_PAIR);
          valueMap.put(pairs[0], pairs[1]);
        }
        return FastJsonUtil.toJson(valueMap);
      }

      @Override
      public Object getData(String testValue) {
        if (StringUtils.isJsonBlank(testValue)) {
          return null;
        }
        return JSONObject.parse(testValue);
      }

      @Override
      public String formatData(Object data) {
        if (null == data) {
          return null;
        }
        if (data instanceof Map) {
          return FastJsonUtil.toJson(data);
        }
        return data.toString();
      }
    };
    public abstract String formatData(String data);

    public abstract String formatData(Object data);

    public abstract Object getData(String testValue);
  }

  /**
   * @description 测试结果
   * @version 1.0
   */
  public enum TestResult {
    /** Success 成功 */
    Success,
    /** Fail 失败 */
    Fail,
    /** Unknown 未知 */
    Unknown
  }

  /**
   * @description 条件范围
   * @version 1.0
   */
  public enum ConditionScope {
    /** isNull 为null */
    isNull {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        return srcVal == null;
      }
    },
    /** isNotNull 不为null */
    isNotNull {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        return srcVal != null;
      }
    },
    /** isEmpty 为空 */
    isEmpty {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        return null == srcVal || StringUtils.isBlank(srcVal.toString());
      }
    },
    /** isNotEmpty 不为空 */
    isNotEmpty {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        return null != srcVal && StringUtils.isNotBlank(srcVal.toString());
      }
    },
    /** eq 等于 */
    eq {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        if (srcVal == null || targetVal == null) {
          return Boolean.FALSE;
        }
        String sSrcVal = srcVal.toString();
        if (ActionTool.isRealNumber(sSrcVal) && ActionTool.isRealNumber(targetVal)) {
          return Double.parseDouble(sSrcVal) == Double.parseDouble(targetVal);
        }
        return targetVal.equals(sSrcVal);
      }
    },
    /** ge 大于等于 */
    ge {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        if (srcVal == null || targetVal == null) {
          return Boolean.FALSE;
        }
        String sSrcVal = srcVal.toString();
        if (ActionTool.isRealNumber(sSrcVal) && ActionTool.isRealNumber(targetVal)) {
          return Double.parseDouble(sSrcVal) >= Double.parseDouble(targetVal);
        }
        return targetVal.compareTo(sSrcVal) <= 0;
      }
    },
    /** le 小于等于 */
    le {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        if (srcVal == null || targetVal == null) {
          return Boolean.FALSE;
        }
        String sSrcVal = srcVal.toString();
        if (ActionTool.isRealNumber(sSrcVal) && ActionTool.isRealNumber(targetVal)) {
          return Double.parseDouble(sSrcVal) <= Double.parseDouble(targetVal);
        }
        return targetVal.compareTo(sSrcVal) >= 0;
      }
    },
    /** gt 大于 */
    gt {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        if (srcVal == null || targetVal == null) {
          return Boolean.FALSE;
        }
        String sSrcVal = srcVal.toString();
        if (ActionTool.isRealNumber(sSrcVal) && ActionTool.isRealNumber(targetVal)) {
          return Double.parseDouble(sSrcVal) > Double.parseDouble(targetVal);
        }
        return targetVal.compareTo(sSrcVal) < 0;
      }
    },
    /** lt 小于 */
    lt {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        if (srcVal == null || targetVal == null) {
          return Boolean.FALSE;
        }
        String sSrcVal = srcVal.toString();
        if (ActionTool.isRealNumber(sSrcVal) && ActionTool.isRealNumber(targetVal)) {
          return Double.parseDouble(sSrcVal) < Double.parseDouble(targetVal);
        }
        return targetVal.compareTo(sSrcVal) > 0;
      }
    },
    /** in 在...内 */
    in {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        if (srcVal == null || targetVal == null) {
          return Boolean.FALSE;
        }
        Set<String> valSet = Sets.newHashSet(targetVal.split(ActionConstant.SEPERATOR_VALUE));
        return valSet.contains(srcVal.toString());
      }
    },
    /** notIn 不在...内 */
    notIn {
      @Override
      public Boolean check(Object srcVal, String targetVal) {
        if (srcVal == null || targetVal == null) {
          return Boolean.FALSE;
        }
        Set<String> valSet = Sets.newHashSet(targetVal.split(ActionConstant.SEPERATOR_VALUE));
        return !valSet.contains(srcVal.toString());
      }
    };
    public abstract Boolean check(Object srcVal, String targetVal);
  }
}
