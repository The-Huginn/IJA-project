package test.diagramObjectTest;

import backend.diagramObject.Element;
import backend.diagramObject.Type;
import backend.diagramObject.Attribute;
import backend.diagramObject.Method;
import backend.diagramObject.UMLclass;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import test.diagramObjectTest.helpers.TypeHelper;

public class UMLClassTest {
    
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
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("class name");
        Assert.assertTrue(classObj.addAttribute(a));
    }

    @Test
    public void getAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("class name");
        classObj.addAttribute(a);
        Assert.assertTrue(classObj.getAttributes().get(0) == a);
    }

    @Test
    public void addAttributeTest2() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("class name");
        classObj.addAttribute(a);
        Assert.assertFalse(classObj.addAttribute(b));
        Assert.assertTrue(classObj.getAttributes().get(0) == a);
    }

    @Test
    public void addAttributeTest3() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("class name");
        classObj.addAttribute(a);
        classObj.addAttribute(b);
        Assert.assertTrue(classObj.getAttributes().size() == 2);
        Assert.assertTrue(classObj.getAttributes().get(0) == a);
        Assert.assertTrue(classObj.getAttributes().get(1) == b);
    }

    @Test
    public void removeAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute c = new Attribute("another random name", Type.getType("long"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("class name");
        classObj.addAttribute(a);
        classObj.addAttribute(b);
        classObj.addAttribute(c);
        classObj.removeAttribute(1);
        Assert.assertTrue(classObj.getAttributes().size() == 2);
        Assert.assertTrue(classObj.getAttributes().get(0) == a);
        Assert.assertTrue(classObj.getAttributes().get(1) == b);
    }

    @Test
    public void removeAttributeTest2() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("class name");
        classObj.addAttribute(a);
        classObj.removeAttribute(0);
        Assert.assertTrue(classObj.getAttributes().size() == 0);
        classObj.addAttribute(a);
        Assert.assertTrue(classObj.getAttributes().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        UMLClass classObj = new UMLClass("class name");
        classObj.addAttribute(a);
        classObj.addAttribute(m);
        Assert.assertTrue(classObj.getAttributes().get(0) == a);
        Assert.assertTrue(classObj.getAttributes().get(1) == m);
    }

    @Test
    public void addInterfaceTest() {
        UMLInterface inter = new UMLInterface("interface name");
        UMLClass classObj = new UMLClass("class name");
        Assert.assertTrue(classObj.addInterface(inter));
        Assert.assertTrue(classObj.getInterfaces().size() == 1);
        Assert.assertTrue(classObj.getInterfaces().get(0) == inter);
    }

    @Test
    public void addInterfaceTest2() {
        UMLInterface inter = new UMLInterface("interface name");
        UMLInterface inter2 = new UMLInterface("another interface name");
        UMLClass classObj = new UMLClass("class name");
        classObj.addInterface(inter);
        classObj.addInterface(inter2);
        Assert.assertTrue(classObj.getInterfaces().size() == 2);
        Assert.assertTrue(classObj.getInterfaces().get(0) == inter);
        Assert.assertTrue(classObj.getInterfaces().get(1) == inter2);
    }

    @Test
    public void addInterfaceTest3() {
        UMLInterface inter = new UMLInterface("interface name");
        UMLInterface inter2 = new UMLInterface("interface name");
        UMLClass classObj = new UMLClass("class name");
        classObj.addInterface(inter);
        Assert.assertFalse(classObj.addInterface(inter2));
        Assert.assertTrue(classObj.getInterfaces().size() == 1);
        Assert.assertTrue(classObj.getInterfaces().get(0) == inter);
    }

    @Test
    public void removeInterfaceTest() {
        UMLInterface inter = new UMLInterface("interface name");
        UMLInterface inter2 = new UMLInterface("another interface name");
        UMLInterface inter3 = new UMLInterface("another random interface name");
        UMLClass classObj = new UMLClass("class name");
        classObj.addInterface(inter);
        classObj.addInterface(inter2);
        classObj.addInterface(inter3);
        classObj.removeInterface(1);
        Assert.assertTrue(classObj.getInterfaces().size() == 2);
        Assert.assertTrue(classObj.getInterfaces().get(0) == inter);
        Assert.assertTrue(classObj.getInterfaces().get(1) == inter3);
    }

    @Test
    public void removeInterfaceTest2() {
        UMLInterface inter = new UMLInterface("interface name");
        UMLClass classObj = new UMLClass("class name");
        classObj.addInterface(inter);
        classObj.removeInterface(0);
        Assert.assertTrue(classObj.getInterfaces().size() == 0);
        classObj.addInterface(inter);
        Assert.assertTrue(classObj.getInterfaces().size() == 1);
        Assert.assertTrue(classObj.getInterfaces().get(0) == inter);
    }
}
