package test.diagramObjectTest;

import backend.diagramObject.Type;
import backend.diagramObject.Attribute;
import backend.diagramObject.Method;
import backend.diagramObject.UMLClass;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.diagramObjectTest.helpers.TypeHelper;

public class UMLClassTest {
    private UMLClass umlClass;

    @Before
    public void setup() {
        umlClass = new UMLClass("umlClass name", null);
        TypeHelper.setup();
    }

    @After
    public void tearDown() {
        TypeHelper.tearDown();
        umlClass = null;
    }

    @Test
    public void addAttributeTest() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Assert.assertTrue(umlClass.addVariable(a));
    }

    @Test
    public void getAttributeTest() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PRIVATE);
        UMLClass umlClass = new UMLClass("umlClass name", null);
        umlClass.addVariable(a);
        Assert.assertTrue(umlClass.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest3() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PROTECTED);
        Attribute b = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        UMLClass umlClass = new UMLClass("umlClass name", null);
        umlClass.addVariable(a);
        Assert.assertFalse(umlClass.addVariable(b));
        Assert.assertTrue(umlClass.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest4() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        umlClass.addVariable(a);
        umlClass.addVariable(b);
        Assert.assertTrue(umlClass.getVariables().size() == 2);
        Assert.assertTrue(umlClass.getVariables().get(0) == a);
        Assert.assertTrue(umlClass.getVariables().get(1) == b);
    }

    @Test
    public void addAttributeTest5() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("name", umlClass, Type.getType("long"), Attribute.Visibility.PUBLIC);
        umlClass.addVariable(a);
        Assert.assertFalse(umlClass.addVariable(b));
    }

    @Test
    public void removeAttributeTest() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute c = new Attribute("another random name", umlClass, Type.getType("long"), Attribute.Visibility.PUBLIC);
        umlClass.addVariable(a);
        umlClass.addVariable(b);
        umlClass.addVariable(c);
        umlClass.removeVariable(1);
        Assert.assertTrue(umlClass.getVariables().size() == 2);
        Assert.assertTrue(umlClass.getVariables().get(0) == a);
        Assert.assertTrue(umlClass.getVariables().get(1) == c);
    }

    @Test
    public void removeAttributeTest2() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        umlClass.addVariable(a);
        umlClass.removeVariable(0);
        Assert.assertTrue(umlClass.getVariables().size() == 0);
        umlClass.addVariable(a);
        Assert.assertTrue(umlClass.getVariables().get(0) == a);
    }

    @Test
    public void addAttributeTest6() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        String params[] = {"int", "long"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        umlClass.addVariable(a);
        Assert.assertTrue(umlClass.addMethod(m));
        Assert.assertTrue(umlClass.getVariables().get(0) == a);
        Assert.assertTrue(umlClass.getMethods().get(0) == m);
    }

    @Test
    public void addAttributeTest7() {
        String params[] = {"int", "long"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        umlClass.addMethod(m);
        Assert.assertTrue(umlClass.addMethod(m2));
        Assert.assertTrue(umlClass.getMethods().get(0) == m);
        Assert.assertTrue(umlClass.getMethods().get(1) == m2);
    }
    
    @Test
    public void addAttributeTest8() {
        String params[] = {"int", "long"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "int"};
        Method m2 = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        umlClass.addMethod(m);
        Assert.assertTrue(umlClass.addMethod(m2));
        Assert.assertTrue(umlClass.getMethods().get(0) == m);
        Assert.assertTrue(umlClass.getMethods().get(1) == m2);
    }

    @Test
    public void addAttributeTest9() {
        String params[] = {"int", "long"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int"};
        Method m2 = new Method("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        umlClass.addMethod(m);
        Assert.assertTrue(umlClass.addMethod(m2));
        Assert.assertTrue(umlClass.getMethods().get(0) == m);
        Assert.assertTrue(umlClass.getMethods().get(1) == m2);
    }

    @Test
    public void addAttributeTest10() {
        String params[] = {"int"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params2);
        umlClass.addMethod(m);
        Assert.assertTrue(umlClass.addMethod(m2));
        Assert.assertTrue(umlClass.getMethods().get(0) == m);
        Assert.assertTrue(umlClass.getMethods().get(1) == m2);
    }

    @Test
    public void addAttributeTest11() {
        String params[] = {"int", "long"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long"};
        Method m2 = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        umlClass.addMethod(m);
        Assert.assertFalse(umlClass.addMethod(m2));
        Assert.assertTrue(umlClass.getMethods().get(0) == m);
    }

    @Test
    public void addWrongAttributeTest() {
        String params[] = {"int", "long"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        Assert.assertFalse(umlClass.addVariable(m));
    }

    @Test(expected = Test.None.class /* expecting no exception */)
    public void removeOutOfBoundsTest() {
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        String params[] = {"int", "long"};
        Method m = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        umlClass.addVariable(a);
        umlClass.addMethod(m);
        
        umlClass.removeMethod(-1);
        umlClass.removeVariable(-1);
        umlClass.removeMethod(1);
        umlClass.removeVariable(1);
    }

    // This test tests Attribute, but working UMLClass is needed
    @Test
    public void setNameTest() {
        UMLClass umlClass = new UMLClass("name", null);
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        umlClass.addVariable(a);
        Attribute b = new Attribute("another name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        umlClass.addVariable(b);
        Assert.assertFalse(a.setName("another name"));
        Assert.assertTrue(a.getName().equals("name"));
    }

    // This test tests Attribute, but working UMLClass is needed
    @Test
    public void setNameTest2() {
        UMLClass umlClass = new UMLClass("name", null);
        Attribute a = new Attribute("name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC);
        Attribute b = new Attribute("another name", umlClass, Type.getType("long"), Attribute.Visibility.PRIVATE);
        Attribute c = new Attribute("another random name", umlClass, Type.getType("string"), Attribute.Visibility.PACKAGE);
        umlClass.addVariable(a);
        umlClass.addVariable(b);
        umlClass.addVariable(c);
        Assert.assertFalse(a.setName("another name"));
        a.setType(Type.getType("long"));
        Assert.assertTrue(a.getType() == Type.getType("long"));
        Assert.assertFalse(a.setName("another name"));
        Assert.assertTrue(a.getName().equals("name"));
    }

    // This test tests Method, but working UMLClass is needed
    @Test
    public void setNameTest3() {
        UMLClass umlClass = new UMLClass("name", null);
        String params[] = {"int", "long"};
        Method a = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        Method b = new Method("another name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        umlClass.addMethod(a);
        umlClass.addMethod(b);
        Assert.assertFalse(a.setName("another name"));
    }

    // This test tests Method, but working UMLClass is needed
    @Test
    public void setNameTest4() {
        UMLClass umlClass = new UMLClass("name", null);
        String params[] = {"int", "long"};
        Method a = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long", "long"};
        Method b = new Method("another name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        umlClass.addMethod(a);
        Assert.assertTrue(umlClass.addMethod(b));
        Assert.assertTrue(a.setName("another name"));   // another signature
    }

    // This test tests Method, but working UMLClass is needed
    @Test
    public void setNameTest5() {
        UMLClass umlClass = new UMLClass("name", null);
        String params[] = {"int", "long"};
        Method a = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        String params2[] = {"int", "long", "long"};
        Method b = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params2);
        umlClass.addMethod(a);
        Assert.assertTrue(umlClass.addMethod(b));
        Assert.assertFalse(a.setParameters(params));   // same as 'a' signature
    }

    // This test tests Method, but working UMLClass is needed
    @Test
    public void setNameTest6() {
        UMLClass umlClass = new UMLClass("name", null);
        String params[] = {"int", "long"};
        Method a = new Method("name", umlClass, Type.getType("void"), Attribute.Visibility.PUBLIC, params);
        Method b = new Method("another name", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params);
        umlClass.addMethod(a);
        Assert.assertTrue(umlClass.addMethod(b));
        Assert.assertTrue(a.setName("another name"));   // another return type
    }
}
