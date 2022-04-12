/**
 * @file TypeTests.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Type class
 */
package test.diagramObjectTest;

import java.util.Arrays;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.diagramObject.Type;

import test.diagramObjectTest.helpers.TypeHelper;

public class TypeTest {
    
    @Before
    public void setup() {
        TypeHelper.setup();
    }

    @After
    public void tearDown() {
        TypeHelper.tearDown();
    }

    @Test
    public void getType() {
        Assert.assertNotNull(Type.getType("int"));
    }

    @Test
    public void setTypesTest() {
        TreeSet<String> set = new TreeSet<String> ();
        set.addAll(Arrays.asList("random", "remove", "should not allow"));
        Assert.assertFalse(Type.initTypes(set.toArray(new String[set.size()]), null));
        Assert.assertNull(Type.getType("random"));
    }

    @Test
    public void setTypesTest2() {
        Type.clearTypes();
        TreeSet<String> set = new TreeSet<String> ();
        set.addAll(Arrays.asList("int", "random", "remove", "boolean", "should not allow"));
        Assert.assertTrue(Type.initTypes(set.toArray(new String[set.size()]), null));
        Assert.assertNotNull(Type.getType("random"));
    }

    @Test
    public void addTypeTest() {
        Assert.assertTrue(Type.addType("random"));
    }

    @Test
    public void dontAddTypeTest() {
        // new Type("random");
        Assert.assertNull(Type.getType("random"));
    }

    @Test
    public void addTypeTest2() {
        Type.addType("random");
        Type type = Type.getType("random");
        Assert.assertFalse(Type.addType("random"));
        Assert.assertTrue(type == Type.getType("random"));
    }

    @Test
    public void RemoveTypeTest() {
        Type.addType("random");
        Assert.assertTrue(Type.removeType("random"));
        Assert.assertNull(Type.getType("random"));
    }

    @Test
    public void AddTypeTest3() {
        Type.addType("random");
        Type type = Type.getType("random");
        Type.removeType("random");
        Type.addType("random");
        type = Type.getType("random");
        Assert.assertTrue(type == Type.getType("random"));
    }

    @Test
    public void RemoveTypeTest2() {
        Assert.assertFalse(Type.removeType("int"));
        Assert.assertNotNull(Type.getType("int"));
    }

    @Test
    public void isUserDefinedTest() {
        Type.addType("random");
        Assert.assertTrue(Type.getType("random").isUserDefined());
        Assert.assertFalse(Type.getType("int").isUserDefined());
    }

    @Test
    public void setNameTest() {
        Type type = Type.getType("int");
        Assert.assertFalse(Type.getType("int").setName("random"));
        Assert.assertTrue(type == Type.getType("int"));
    }

    @Test
    public void setNameTest2() {
        Type.addType("random");
        Type type = Type.getType("random");
        Assert.assertTrue(type.setName("another random"));
        Assert.assertNull(Type.getType("random"));
        Assert.assertTrue(type == Type.getType("another random"));
    }

    @Test
    public void setNameTest3() {
        Type.addType("random");
        Type type = Type.getType("random");
        Type intType = Type.getType("int");
        Assert.assertFalse(type.setName("int"));
        Assert.assertTrue(intType == Type.getType("int"));
    }

    @Test
    public void setNameTest4() {
        Type.addType("random");
        Type.addType("another random");
        Type type = Type.getType("random");
        Type type2 = Type.getType("another random");
        Assert.assertFalse(type.setName("another random"));
        Assert.assertFalse(type2.setName("random"));
    }
}
