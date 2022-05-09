/**
 * @file AttributeTest.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Attribute class
 */
package com.ija.backend.diagramObject;

import com.ija.backend.diagramObject.helpers.TypeHelper;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AttributeTest {
    
    @Before
    public void setup() {
        TypeHelper.setup();
    }

    @After
    public void tearDown() {
        TypeHelper.tearDown();
    }

    @Test
    public void getterTest() {
        Attribute a = new Attribute("name", null, Type.getType("int"), Attribute.Visibility.PUBLIC);
        assertTrue("name".equals(a.getName()));
        assertTrue(Attribute.Visibility.PUBLIC == a.getVisibility());
        assertTrue(a.isVisibilityChangable());
    }

    @Test
    public void setVisibilityTest() {
        Attribute a = new Attribute("name", null, Type.getType("int"), Attribute.Visibility.PUBLIC);
        for (Attribute.Visibility visibility : Attribute.Visibility.values()) {
            a.setVisibility(visibility);
            assertTrue(visibility == a.getVisibility());
        }
    }

    @Test
    public void setTypeTest() {
        Attribute a = new Attribute("name", null, Type.getType("int"), Attribute.Visibility.PUBLIC);
        a.setType(Type.getType("long"));
        assertTrue(a.getType() == Type.getType("long")); // yes, we compare by reference
    }

    @Test
    public void setNullTypeTest() {
        Attribute a = new Attribute("name", null, Type.getType("int"), Attribute.Visibility.PUBLIC);
        a.setType(Type.getType("not existing"));
        assertTrue(a.getType() == Type.getType("int"));
    }

    @Test
    public void changeAttributeTest() {
        Attribute a = new Attribute("name", null, Type.getType("int"), Attribute.Visibility.PUBLIC);
        a.setVisibility(Attribute.Visibility.PROTECTED);
        a.setType(Type.getType("boolean"));
        a.setName("new name");
        assertTrue(a.getVisibility() == Attribute.Visibility.PROTECTED);
        assertTrue("new name".equals(a.getName()));
        assertTrue(a.getType() == Type.getType("boolean"));  // yes, we compare by reference
    }

    @Test
    public void unchangableVisibilityTest() {
        Attribute a = new Attribute("name", null, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        a.setVisibility(Attribute.Visibility.PROTECTED);
        assertTrue(Attribute.Visibility.PUBLIC == a.getVisibility());
        assertFalse(a.isVisibilityChangable());
    }
}
