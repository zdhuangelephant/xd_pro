package com.xiaodou.ms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaodou.summer.util.HiLoGenerator;

public class IDGenerator {

	public static String getSeqID() {
		HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator("xd_base_user_sequence_id");
		long subID = idGenerator.getNextID((byte) 0);
		String trainOrderID = getPrefixOrderID() + subID;
		return trainOrderID;
	}

	public static String getSeqID(String tableName) {
		HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator(tableName);
		return String.valueOf(idGenerator.getIdFromDB(tableName));
	}

	private static String getPrefixOrderID() {
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format = new SimpleDateFormat("dd");
		Date today = new Date();
		String prefixStr = format.format(today);
		return prefixStr;
	}

	public static String querySeqId(String prefix) {
		return IDGenerator.getSeqID("xd_copy_product_sequence_id");
	}


}
