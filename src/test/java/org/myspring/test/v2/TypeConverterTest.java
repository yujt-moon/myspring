package org.myspring.test.v2;

import org.junit.Test;
import org.myspring.beans.SimpleTypeConverter;
import org.myspring.beans.TypeConverter;
import org.myspring.beans.TypeMismatchException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * 测试字符串类型转换成其他类型（数值类型和布尔类型）
 * @author yujiangtao
 * @date 2018/6/27 18:40
 */
public class TypeConverterTest {

    @Test
    public void testConvertStringToInt() {
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        assertEquals(3, i.intValue());

        try {
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e) {
            return;
        }
        fail();
    }

    @Test
    public void testConvertStringToBoolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        assertEquals(true, b.booleanValue());

        try {
            converter.convertIfNecessary("xxxyyyzzz", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        fail();
    }
}
