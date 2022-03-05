package test.diagramTest;

import backend.diagramObject.UMLClass;
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
            classes.add(new UMLClass("first class" + String.valueOf(i)));
        method = new Method("method", Type.getType("int"), new String[] {"int", "string"});
        attribute = new Attribute("attribute", Type.getType("int"));
    }

    @AfterEach
    public void tearDown() {
        TypeHelper.tearDown();
        classes.clear();
        method = null;
        attribute = null;
    }

    private Pair<UMLClass, Integer> createPair(UMLClass class1, int number) {
        return new Pair<UMLClass, Integer>(class1, number);
    }

    @Test
    public void getterTest() {
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Pair<UMLClass, Integer> pair = relation.getFirst();
        Assert.assertTrue(relation.getType() == SeqRelation.SeqRelEnum.SYNCHROUNOUS);
        Assert.assertTrue(pair.getKey() == classes.get(0) && pair.getValue() == 0);
        pair = relation.getSecond();
        Assert.assertTrue(pair.getKey() == classes.get(1) && pair.getValue() == 0);
    }

    @Test
    public void setRelationTest() {
        SeqRelation relation = new SeqRelation("relation");
        relation.setFirst(createPair(classes.get(0), 1));
        relation.setSecond(createPair(classes.get(1), 2));
        Assert.assertTrue(relation.getFirst().getKey() == classes.get(0) && relation.getFirst().getValue() == 1);
        Assert.assertTrue(relation.getSecond().getKey() == classes.get(1) && relation.getSecond().getValue() == 2);
    }

    @Test
    public void changeTypeTest() {
        SeqRelation relation = new SeqRelation("relation");
        relation.setType(SeqRelation.SeqRelEnum.RETURN);
        Assert.assertTrue(relation.getType() == SeqRelation.SeqRelEnum.RETURN);
    }

    @Test
    public void changePeersTest() {
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        relation.setFirst(createPair(classes.get(2), 2));
        Assert.assertTrue(relation.getFirst().getKey() == classes.get(2) && relation.getFirst().getValue() == 2);
        relation.setSecond(createPair(classes.get(3), 3));
        Assert.assertTrue(relation.getSecond().getKey() == classes.get(3) && relation.getSecond().getValue() == 3);
    }

    @Test
    public void samePeerTest() {
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        relation.setSecond(createPair(classes.get(0) 1));
        relation.setType(SeqRelation.SeqRelEnum.DESTRUCTION);
        Assert.assertTrue(relation.getFirst().getKey() == relation.getSecond().getKey());
        Assert.assertTrue(relation.getFirst().getValue() == 0 && relation.getSecond().getValue() == 1);
        Assert.assertTrue(relation.getType() == SeqRelation.SeqRelEnum.DESTRUCTION);
    }

    @Test
    public void samePeerTest() {
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Assert.assertFalse(relation.setSecond(createPair(classes.get(0) 0)));   // we probably dont want to allow self-reference
    }

    @Test
    public void setMethodTest() {
        classes.get(0).addAttribute(method);
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Assert.assertTrue(relation.setMethod("method(10, \"string\")"));
        Assert.assertTrue(relation.getMethod().equals("method"));
        Assert.assertTrue(relation.checkMethod());
    }

    @Test
    public void setNoteTest() {
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        relation.setNote("note this");
        Assert.assertTrue(relation.getNote().equals("note this"));
    }

    @Test
    public void changeMethodParamsTest() {
        classes.get(0).addAttribute(method);
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        relation.setMethod("method(10, \"string\")");
        method.setParameters(new String[] {"int", "string", "int"});
        Assert.assertFalse(relation.checkMethod());
    }

    @Test
    public void deleteMethodTest() {
        classes.get(0).addAttribute(method);
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        relation.setMethod("method(10, \"string\")");
        // TODO
    }

    @Test
    public void wrongParamCountTest() {
        method.setParameters(new String[] {"int"});
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Assert.assertFalse(relation.setMethod("method()"));
    }

    @Test
    public void oneParamTest() {
        method.setParameters(new String[] {"int"});
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Assert.assertTrue(relation.setMethod("method(10)"));
    }

    @Test
    public void zeroParamTest() {
        method.setParameters(new String[] {});
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Assert.assertFalse(relation.setMethod("method(10)"));   // there should be nothing between parenthesis
        Assert.assertTrue(relation.setMethod("method()"));
    }

    @Test
    public void setNotMethodTest() {
        classes.get(0).addAttribute(attribute);
        SeqRelation relation = new SeqRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Assert.assertFalse(relation.setMethod("attribute(nieco)"));
    }
}
