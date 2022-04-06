package test.diagramObjectUndoTest;

import org.junit.Assert;
import org.junit.Test;

import backend.diagramObject.Element;

public class ElementUndoTest {
    
    @Test
    public void SimpleUndoTest() {
        Element e = new Element("name");

        e.setName("newName");
        Assert.assertTrue(e.getName().equals("newName"));

        e.undo();
        Assert.assertTrue(e.getName().equals("name"));

        e.undo();
        Assert.assertTrue(e.getName().equals("name"));
    }
}
