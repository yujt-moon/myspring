package org.myspring.test.v2;


import org.junit.Assert;
import org.junit.Test;
import org.myspring.beans.propertyeditors.CustomBooleanEditor;

/**
 * 测试 CustomBooleanEditor 是否能将默认的某些字符串的值
 * （"true", "false"; "on", "off"; "yes", "no"; "1", "0"）
 * 转换成boolean类型的值
 * @author yujiangtao
 * @date 2018/6/27 17:11
 */
public class CustomBooleanEditorTest {

    @Test
    public void testConvertStringToBoolean() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        try {
            editor.setAsText("aabbcc");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
