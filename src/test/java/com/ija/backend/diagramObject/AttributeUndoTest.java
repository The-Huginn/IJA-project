/**
 * @file AttributeUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of Attribute class
 */
package com.ija.backend.diagramObject;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.ija.backend.diagramObject.Attribute;
import com.ija.backend.diagramObject.Type;
import com.ija.backend.diagramObject.Attribute.Visibility;
import com.ija.backend.diagramObject.helpers.TypeHelper;
public class AttributeUndoTest {
    
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
        Attribute attr = new Attribute("name", null);

        attr.setName("newName");

        attr.undo();

        assertTrue(attr.getName().equals("name"));
    }

    @Test
    public void setTypeTest() {
        Attribute attr = new Attribute("name", null, Type.getType("int"), Visibility.PUBLIC);

        attr.setType(Type.getType("long"));
        attr.setType(Type.getType("void"));

        attr.undo();

        assertTrue(attr.getType() == Type.getType("long"));

        attr.undo();

        assertTrue(attr.getType() == Type.getType("int"));
    }

    @Test
    public void setVisibilityTest() {
        Attribute attr = new Attribute("name", null, Type.getType("int"), Visibility.PUBLIC);

        attr.setVisibility(Visibility.PRIVATE);
        attr.setVisibility(Visibility.PRIVATE);

        attr.undo();

        assertTrue(attr.getVisibility() == Visibility.PRIVATE);

        attr.undo();

        assertTrue(attr.getVisibility() == Visibility.PUBLIC);
    }

    @Test
    public void allTest() {
        Attribute attr = new Attribute("name", null, Type.getType("int"), Visibility.PUBLIC);

        attr.setName("newName");
        attr.setType(Type.getType("long"));
        attr.setVisibility(Visibility.PRIVATE);

        attr.undo();

        assertTrue(attr.getVisibility() == Visibility.PUBLIC);

        attr.undo();

        assertTrue(attr.getType() == Type.getType("int"));

        attr.undo();

        assertTrue(attr.getName().equals("name"));
    }
}
