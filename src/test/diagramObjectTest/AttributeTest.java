/**
 * @file AttributeTest.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Attribute class
 */
package test.diagramObjectTest;

import backend.diagramObject.Element;
import backend.diagramObject.Type;
import backend.diagramObject.Attribute;

import test.diagramObjectTest.helpers.TypeHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class AttributeTest {
    
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
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Assert.assertTrue("name".equals(a.getName()));
        Assert.assertTrue(Attribute.Visibility.PUBLIC == a.getType());
        Assert.assertTrue(a.isVisibilityChangable());
    }

    @Test
    public void setVisibilityTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        for (Attribute.Visibility visibility : Attribute.Visibility.values()) {
            a.setType(visibility);
            Assert.assertTrue(visibility == a.getVisibility());
        }
    }

    @Test
    public void setTypeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        a.setType(Type.getType("long"));
        Assert.assertTrue(a.getType() == Type.getType("long")); // yes, we compare by reference
    }

    @Test
    public void setNullTypeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Assert.assertFalse(a.setType(Type.getType("not existing")));
        Assert.assertTrue(a.getType() == Type.getType("int"));
    }

    @Test
    public void changeAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        a.setVisibility(Attribute.Visibility.PROTECTED);
        a.setType(Type.getType("boolean"));
        a.setName("new name");
        Assert.assertTrue(a.getType() == Attribute.Visibility.PROTECTED);
        Assert.assertTrue("new name".equals(a.getName()));
        Assert.assertTrue(a.getType() == Type.getType("int"));  // yes, we compare by reference
    }

    @Test
    public void unchangableVisibilityTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Assert.assertFalse(a.setVisibility(Attribute.Visibility.PROTECTED));
        Assert.assertTrue(Attribute.Visibility.PUBLIC == a.getVisibility());
        Assert.assertFalse(a.isVisibilityChangable());
    }
}
