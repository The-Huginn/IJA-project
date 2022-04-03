package test.diagramObjectUndoTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.diagramObject.Type;
import test.diagramObjectTest.helpers.TypeHelper;

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

        Assert.assertNull(Type.getType("typeName"));
    }

    @Test
    public void AddTypesTest() {
        Type.addType("typeName");
        Type.addType("typeName2");

        (new Type()).undo();

        Assert.assertNotNull(Type.getType("typeName"));
        Assert.assertNull(Type.getType("typeName2"));

        (new Type()).undo();

        Assert.assertNull(Type.getType("typeName"));
    }

    @Test
    public void SetNameTest() {
        Type.addType("typeName");
        Assert.assertTrue(Type.getType("typeName").setName("newName"));

        (new Type()).undo();

        Assert.assertNotNull(Type.getType("typeName"));
    }

    @Test
    public void initTypesTest() {
        Type.clearTypes();

        (new Type()).undo();

        Assert.assertNotNull(Type.getType("int"));
        Assert.assertNotNull(Type.getType("bool"));
    }

    @Test
    public void removeTypesTest() {
        Type.addType("typeName");
        Type.addType("newType");

        Type.removeType("typeName");
        Type.removeType("newType");

        Assert.assertNull(Type.getType("typeName"));

        (new Type()).undo();

        Assert.assertNotNull(Type.getType("typeName"));

        (new Type()).undo();

        Assert.assertNotNull(Type.getType("newType"));
    }
}
