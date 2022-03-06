/**
 * @file ElementTest.java
 * @author Rastislav Budinsky
 * @brief This file contains tests for Element class
 */
package test.diagramObjectTest;

import org.junit.Assert;
import org.junit.Test;

import backend.diagramObject.Element;

import java.util.ArrayList;
import java.util.Arrays;

public class ElementTest {

    @Test
    public void CreationTest() {
        Assert.assertNotNull(new Element("first"));
    }

    @Test
    public void getNameTest() {
        Element e = new Element("name");
        Assert.assertTrue("name".equals(e.getName()));
    }

    @Test
    public void setNameTest() {
        Element e = new Element("name");
        Assert.assertTrue(e.setName("new name"));
        Assert.assertTrue("new name".equals(e.getName()));
    }

    @Test
    public void EqualsTest() {
        Element e1 = new Element("name");
        Element e2 = new Element("name");
        Assert.assertTrue(e1.equals(e2));
    }

    @Test
    public void NotEqualsTest() {
        Element e1 = new Element("name");
        Element e2 = new Element("another name");
        Assert.assertFalse(e1.equals(e2));
    }

    @Test
    public void EqualsAfterRenameTest() {
        Element e1 = new Element("name");
        Element e2 = new Element("name");
        Assert.assertTrue(e1.setName("new name for element"));
        Assert.assertTrue(e2.setName("new name for element"));
        Assert.assertTrue(e1.equals(e2));
    }

    @Test
    public void EqualsUnrelatedTest() {
        Element e1 = new Element("name");
        ArrayList<Integer> e2 = new ArrayList<>(Arrays.asList(1, 2));
        Assert.assertFalse(e1.equals(e2));
    }

    @Test
    public void EqualsNullTest() {
        Element e = new Element("name");
        Assert.assertFalse(e.equals(null));
    }
}
