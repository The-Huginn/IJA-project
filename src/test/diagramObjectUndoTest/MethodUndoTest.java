package test.diagramObjectUndoTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backend.diagramObject.Method;
import backend.diagramObject.Type;
import backend.diagramObject.Attribute.Visibility;
import test.diagramObjectTest.helpers.TypeHelper;

public class MethodUndoTest {
    
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
        String params[] = {"int", "string"};
        Method method = new Method("name", null, Type.getType("int"), Visibility.PUBLIC, params);

        method.setName("newName");
        method.setName("anotherName");

        method.undo();

        Assert.assertTrue(method.getName().equals("newName"));

        method.undo();

        Assert.assertTrue(method.getName().equals("name"));
    }

    @Test
    public void setParametersTest() {
        String params[] = {"int", "string"};
        Method method = new Method("name", null, Type.getType("int"), Visibility.PUBLIC, params);

        String newParams[] = {"long", "int"};
        method.setParameters(newParams);
        method.setParameters(params);

        method.undo();

        Assert.assertTrue(method.getParameters().get(0) == Type.getType("long") && method.getParameters().get(1) == Type.getType("int"));

        method.undo();

        Assert.assertTrue(method.getParameters().get(0) == Type.getType("int") && method.getParameters().get(1) == Type.getType("string"));
    }

    @Test
    public void setTypeTest() {
        String params[] = {"int", "string"};
        Method method = new Method("name", null, Type.getType("int"), Visibility.PUBLIC, params);

        method.setType(Type.getType("long"));

        method.undo();

        Assert.assertTrue(method.getType() == Type.getType("int"));
    }

    @Test
    public void setVisibilityTest() {
        String params[] = {"int", "string"};
        Method method = new Method("name", null, Type.getType("int"), Visibility.PUBLIC, params);

        method.setVisibility(Visibility.PRIVATE);

        method.undo();

        Assert.assertTrue(method.getVisibility() == Visibility.PUBLIC);
    }
}
