/**
 * @file UMLClassUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of UMLCLass class
 */
package com.ija.backend.diagramObject;
import static org.junit.Assert.*;
import org.junit.Test;

import com.ija.backend.diagramObject.Attribute;
import com.ija.backend.diagramObject.Method;
import com.ija.backend.diagramObject.UMLClass;

public class UMLClassUndoTest {

    @Test
    public void setNameTest() {
        UMLClass umlClass = new UMLClass("name", null);

        umlClass.setName("newName");
        umlClass.setName("anotherName");

        umlClass.undo();

        assertTrue(umlClass.getName().equals("newName"));

        umlClass.undo();

        assertTrue(umlClass.getName().equals("name"));
    }

    @Test
    public void addVariableTest() {
        UMLClass umlClass = new UMLClass("name", null);

        Attribute var = new Attribute("name", umlClass);

        umlClass.addVariable(var);
        umlClass.addVariable(new Attribute("another name", umlClass));

        umlClass.undo();

        assertTrue(umlClass.getVariables().size() == 1);
        assertTrue(umlClass.getVariables().get(0) == var);

        umlClass.undo();

        assertTrue(umlClass.getVariables().size() == 0);
    }

    @Test
    public void AddMethodTest() {
        UMLClass umlClass = new UMLClass("name", null);

        Method method = new Method("name", umlClass);

        umlClass.addMethod(method);
        umlClass.addMethod(new Method("another name", umlClass));

        umlClass.undo();

        assertTrue(umlClass.getMethods().size() == 1);
        assertTrue(umlClass.getMethods().get(0) == method);

        umlClass.undo();

        assertTrue(umlClass.getMethods().size() == 0);
    }

    @Test
    public void removeVariableTest() {
        UMLClass umlClass = new UMLClass("name", null);

        Attribute var1 = new Attribute("name", umlClass);
        Attribute var2 = new Attribute("another name", umlClass);

        umlClass.addVariable(var1);
        umlClass.addVariable(var2);

        umlClass.removeVariable(0);
        umlClass.removeVariable(0);

        umlClass.undo();

        assertTrue(umlClass.getVariables().size() == 1);
        assertTrue(umlClass.getVariables().get(0) == var2);

        umlClass.undo();

        assertTrue(umlClass.getVariables().size() == 2);
        assertTrue(umlClass.getVariables().get(0) == var1);
        assertTrue(umlClass.getVariables().get(1) == var2);
    }

    @Test
    public void removeMethodTest() {
        UMLClass umlClass = new UMLClass("name", null);

        Method var1 = new Method("name", umlClass);
        Method var2 = new Method("another name", umlClass);

        umlClass.addMethod(var1);
        umlClass.addMethod(var2);

        umlClass.removeMethod(0);
        umlClass.removeMethod(0);

        umlClass.undo();

        assertTrue(umlClass.getMethods().size() == 1);
        assertTrue(umlClass.getMethods().get(0) == var2);

        umlClass.undo();

        assertTrue(umlClass.getMethods().size() == 2);
        assertTrue(umlClass.getMethods().get(0) == var1);
        assertTrue(umlClass.getMethods().get(1) == var2);
    }

    @Test
    public void allTest() {
        UMLClass umlClass = new UMLClass("name", null);

        umlClass.addMethod(new Method("name", umlClass));
        umlClass.setName("newName");
        umlClass.addVariable(new Attribute("name", umlClass));

        umlClass.undo();

        assertTrue(umlClass.getMethods().size() == 1);
        assertTrue(umlClass.getVariables().size() == 0);
        assertTrue(umlClass.getName().equals("newName"));

        umlClass.undo();

        assertTrue(umlClass.getMethods().size() == 1);
        assertTrue(umlClass.getVariables().size() == 0);
        assertTrue(umlClass.getName().equals("name"));

        umlClass.undo();

        assertTrue(umlClass.getMethods().size() == 0);
        assertTrue(umlClass.getVariables().size() == 0);
        assertTrue(umlClass.getName().equals("name"));
    }
}
