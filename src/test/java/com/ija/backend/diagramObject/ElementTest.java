/**
 * @file ElementTest.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Element class
 */
package com.ija.backend.diagramObject;

import static org.junit.Assert.*;
import org.junit.Test;

import com.ija.backend.diagramObject.Element;

public class ElementTest {

    @Test
    public void CreationTest() {
        assertNotNull(new Element("first"));
    }

    @Test
    public void getNameTest() {
        Element e = new Element("name");
        assertTrue("name".equals(e.getName()));
    }

    @Test
    public void setNameTest() {
        Element e = new Element("name");
        assertTrue(e.setName("new name"));
        assertTrue("new name".equals(e.getName()));
    }

    @Test
    public void EqualsTest() {
        Element e1 = new Element("name");
        Element e2 = new Element("name");
        assertTrue(e1.equals(e2));
    }

    @Test
    public void NotEqualsTest() {
        Element e1 = new Element("name");
        Element e2 = new Element("another name");
        assertFalse(e1.equals(e2));
    }

    @Test
    public void EqualsAfterRenameTest() {
        Element e1 = new Element("name");
        Element e2 = new Element("name");
        assertTrue(e1.setName("new name for element"));
        assertTrue(e2.setName("new name for element"));
        assertTrue(e1.equals(e2));
    }

    @Test
    public void EqualsUnrelatedTest() {
        Element e1 = new Element("name");
        assertFalse(e1.equals(null));
    }

    @Test
    public void EqualsNullTest() {
        Element e = new Element("name");
        assertFalse(e.equals(null));
    }
}
