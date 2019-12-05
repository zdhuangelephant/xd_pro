package com.xiaodou.common.test.introspector;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.xiaodou.common.util.introspector.ClassIntrospector;
import com.xiaodou.common.util.introspector.ObjectInfo;

public class ClassIntrospectorTest {
    public static void main(String[] args) throws IllegalAccessException {
        final ClassIntrospector ci = new ClassIntrospector();
        final Map<String, BigDecimal> map = new HashMap<String, BigDecimal>( 10);
        map.put( "one", BigDecimal.ONE );
        map.put( "zero", BigDecimal.ZERO );
        map.put( "ten", BigDecimal.TEN );
        ObjectInfo res;
        res = ci.introspect( "0123456789012345678901234567" );
        System.out.println( res.getDeepSize() );
        res = ci.introspect( new TestObjChild() );
        System.out.println( res.getDeepSize() );
        res = ci.introspect(map);
        System.out.println( res.getDeepSize() );
        res = ci.introspect( new String[] { "str1", "str2" } );
        System.out.println( res.getDeepSize() );
        res = ci.introspect(ObjectInfo.class);
        System.out.println( res.getDeepSize() );
        res = ci.introspect( new TestObj() );
        System.out.println( res.getDeepSize() );
 
        System.out.println( res );
    }
 
    /** First test object - testing various arrays and complex objects */
    private static class TestObj
    {
        protected final String[] strings = { "str1", "str2" };
        protected final int[] ints = { 14, 16 };
        private final Integer i = 28;
        protected final BigDecimal bigDecimal = BigDecimal.ONE;
 
        @Override
        public String toString() {
            return "TestObj{" +
                    "strings=" + (strings == null ? null : Arrays.asList(strings)) +
                    ", ints=" + Arrays.toString( ints ) +
                    ", i=" + i +
                    ", bigDecimal=" + bigDecimal +
                    '}';
        }
    }
 
    /** Test class 2 - testing inheritance */
    private static class TestObjChild extends TestObj
    {
        private final boolean[] flags = { true, true, false };
        private final boolean flag = false;
 
        @Override
        public String toString() {
            return "TestObjChild{" +
                    "flags=" + Arrays.toString( flags ) +
                    ", flag=" + flag +
                    '}';
        }
    }
}
