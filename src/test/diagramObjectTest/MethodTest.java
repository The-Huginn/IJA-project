/**
 * @file MethodTest.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Method class
 */
package test.diagramObjectTest;

import backend.diagramObject.Type;
import test.diagramObjectTest.helpers.TypeHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import backend.diagramObject.Attribute;
import backend.diagramObject.Method;


public class MethodTest {
    private String params[] = {"int", "int", "boolean", "long", "int", "string"};

    @BeforeEach
    public void setup() {
        TypeHelper.setup();
    }
    
    @AfterEach
    public void tearDown() {
        TypeHelper.tearDown();
    }

    @Test
    public void getterTest() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        Assert.assertArrayEquals(params, m.getParameters().toArray(new Type[m.getParameters().size()]));
    }

    @Test
    public void setParametersTest() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        Assert.assertTrue(m.setParameters(params));
    }

    @Test
    public void setParametersTest2() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String newParams[] = {"long", "boolean", "string"};
        Assert.assertTrue(m.setParameters(newParams));
        Assert.assertArrayEquals(params, m.getParameters().toArray(new Type[m.getParameters().size()]));
    }

    @Test
    public void setParametersTest3() {
        Method m = new Method("name", null, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String wrongParams[] = {"long", "boolean", "string", "random"};
        Assert.assertFalse(m.setParameters(wrongParams));
        Assert.assertArrayEquals(params, m.getParameters().toArray(new Type[m.getParameters().size()]));
    }
}
