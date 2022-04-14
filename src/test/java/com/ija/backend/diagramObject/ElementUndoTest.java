/**
 * @file ElementUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of Element class
 */
package com.ija.backend.diagramObject;
import static org.junit.Assert.*;
import org.junit.Test;

import com.ija.backend.diagramObject.Element;

public class ElementUndoTest {
    
    @Test
    public void SimpleUndoTest() {
        Element e = new Element("name");

        e.setName("newName");
        assertTrue(e.getName().equals("newName"));

        e.undo();
        assertTrue(e.getName().equals("name"));

        e.undo();
        assertTrue(e.getName().equals("name"));
    }
}
