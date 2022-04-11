/**
 * @file UMLInterfaceUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of UMLInterface class
 */
package test.diagramObjectUndoTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.diagramObject.Attribute;
import backend.diagramObject.Method;
import backend.diagramObject.Type;
import backend.diagramObject.UMLInterface;
import backend.diagramObject.Attribute.Visibility;
import test.diagramObjectTest.helpers.TypeHelper;

public class UMLInterfaceUndoTest {

    @Before
    public void setup() {
        TypeHelper.setup();
    }

    @After
    public void teardown() {
        TypeHelper.tearDown();
    }
    
    @Test
    public void setNameTest() {
        UMLInterface umlInterface = new UMLInterface("name", null);

        umlInterface.setName("newName");
        umlInterface.setName("anotherName");

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getName().equals("newName"));

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getName().equals("name"));
    }

    @Test
    public void addVariableTest() {
        UMLInterface umlInterface = new UMLInterface("name", null);

        Attribute var = new Attribute("name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false);

        umlInterface.addVariable(var);
        umlInterface.addVariable(new Attribute("another name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false));

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getVariables().size() == 1);
        Assert.assertTrue(umlInterface.getVariables().get(0) == var);

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getVariables().size() == 0);
    }

    @Test
    public void AddMethodTest() {
        UMLInterface umlInterface = new UMLInterface("name", null);

        Method method = new Method("name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false);

        umlInterface.addMethod(method);
        umlInterface.addMethod(new Method("another name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false));

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getMethods().size() == 1);
        Assert.assertTrue(umlInterface.getMethods().get(0) == method);

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getMethods().size() == 0);
    }

    @Test
    public void removeVariableTest() {
        UMLInterface umlInterface = new UMLInterface("name", null);

        Attribute var1 = new Attribute("name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false);
        Attribute var2 = new Attribute("another name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false);

        umlInterface.addVariable(var1);
        umlInterface.addVariable(var2);

        umlInterface.removeVariable(0);
        umlInterface.removeVariable(0);

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getVariables().size() == 1);
        Assert.assertTrue(umlInterface.getVariables().get(0) == var2);

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getVariables().size() == 2);
        Assert.assertTrue(umlInterface.getVariables().get(0) == var1);
        Assert.assertTrue(umlInterface.getVariables().get(1) == var2);
    }

    @Test
    public void removeMethodTest() {
        UMLInterface umlInterface = new UMLInterface("name", null);

        Method var1 = new Method("name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false);
        Method var2 = new Method("another name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false);

        umlInterface.addMethod(var1);
        umlInterface.addMethod(var2);

        umlInterface.removeMethod(0);
        umlInterface.removeMethod(0);

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getMethods().size() == 1);
        Assert.assertTrue(umlInterface.getMethods().get(0) == var2);

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getMethods().size() == 2);
        Assert.assertTrue(umlInterface.getMethods().get(0) == var1);
        Assert.assertTrue(umlInterface.getMethods().get(1) == var2);
    }

    @Test
    public void allTest() {
        UMLInterface umlInterface = new UMLInterface("name", null);

        umlInterface.addMethod(new Method("name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false));
        umlInterface.setName("newName");
        umlInterface.addVariable(new Attribute("name", umlInterface, Type.getType("int"), Visibility.PUBLIC, false));

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getMethods().size() == 1);
        Assert.assertTrue(umlInterface.getVariables().size() == 0);
        Assert.assertTrue(umlInterface.getName().equals("newName"));

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getMethods().size() == 1);
        Assert.assertTrue(umlInterface.getVariables().size() == 0);
        Assert.assertTrue(umlInterface.getName().equals("name"));

        umlInterface.undo();

        Assert.assertTrue(umlInterface.getMethods().size() == 0);
        Assert.assertTrue(umlInterface.getVariables().size() == 0);
        Assert.assertTrue(umlInterface.getName().equals("name"));
    }
}
