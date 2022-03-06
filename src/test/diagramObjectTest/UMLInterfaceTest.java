package test.diagramObjectTest;

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
    private UMLInterface inter;

    @BeforeEach
    public void setup() {
        inter = new UMLInterface("interface name", null);
        TypeHelper.setup();
    }

    @AfterEach
    public void tearDown() {
        inter = null;
        TypeHelper.tearDown();
    }

    @Test
    public void addAttributeTest() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Assert.assertTrue(inter.addVariable(a));
    }

    @Test
    public void addAttributeTest2() {
        Attribute b = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Assert.assertFalse(inter.addVariable(b));
    }

    @Test
    public void getAttributeTest() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        inter.addVariable(a);
        Assert.assertTrue(inter.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest3() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        inter.addVariable(a);
        Assert.assertFalse(inter.addVariable(b));
        Assert.assertTrue(inter.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("another name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        inter.addVariable(a);
        inter.addVariable(b);
        Assert.assertTrue(inter.getVariables().size() == 2);
        Assert.assertTrue(inter.getVariables().get(0) == a);
        Assert.assertTrue(inter.getVariables().get(1) == b);
    }

    @Test
    public void addAttributeTest5() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("name", inter, Type.getType("long"), Attribute.Visibility.PUBLIC, false);
        inter.addVariable(a);
        Assert.assertFalse(inter.addVariable(b));
    }

    @Test
    public void removeAttributeTest() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute b = new Attribute("another name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        Attribute c = new Attribute("another random name", inter, Type.getType("long"), Attribute.Visibility.PUBLIC, false);
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
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        inter.addVariable(a);
        inter.removeVariable(0);
        Assert.assertTrue(inter.getVariables().size() == 0);
        inter.addVariable(a);
        Assert.assertTrue(inter.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest6() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false);
        String params[] = {"int", "long"};
        Method m = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        inter.addVariable(a);
        Assert.assertTrue(inter.addVariable(m));
        Assert.assertTrue(inter.getVariables().get(0) == a);
        Assert.assertTrue(inter.getVariables().get(1) == m);
    }

    @Test
    public void addAttributeTest7() {
        String params[] = {"int", "long"};
        Method m = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false, params2);
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }
    
    @Test
    public void addAttributeTest8() {
        String params[] = {"int", "long"};
        Method m = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "int"};
        Method m2 = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params2);
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest9() {
        String params[] = {"int", "long"};
        Method m = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int"};
        Method m2 = new Method("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false, params2);
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest10() {
        String params[] = {"int"};
        Method m = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC, false, params2);
        inter.addVariable(m);
        Assert.assertTrue(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
        Assert.assertTrue(inter.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest11() {
        String params[] = {"int", "long"};
        Method m = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, false, params2);
        inter.addVariable(m);
        Assert.assertFalse(inter.addVariable(m2));
        Assert.assertTrue(inter.getVariables().get(0) == m);
    }

    @Test(expected = Test.None.class /* expecting no exception */)
    public void removeOutOfBoundsTest() {
        Attribute a = new Attribute("name", inter, Type.getType("int"), Attribute.Visibility.PUBLIC);
        String params[] = {"int", "long"};
        Method m = new Method("name", inter, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        inter.addVariable(a);
        inter.addMethod(m);
        
        inter.removeMethod(-1);
        inter.removeVariable(-1);
        inter.removeMethod(1);
        inter.removeVariable(1);
    }
}
