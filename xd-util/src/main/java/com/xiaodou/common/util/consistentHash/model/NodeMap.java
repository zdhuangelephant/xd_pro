package com.xiaodou.common.util.consistentHash.model;

/**
 * 负载节点 别名-值 对象
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-2-19
 * @param <T>
 */
public class NodeMap<T> {

  /**
   * 节点别名
   */
  private String nodename;

  /**
   * 负载节点
   */
  private T node;
  
  /**
   * 一致性哈希环粒度
   */
  private final int numberOfReplicas;

  public int getNumberOfReplicas() {
    return numberOfReplicas;
  }

  public NodeMap(String nodename, T node) {
    this(nodename, node, -1);
  }
  
  public NodeMap(String nodename, T node, Integer numberOfReplicas) {
    this.nodename = nodename;
    this.node = node;
    this.numberOfReplicas = numberOfReplicas;
  }

  public String getNodename() {
    return nodename;
  }

  public void setNodename(String noodname) {
    this.nodename = noodname;
  }

  public T getNode() {
    return node;
  }

  public void setNode(T node) {
    this.node = node;
  }

}
