package com.xiaodou.node.paxos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PeerConfig {
  private static final Logger LOG = Logger.getLogger(PeerConfig.class);

  
  protected Integer serverId = 0;
  protected boolean isGod = false;
  protected String localAddress;
  protected int clientPort = 0;
  protected int electionPort = 0;
  
  protected String dataDir;
  protected String dataLogDir;

  protected int tickTime = 200;
  protected int maxClientCnxns = 10;
  /** defaults to -1 if not set explicitly */
  protected int minSessionTimeout = -1;
  /** defaults to -1 if not set explicitly */
  protected int maxSessionTimeout = -1;

  protected int initLimit;
  protected int syncLimit;
  
  //由god发送给node的配置string
  protected String configString = "";
  
  protected final HashMap<Integer, QuorumServer> servers = new HashMap<Integer, QuorumServer>();
  protected final HashMap<Integer, QuorumServer> observers = new HashMap<Integer, QuorumServer>();
  protected final HashMap<Integer, QuorumServer> target = new HashMap<Integer, QuorumServer>();

  private static final String CR = System.getProperty("line.separator");
  @SuppressWarnings("serial")
  public static class ConfigException extends Exception {
    public ConfigException(String msg) {
      super(msg);
    }

    public ConfigException(String msg, Exception e) {
      super(msg, e);
    }
  }

  /**
   * Parse a ZooKeeper configuration file
   * 
   * @param path the patch of the configuration file
   * @throws ConfigException error processing configuration
   */
  public void parse(String path) throws ConfigException {
    File configFile = new File(path);

    LOG.info("Reading configuration from: " + configFile);

    try {
      if (!configFile.exists()) {
        throw new IllegalArgumentException(configFile.toString() + " file is missing");
      }

      Properties cfg = new Properties();
      FileInputStream in = new FileInputStream(configFile);
      try {
        cfg.load(in);
      } finally {
        in.close();
      }
      parseProperties(cfg);
    } catch (IOException e) {
      throw new ConfigException("Error processing " + path, e);
    } catch (IllegalArgumentException e) {
      throw new ConfigException("Error processing " + path, e);
    }
  }

  /**
   * Parse a server configuration file
   * 
   * @param path the patch of the configuration file
   * @throws ConfigException error processing configuration
   */
  public void parseFromProperties(Properties props) throws ConfigException {
    LOG.info("Reading configuration from: " + "inner jar package server.properties");
    try {
      if (props.isEmpty()) {
        throw new IllegalArgumentException(props.toString() + " file is missing");
      }
      parseProperties(props);
    } catch (IOException e) {
      throw new ConfigException("Error processing " + "config file", e);
    } catch (IllegalArgumentException e) {
      throw new ConfigException("Error processing " + "config file", e);
    }
  }

  public static class QuorumServer {
    public QuorumServer(long id, String addr, Integer port) {
      this.id = id;
      this.addr = addr;
      this.port = port;
    }

    public QuorumServer(long id, String addr) {
      this.id = id;
      this.addr = addr;
    }

    public String addr;

    public Integer port;

    public long id;

  }



  /**
   * Parse config from a Properties.
   * 
   * @param zkProp Properties to parse from.
   * @throws IOException
   * @throws ConfigException
   */
  public void parseProperties(Properties zkProp) throws IOException, ConfigException {
    for (Entry<Object, Object> entry : zkProp.entrySet()) {
      String key = entry.getKey().toString().trim();
      String value = entry.getValue().toString().trim();
      configString = configString + key + value +CR;
      
      if (key.equals("isGod")) {
        if (value.equals("true")) {
          isGod = true;
        }
      } if (key.equals("dataDir")) {
        dataDir = value;
      } else if (key.equals("dataLogDir")) {
        dataLogDir = value;
      } else if (key.equals("clientPort")) {
        clientPort = Integer.parseInt(value);
      } else if (key.equals("electionPort")) {
        electionPort = Integer.parseInt(value);
      } else if (key.equals("tickTime")) {
        tickTime = Integer.parseInt(value);
      } else if (key.equals("maxClientCnxns")) {
        maxClientCnxns = Integer.parseInt(value);
      } else if (key.equals("minSessionTimeout")) {
        minSessionTimeout = Integer.parseInt(value);
      } else if (key.equals("maxSessionTimeout")) {
        maxSessionTimeout = Integer.parseInt(value);
      } else if (key.equals("initLimit")) {
        initLimit = Integer.parseInt(value);
      } else if (key.equals("syncLimit")) {
        syncLimit = Integer.parseInt(value);
      } else if (key.startsWith("server.")) {
        
        int dot = key.indexOf('.');
        Integer sid = Integer.parseInt(key.substring(dot + 1));
        String parts[] = value.split(":");
        if ((parts.length != 3)) {
          LOG.error(value + " does not have the form host:port or host:port:port "
              + " or host:port:port:type");
        }
        String localAddeess = PropertiesUtil.getLocalAddeess();
        
        if (localAddeess.equals(parts[2])) {
          this.serverId = sid;
          this.localAddress = localAddeess;
          LOG.info("localAddress" + parts[2]);
//          servers.put(Long.valueOf(sid), new QuorumServer(sid, this.clientPortAddress, this.electionAddr));
        }
        servers.put(Integer.valueOf(sid), new QuorumServer(sid, parts[2]));
      } 
    }

    if (dataDir == null) {
      throw new IllegalArgumentException("dataDir is not set");
    }
    if (dataLogDir == null) {
      dataLogDir = dataDir;
    } else {
      if (!new File(dataLogDir).isDirectory()) {
        throw new IllegalArgumentException("dataLogDir " + dataLogDir + " is missing.");
      }
    }
    if (clientPort == 0) {
      throw new IllegalArgumentException("clientPort is not set");
    }
    if (electionPort == 0) {
      throw new IllegalArgumentException("electionPort is not set");
    }
    if (tickTime == 0) {
      throw new IllegalArgumentException("tickTime is not set");
    }
    if (minSessionTimeout > maxSessionTimeout) {
      throw new IllegalArgumentException(
          "minSessionTimeout must not be larger than maxSessionTimeout");
    }
    if (servers.size() == 0) {
      if (observers.size() > 0) {
        throw new IllegalArgumentException(
            "Observers w/o participants is an invalid configuration");
      }
      // Not a quorum configuration so return immediately - not an error
      // case (for b/w compatibility), server will default to standalone
      // mode.
      return;
    } else if (servers.size() == 1) {
      if (observers.size() > 0) {
        throw new IllegalArgumentException("Observers w/o quorum is an invalid configuration");
      }

      // HBase currently adds a single server line to the config, for
      // b/w compatibility reasons we need to keep this here.
      LOG.error("Invalid configuration, only one server specified (ignoring)");
      servers.clear();
    } else if (servers.size() > 1) {
      if (servers.size() == 2) {
        LOG.warn("无法承受服务器宕机 " + "至少启动三台服务器");
      } else if (servers.size() % 2 == 0) {
        throw new IllegalArgumentException("应该保证是奇数台服务器");
      }
     
      /*
       * 检查读取的文件是否可用
       */
      if (servers.size()!= 0) {
        for (QuorumServer s : servers.values()) {
          if (s.addr== null || "".equals(s.addr.trim()))
            throw new IllegalArgumentException("Missing address for server: " + s.id);
        }
      }
      
      /*
       * 开启自治模式，开始自行监督
       */
      if (serverId != 0 && isGod == false) {
        int total = servers.size();
        int num = (total-1)/2;
        for (int i = 1; i <= num; i++) {
          Integer observerId =  (serverId +i) < total ? (serverId +i): (serverId +i-total);  
          observers.put(observerId, servers.get(observerId));
        }
        //server 的 观察者
      }
    }
  }

  public String getDataDir() {
    return dataDir;
  }

  public String getDataLogDir() {
    return dataLogDir;
  }

  public int getTickTime() {
    return tickTime;
  }

  public int getMaxClientCnxns() {
    return maxClientCnxns;
  }

  public int getMinSessionTimeout() {
    return minSessionTimeout;
  }

  public int getMaxSessionTimeout() {
    return maxSessionTimeout;
  }

  public int getInitLimit() {
    return initLimit;
  }

  public int getSyncLimit() {
    return syncLimit;
  }

  public int getClientPort(){
    return clientPort;
  }
  
  public int getElectionPort() {
    return electionPort;
  }
  
  public boolean getIsGod() {
    return isGod;
  }
  
  //所有的server
  public Map<Integer, QuorumServer> getServers() {
    return Collections.unmodifiableMap(servers);
  }
  
  //返回上游观察者
  public Map<Integer, QuorumServer> getObservers() {
    return Collections.unmodifiableMap(observers);
  }

  //把配置文件转换成String
  public String getConfigString() {
    return configString;
  }


  public boolean isDistributed() {
    return servers.size() > 1;
  }

}
