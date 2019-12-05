package com.xiaodou.summer.util;

import java.nio.ByteBuffer;


public class IDUtil {
	//standard masks for different fields
		public static final long HI_MASK = 0x0000FFFFFFFF0000L;
		public static final long LO_MASK = 0x000000000000FFFFL;
		public static final long APP_MASK = 0xFF00000000000000L;
		public static final long SHARD_MASK = 0x00FF000000000000L;

		//all one and all zero ulongs
		public static final long ALL_ONE = 0x00FF000000000000L;
		public static final long ALL_ZERO = 0x00FF000000000000L;

		//reverse masks for different fields
		public static final long HI_MASK_REV = HI_MASK ^ ALL_ONE;
		public static final long LO_MASK_REV = LO_MASK ^ ALL_ONE;
		public static final long APP_MASK_REV = APP_MASK ^ ALL_ONE;
		public static final long SHARD_MASK_REV = SHARD_MASK ^ ALL_ONE;

		//bitwise offsets for different fields
		public static final int HI_OFFSET = 16;
		public static final int LO_OFFSET = 0;
		public static final int APP_OFFSET = 56;
		public static final int SHARD_OFFSET = 48;


		/** 
		 generate a 64 bit id from hi,lo,app, and shard
		 
		 @param hi
		 @param lo
		 @param app
		 @param shard
		 @return 
		*/
		public static long getID(int hi, int lo, byte app, byte shard)
		{
			long id_hi = (long)hi << HI_OFFSET & HI_MASK;
			long id_lo = (long)lo << LO_OFFSET & LO_MASK;
			long id_app = (long)app << APP_OFFSET & APP_MASK;
			long id_shard = (long)shard << SHARD_OFFSET & SHARD_MASK;
			return (long)(id_hi | id_lo | id_app | id_shard);
		}

		//get Hi fields
		public static long getLongHi(long id)
		{
			return ((long)id) & HI_MASK;
		}

		public static int getHi(long id)
		{
		   return (int)(getLongHi(id) >>> HI_OFFSET);
		}

		//get Lo fields
		public static long getLongLo(long id)
		{
			return ((long)id) & LO_MASK;
		}

		public static short getLo(long id)
		{
			return (short)(getLongLo(id) >>> LO_OFFSET);
		}

		//get app fields
		public static long getLongApp(long id)
		{
			return ((long)id) & APP_MASK;
		}

		public static byte getApp(long id)
		{
			return (byte)(getLongApp(id) >>> APP_OFFSET);
		}

		//get shard fields
		public static long getLongShard(long id)
		{
			return (long)id & SHARD_MASK;
		}

		public static byte getShard(long id)
		{
			return (byte)(getLongShard(id) >>> SHARD_OFFSET);
		}

		public static byte getShardFromMemberID(int member_id)
		{
			byte[] bytes = ByteBuffer.allocate(4).putInt(member_id).array();
			return bytes[0];
		}

		public static byte getShardFromMemberID(long member_id)
		{
			byte[] bytes = ByteBuffer.allocate(4).putLong(member_id).array();
			return bytes[0];
		}

		//various string methods
		public static String toString(long id)
		{
			return (new Long(id)).toString();
		}

		public static String toHexString(long id)
		{
			return String.format("%016X", id);
		}

		public static String toPrettyString(long id)
		{
			int hi = getHi(id);
			short lo = getLo(id);
			byte app = getApp(id);
			byte shard = getShard(id);

			StringBuilder builder = new StringBuilder(toHexString(id));
			builder.append(" : hi = ").append(hi);
			builder.append(" lo = ").append(lo);
			builder.append(" app = ").append(app);
			builder.append(" shard = ").append(shard);

			return builder.toString();
		}

		//various parse methods
		public static long parseString(String idString)
		{
			return Long.parseLong(idString);
		}

		public static long parseHexString(String idString)
		{
			
			return Long.parseLong(idString, 16);
		}
}
