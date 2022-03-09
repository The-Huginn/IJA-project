package test.diagramTest;

import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;
import backend.diagramObject.UMLObject;
import javafx.util.Pair;
import backend.diagram.SeqRelation;
import backend.diagramObject.Attribute;
import backend.diagramObject.Method;
import backend.diagramObject.Type;

import java.util.ArrayList;
import test.diagramObjectTest.helpers.TypeHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class SeqRelationTest {
    private ArrayList<UMLClass> classes;
    private Method method;
    private Attribute attribute;

    @BeforeEach
    public void setup() {
        TypeHelper.setup();
        classes = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            classes.add(new UMLClass("first class" + String.valueOf(i), null));
        method = new Method("method", classes.get(0), Type.getType("int"), Attribute.Visibility.PUBLIC, new String[] {"int", "string"});
        classes.get(0).addMethod(method);
        attribute = new Attribute("attribute", classes.get(0) ,Type.getType("int"), Attribute.Visibility.PUBLIC);
        classes.get(0).addVariable(attribute);
    }

    @AfterEach
    public void tearDown() {
        TypeHelper.tearDown();
        classes.clear();
        method = null;
        attribute = null;
    }

    // private Pair<UMLClass, Integer> createPair(UMLClass class1, int number) {
    //     return new Pair<UMLClass, Integer>(class1, number);
    // }

    @Test
    public void getterTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        Pair<UMLObject, Integer> pair = relation.getFirst();
        Assert.assertTrue(relation.getType() == SeqRelation.SeqRelEnum.SYNCHROUNOUS);
        Assert.assertTrue((UMLClass) pair.getKey() == classes.get(0) && pair.getValue() == 0);
        pair = relation.getSecond();
        Assert.assertTrue((UMLClass) pair.getKey() == classes.get(1) && pair.getValue() == 0);
    }

    @Test
    public void setRelationTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        relation.setFirst(classes.get(0), 1);
        relation.setSecond(classes.get(1), 2);
        Assert.assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(0) && relation.getFirst().getValue() == 1);
        Assert.assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(1) && relation.getSecond().getValue() == 2);
    }

    @Test
    public void changeTypeTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        relation.setType(SeqRelation.SeqRelEnum.RETURN);
        Assert.assertTrue(relation.getType() == SeqRelation.SeqRelEnum.RETURN);
    }

    @Test
    public void changePeersTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setFirst(classes.get(2), 2);
        Assert.assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(2) && relation.getFirst().getValue() == 2);
        relation.setSecond(classes.get(3), 3);
        Assert.assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(3) && relation.getSecond().getValue() == 3);
    }

    @Test
    public void samePeerTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setSecond(classes.get(0), 1);
        relation.setType(SeqRelation.SeqRelEnum.DESTRUCTION);
        Assert.assertTrue((UMLClass) relation.getFirst().getKey() == (UMLClass) relation.getSecond().getKey());
        Assert.assertTrue(relation.getFirst().getValue() == 0 && relation.getSecond().getValue() == 1);
        Assert.assertTrue(relation.getType() == SeqRelation.SeqRelEnum.DESTRUCTION);
    }

    @Test
    public void samePeerTest2() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        Assert.assertFalse(relation.setSecond(classes.get(0), 0));   // we probably dont want to allow self-reference
    }

    @Test
    public void setMethodTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        Assert.assertTrue(relation.setMethod("method(10, \"string\")"));
        Assert.assertTrue(relation.getMethod().equals("method"));
        Assert.assertTrue(relation.checkCorrect());
    }

    @Test
    public void setNoteTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setNote("note this");
        Assert.assertTrue(relation.getNote().equals("note this"));
    }

    @Test
    public void changeMethodParamsTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setMethod("method(10, \"string\")");
        method.setParameters(new String[] {"int", "string", "int"});
        Assert.assertFalse(relation.checkCorrect());
    }

    @Test
    public void deleteMethodTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setMethod("method(10, \"string\")");
        classes.get(0).removeMethod(0);
        Assert.assertFalse(relation.checkCorrect());
    }

    @Test
    public void wrongParamCountTest() {
        method.setParameters(new String[] {"int"});
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        Assert.assertFalse(relation.setMethod("method()"));
    }

    @Test
    public void oneParamTest() {
        method.setParameters(new String[] {"int"});
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        Assert.assertTrue(relation.setMethod("method(10)"));
    }

    @Test
    public void zeroParamTest() {
        method.setParameters(new String[] {});
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        Assert.assertFalse(relation.setMethod("method(10)"));   // there should be nothing between parenthesis except white spaces
        Assert.assertTrue(relation.setMethod("method(   )"));
    }

    @Test
    public void setNotMethodTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        Assert.assertFalse(relation.setMethod("attribute(nieco)"));
    }

    @Test
    public void setInterfaceTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        UMLInterface inter = new UMLInterface("name", null);
        Assert.assertFalse(relation.setFirst(inter, 0));
        Assert.assertFalse(relation.setSecond(inter, 0));
    }

    @Test
    public void setFirstTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        Assert.assertFalse(relation.setSecond(classes.get(0), 0));
    }
}
