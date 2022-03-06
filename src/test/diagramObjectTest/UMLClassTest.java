package test.diagramObjectTest;

import backend.diagramObject.Element;
import backend.diagramObject.Type;
import backend.diagramObject.Attribute;
import backend.diagramObject.Method;
import backend.diagramObject.UMLClass;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import test.diagramObjectTest.helpers.TypeHelper;

public class UMLClassTest {
    private UMLClass classObj;

    @BeforeEach
    public void setup() {
        classObj = new UMLClass("classObj name", null);
        TypeHelper.setup();
    }

    @AfterEach
    public void tearDown() {
        TypeHelper.tearDown();
        classObj = null;
    }

    @Test
    public void addAttributeTest() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Assert.assertTrue(classObj.addVariable(a));
    }

    @Test
    public void getAttributeTest() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PRIVATE);
        UMLClass classObj = new UMLClass("classObj name", null);
        classObj.addVariable(a);
        Assert.assertTrue(classObj.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest3() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PROTECTED);
        Attribute b = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("classObj name", null);
        classObj.addVariable(a);
        Assert.assertFalse(classObj.addVariable(b));
        Assert.assertTrue(classObj.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        classObj.addVariable(a);
        classObj.addVariable(b);
        Assert.assertTrue(classObj.getVariables().size() == 2);
        Assert.assertTrue(classObj.getVariables().get(0) == a);
        Assert.assertTrue(classObj.getVariables().get(1) == b);
    }

    @Test
    public void addAttributeTest5() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("name", classObj, Type.getType("long"), Attribute.Visibility.PUBLIC);
        classObj.addVariable(a);
        Assert.assertFalse(classObj.addVariable(b));
    }

    @Test
    public void removeAttributeTest() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute c = new Attribute("another random name", classObj, Type.getType("long"), Attribute.Visibility.PUBLIC);
        classObj.addVariable(a);
        classObj.addVariable(b);
        classObj.addVariable(c);
        classObj.removeVariable(1);
        Assert.assertTrue(classObj.getVariables().size() == 2);
        Assert.assertTrue(classObj.getVariables().get(0) == a);
        Assert.assertTrue(classObj.getVariables().get(1) == b);
    }

    @Test
    public void removeAttributeTest2() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        classObj.addVariable(a);
        classObj.removeVariable(0);
        Assert.assertTrue(classObj.getVariables().size() == 0);
        classObj.addVariable(a);
        Assert.assertTrue(classObj.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest6() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        String params[] = {"int", "long"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        classObj.addVariable(a);
        Assert.assertTrue(classObj.addMethod(m));
        Assert.assertTrue(classObj.getVariables().get(0) == a);
        Assert.assertTrue(classObj.getMethods().get(0) == m);
    }

    @Test
    public void addAttributeTest7() {
        String params[] = {"int", "long"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }
    
    @Test
    public void addAttributeTest8() {
        String params[] = {"int", "long"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "int"};
        Method m2 = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest9() {
        String params[] = {"int", "long"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int"};
        Method m2 = new Method("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest10() {
        String params[] = {"int"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest11() {
        String params[] = {"int", "long"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        classObj.addVariable(m);
        Assert.assertFalse(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
    }

    @Test
    public void addWrongAttributeTest() {
        String params[] = {"int", "long"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        Assert.assertFalse(classObj.addVariable(m));
    }

    @Test(expected = Test.None.class /* expecting no exception */)
    public void removeOutOfBoundsTest() {
        Attribute a = new Attribute("name", classObj, Type.getType("int"), Attribute.Visibility.PUBLIC);
        String params[] = {"int", "long"};
        Method m = new Method("name", classObj, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        classObj.addVariable(a);
        classObj.addMethod(m);
        
        classObj.removeMethod(-1);
        classObj.removeVariable(-1);
        classObj.removeMethod(1);
        classObj.removeVariable(1);
    }
}
