package com.xiaodou.summer.util;

import java.util.HashMap;

import com.xiaodou.summer.dao.jdbc.seq.SeqDao;

public class HiLoGenerator {

  private SeqDao seqDao = (SeqDao) SpringWebContextHolder.getBean("seqDao");

  // app ID is loaded during app-server start-up
  private static byte m_appID = 0x00;

  // maximum lo count
  private static int MAX_LO = 0xFFFF;

  // db table name to id generator mapping
  private static HashMap<String, HiLoGenerator> generatorTable =
      new HashMap<String, HiLoGenerator>();

  /**
   * get the ID generator for a given db table
   * 
   * @param TableName
   * @return
   */
  public static HiLoGenerator getIDGenerator(String tableName) {
    HiLoGenerator generator;

    synchronized (generatorTable) {
      if (generatorTable.containsKey(tableName)) {
        generator = generatorTable.get(tableName);
      } else {
        generator = new HiLoGenerator(tableName);
        generatorTable.put(tableName, generator);
      }
    }
    return generator;
  }

  // hi count is a 32 bit integer field
  private int m_hi;
  // lo count is a 16 bit unsigned field
  private int m_lo;

  // corresponding db table name
  private String m_tableName;

  // constructor
  private HiLoGenerator(String tableName) {

    m_tableName = tableName;

    // get hi from DB , then add 1, and update DB with new value
    m_hi = getIdFromDB(tableName);

    // set lo with 0;
    m_lo = (short) 0;

  }

  private int getNextLo() {
    // lo reaches limit, reset it to 0 and increase Hi count .
    if (MAX_LO == m_lo) {
      m_hi = getIdFromDB(m_tableName);
      m_lo = 0;

      // Console.WriteLine("-------roll  over -----");
    } else {
      m_lo++;
    }

    return m_lo;
  }

  /**
   * get eLongID as a long sized (64 bits) structure The hi count is the higher 32 bits, lo count is
   * next 16bits, the last 16 bits are app ID and virtual shard ID.
   * 
   * | 32 bit | 16 bit | 8 bit | 8 bit | | Hi count | Lo count | appID | virtual shard(0 for now)|
   */
  public final long getNextID(byte shardID) {
    long id;
    synchronized (this) {
      getNextLo();
      id = IDUtil.getID(m_hi, m_lo, m_appID, shardID);
    }
    return id;
  }

  public int getIdFromDB(String tableName) {
    int seq = seqDao.querySeqId(tableName);
    return seq;
  }

}
