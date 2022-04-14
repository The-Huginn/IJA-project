/**
 * @file TypeUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of Type class
 */
package com.ija.backend.diagramObject;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.ija.backend.diagramObject.Type;
import com.ija.backend.diagramObject.helpers.TypeHelper;
public class TypeUndoTest {
    
    @Before
    public void setup() {
        TypeHelper.setup();
    }

    @After
    public void teardown() {
        TypeHelper.tearDown();
    }

    @Test
    public void AddTypeTest() {

        Type.addType("typeName");

        (new Type()).undo();

        assertNull(Type.getType("typeName"));
    }

    @Test
    public void AddTypesTest() {
        Type.addType("typeName");
        Type.addType("typeName2");

        (new Type()).undo();

        assertNotNull(Type.getType("typeName"));
        assertNull(Type.getType("typeName2"));

        (new Type()).undo();

        assertNull(Type.getType("typeName"));
    }

    @Test
    public void SetNameTest() {
        Type.addType("typeName");
        assertTrue(Type.getType("typeName").setName("newName"));

        (new Type()).undo();

        assertNotNull(Type.getType("typeName"));
        assertTrue(Type.getType("typeName").getName().equals("typeName"));
    }

    @Test
    public void initTypesTest() {
        Type.clearTypes();

        (new Type()).undo();

        assertNotNull(Type.getType("int"));
        assertNotNull(Type.getType("boolean"));
    }

    @Test
    public void removeTypesTest() {
        Type.addType("typeName");
        Type.addType("newType");

        Type.removeType("typeName");
        Type.removeType("newType");

        assertNull(Type.getType("typeName"));

        (new Type()).undo();

        assertNotNull(Type.getType("newType"));
        
        (new Type()).undo();
        
        assertNotNull(Type.getType("typeName"));
    }
}
