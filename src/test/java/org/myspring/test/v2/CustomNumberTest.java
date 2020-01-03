package org.myspring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.beans.propertyeditors.CustomNumberEditor;

/**
 * 测试普通的数字型数据
 * @author yujiangtao
 * @date 2018/6/23 15:12
 */
public class CustomNumberTest {

    @Test
    public void testConvertString() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer)editor.getValue()).intValue());

        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);

        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();

        editor = new CustomNumberEditor(Float.class, false);
        editor.setAsText("3.1");
        Assert.assertEquals(3.1f, (float) editor.getValue(), 0);
    }
}
