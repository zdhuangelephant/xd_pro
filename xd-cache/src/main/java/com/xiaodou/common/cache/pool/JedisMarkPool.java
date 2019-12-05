package com.xiaodou.common.cache.pool;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.xiaodou.common.cache.redis.JedisProp;

/**
 * 可标记状态JedisPool
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月6日
 */
public class JedisMarkPool extends JedisPool {

  private static String TEST_KEY = "@@MARK今のうちにきみをつかまえPOOL@@";
  
  /**
   * 构造方法
   * @param poolConfig config
   * @param host 主机IP
   * @param port 端口号
   * @param timeout 超时
   */
  public JedisMarkPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout) {
    super(poolConfig, host, port, timeout);
    this.nodekey = host + ":" + port;
  }
  
  /**失败次数*/
  private AtomicInteger failCounter = new AtomicInteger(0);
  
  /**状态*/
  private boolean status = true;
  
  /**节点号*/
  private String nodekey;

  /**
   * 获取节点号
   * @return 节点号
   */
  public String getNodekey() {
    return nodekey;
  }

  /**
   * 判断状态是否可用
   * @return 状态
   */
  public boolean isConnect() {
    return status;
  }

  /**
   * 设置状态
   * @param status 状态
   */
  private void setStatus(boolean status) {
    this.status = status;
  }

  /**节点失效阈值*/
  private static Integer THRESHOLD = Integer.valueOf(JedisProp.getParams("redis.threshold"));
  
  /**
   * 获取客户端失败
   */
  public void fail(){
    if(failCounter.incrementAndGet()>THRESHOLD)
      this.setStatus(false);
  }
  
  /**
   * 尝试重连
   * @return 是否可用
   */
  public boolean connect(){
    Jedis jedis = null;
    try {
      jedis = this.getResource();
      if(null!=jedis){
        if(-2<=jedis.ttl(TEST_KEY)){
          this.returnResource(jedis);
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      if(null!=jedis){
        this.returnBrokenResource(jedis);
      }
      return false;
    } finally {
      if(null!=jedis){
        this.returnResource(jedis);
      }
    }
  }
}
