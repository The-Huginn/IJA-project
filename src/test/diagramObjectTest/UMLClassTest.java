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
        UMLClass classObj = new UMLClass("classObj name");
        Assert.assertTrue(classObj.addVariable(a));
    }

    @Test
    public void getAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PRIVATE);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(a);
        Assert.assertTrue(classObj.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest3() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PROTECTED);
        Attribute b = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(a);
        Assert.assertFalse(classObj.addVariable(b));
        Assert.assertTrue(classObj.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(a);
        classObj.addVariable(b);
        Assert.assertTrue(classObj.getVariables().size() == 2);
        Assert.assertTrue(classObj.getVariables().get(0) == a);
        Assert.assertTrue(classObj.getVariables().get(1) == b);
    }

    @Test
    public void addAttributeTest5() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("name", Type.getType("long"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(a);
        Assert.assertFalse(classObj.addVariable(b));
    }

    @Test
    public void removeAttributeTest() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute c = new Attribute("another random name", Type.getType("long"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("classObj name");
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
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addBariable(a);
        classObj.removeVariable(0);
        Assert.assertTrue(classObj.getVariables().size() == 0);
        classObj.addVariable(a);
        Assert.assertTrue(classObj.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", Type.getType("int"), Attribute.Visibility.PUBLIC);
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(a);
        Assert.assertTrue(classObj.addVariable(m));
        Assert.assertTrue(classObj.getVariables().get(0) == a);
        Assert.assertTrue(classObj.getVariables().get(1) == m);
    }

    @Test
    public void addAttributeTest5() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }
    
    @Test
    public void addAttributeTest6() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "int"};
        Method m2 = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest6() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int"};
        Method m2 = new Method("name", Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest7() {
        String params[] = {"int"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(m);
        Assert.assertTrue(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
        Assert.assertTrue(classObj.getVariables().get(1) == m2);
    }

    @Test
    public void addAttributeTest5() {
        String params[] = {"int", "long"};
        Method m = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        UMLClass classObj = new UMLClass("classObj name");
        classObj.addVariable(m);
        Assert.assertFalse(classObj.addVariable(m2));
        Assert.assertTrue(classObj.getVariables().get(0) == m);
    }
}
