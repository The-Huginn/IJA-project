/**
 * @file TypeTests.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Type class
 */
package com.ija.backend.diagramObject;
import java.util.Arrays;
import java.util.TreeSet;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.ija.backend.diagramObject.helpers.TypeHelper;
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
        assertNotNull(Type.getType("int"));
    }

    @Test
    public void setTypesTest() {
        TreeSet<String> set = new TreeSet<String> ();
        set.addAll(Arrays.asList("random", "remove", "should not allow"));
        assertFalse(Type.initTypes(set.toArray(new String[set.size()]), null));
        assertNull(Type.getType("random"));
    }

    @Test
    public void setTypesTest2() {
        Type.clearTypes();
        TreeSet<String> set = new TreeSet<String> ();
        set.addAll(Arrays.asList("int", "random", "remove", "boolean", "should not allow"));
        assertTrue(Type.initTypes(set.toArray(new String[set.size()]), null));
        assertNotNull(Type.getType("random"));
    }

    @Test
    public void addTypeTest() {
        assertTrue(Type.addType("random"));
    }

    @Test
    public void dontAddTypeTest() {
        // new Type("random");
        assertNull(Type.getType("random"));
    }

    @Test
    public void addTypeTest2() {
        Type.addType("random");
        Type type = Type.getType("random");
        assertFalse(Type.addType("random"));
        assertTrue(type == Type.getType("random"));
    }

    @Test
    public void RemoveTypeTest() {
        Type.addType("random");
        assertTrue(Type.removeType("random"));
        assertNull(Type.getType("random"));
    }

    @Test
    public void AddTypeTest3() {
        Type.addType("random");
        Type type = Type.getType("random");
        Type.removeType("random");
        Type.addType("random");
        type = Type.getType("random");
        assertTrue(type == Type.getType("random"));
    }

    @Test
    public void RemoveTypeTest2() {
        assertFalse(Type.removeType("int"));
        assertNotNull(Type.getType("int"));
    }

    @Test
    public void isUserDefinedTest() {
        Type.addType("random");
        assertTrue(Type.getType("random").isUserDefined());
        assertFalse(Type.getType("int").isUserDefined());
    }

    @Test
    public void setNameTest() {
        Type type = Type.getType("int");
        assertFalse(Type.getType("int").setName("random"));
        assertTrue(type == Type.getType("int"));
    }

    @Test
    public void setNameTest2() {
        Type.addType("random");
        Type type = Type.getType("random");
        assertTrue(type.setName("another random"));
        assertNull(Type.getType("random"));
        assertTrue(type == Type.getType("another random"));
    }

    @Test
    public void setNameTest3() {
        Type.addType("random");
        Type type = Type.getType("random");
        Type intType = Type.getType("int");
        assertFalse(type.setName("int"));
        assertTrue(intType == Type.getType("int"));
    }

    @Test
    public void setNameTest4() {
        Type.addType("random");
        Type.addType("another random");
        Type type = Type.getType("random");
        Type type2 = Type.getType("another random");
        assertFalse(type.setName("another random"));
        assertFalse(type2.setName("random"));
    }
}
