package com.xiaodou.oms.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.xiaodou.oms.util.CollectionUtil;

public class CollectionUtilTest {

    @Test
    public void testCompareAndReturnFirstPlus() {
        List<String> t1 = new ArrayList<>();
        List<String> t2 = new ArrayList<>();
        t1.add("aa");
        t1.add("bb");
        t1.add("cc");
        t1.add("ee");

        t2.add("aa");
        t2.add("bb");
        t2.add("cc");

        List<String> difs = new ArrayList<>();
        difs = CollectionUtil.compareAndReturnFirstPlus(t1, t2);
        Assert.assertEquals(1, difs.size());
    }
    
    
}
