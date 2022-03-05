package test.diagramObjectTest;

import backend.diagramObject.Element;
import backend.diagramObject.Type;
import backend.diagramObject.Attribute;
import backend.diagramObject.Method;
import backend.diagramObject.UMLInterface;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import test.diagramObjectTest.helpers.TypeHelper;

public class UMLInterfaceTest {
    
    @BeforeEach
    public void setup() {
        TypeHelper.setup();
    }

    @AfterEach
    public void tearDown() {
        TypeHelper.tearDown();
    }

    @Test
    public void addAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        Assert.assertTrue(inter.addAttribute(a));
    }

    @Test
    public void addAttributeTest2() {
        Attribute b = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLInterface inter = new UMLInterface("interface name");
        Assert.assertFalse(inter.addAttribute(b));
    }

    @Test
    public void getAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addAttribute(a);
        Assert.assertTrue(inter.getAttributes().get(0) == a);
    }

    @Test
    public void addAttributeTest3() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addAttribute(a);
        Assert.assertFalse(inter.addAttribute(b));
        Assert.assertTrue(inter.getAttributes().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addAttribute(a);
        inter.addAttribute(b);
        Assert.assertTrue(inter.getAttributes().size() == 2);
        Assert.assertTrue(inter.getAttributes().get(0) == a);
        Assert.assertTrue(inter.getAttributes().get(1) == b);
    }

    @Test
    public void removeAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute c = new Attribute("another random name", Type.getType("long"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addAttribute(a);
        inter.addAttribute(b);
        inter.addAttribute(c);
        inter.removeAttribute(1);
        Assert.assertTrue(inter.getAttributes().size() == 2);
        Assert.assertTrue(inter.getAttributes().get(0) == a);
        Assert.assertTrue(inter.getAttributes().get(1) == b);
    }

    @Test
    public void removeAttributeTest2() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addAttribute(a);
        inter.removeAttribute(0);
        Assert.assertTrue(inter.getAttributes().size() == 0);
        inter.addAttribute(a);
        Assert.assertTrue(inter.getAttributes().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addAttribute(a);
        inter.addAttribute(m);
        Assert.assertTrue(inter.getAttributes().get(0) == a);
        Assert.assertTrue(inter.getAttributes().get(1) == m);
    }
}
