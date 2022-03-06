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
        Assert.assertTrue(inter.addVariable(a));
    }

    @Test
    public void addAttributeTest2() {
        Attribute b = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLInterface inter = new UMLInterface("interface name");
        Assert.assertFalse(inter.addVariable(b));
    }

    @Test
    public void getAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(a);
        Assert.assertTrue(inter.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest3() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(a);
        Assert.assertFalse(inter.addVariable(b));
        Assert.assertTrue(inter.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(a);
        inter.addVariable(b);
        Assert.assertTrue(inter.getVariables().size() == 2);
        Assert.assertTrue(inter.getVariables().get(0) == a);
        Assert.assertTrue(inter.getVariables().get(1) == b);
    }

    @Test
    public void addAttributeTest5() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("name", Type.getType("long"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(a);
        Assert.assertFalse(inter.addVariable(b));
    }

    @Test
    public void removeAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute c = new Attribute("another random name", Type.getType("long"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(a);
        inter.addVariable(b);
        inter.addVariable(c);
        inter.removeVariable(1);
        Assert.assertTrue(inter.getVariables().size() == 2);
        Assert.assertTrue(inter.getVariables().get(0) == a);
        Assert.assertTrue(inter.getVariables().get(1) == b);
    }

    @Test
    public void removeAttributeTest2() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addBariable(a);
        inter.removeVariable(0);
        Assert.assertTrue(inter.getVariables().size() == 0);
        inter.addVariable(a);
        Assert.assertTrue(inter.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(a);
        Assert.assertTrue(inter.addVariable(m));
        Assert.assertTrue(inter.getVariables().get(0) == a);
        Assert.assertTrue(inter.getVariables().get(1) == m);
    }

    @Test
    public void addAttributeTest5() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false, params2);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }
    
    @Test
    public void addAttributeTest6() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "int"};
        Method m2 = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params2);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest6() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int"};
        Method m2 = new Method("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false, params2);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest7() {
        String params[] = {"int"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", Type.getType("int"), Attribute.Visibility.PUBLIC, false, params2);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest5() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, false, params2);
        UMLInterface inter = new UMLInterface("interface name");
        inter.addVariable(m);
        Assert.assertFalse(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
    }
}
