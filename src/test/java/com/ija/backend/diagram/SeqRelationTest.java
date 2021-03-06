/**
 * @file SeqRelationTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for SeqRelation class
 */
package com.ija.backend.diagram;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;
import com.ija.backend.diagramObject.UMLObject;
import com.ija.backend.diagramObject.Attribute;
import com.ija.backend.diagramObject.Method;
import com.ija.backend.diagramObject.Type;

import javafx.util.Pair;
import java.util.ArrayList;
import com.ija.backend.diagramObject.helpers.TypeHelper;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class SeqRelationTest {
    private ArrayList<UMLClass> classes;
    private Method method;
    private Attribute attribute;

    @Before
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

    @After
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
        assertTrue(relation.getType() == SeqRelation.SeqRelEnum.SYNCHROUNOUS);
        assertTrue((UMLClass) pair.getKey() == classes.get(0) && pair.getValue() == 0);
        pair = relation.getSecond();
        assertTrue((UMLClass) pair.getKey() == classes.get(1) && pair.getValue() == 0);
    }

    @Test
    public void setRelationTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        relation.setFirst(classes.get(0), 1);
        relation.setSecond(classes.get(1), 2);
        assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(0) && relation.getFirst().getValue() == 1);
        assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(1) && relation.getSecond().getValue() == 2);
    }

    @Test
    public void changeTypeTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        relation.setType(SeqRelation.SeqRelEnum.RETURN);
        assertTrue(relation.getType() == SeqRelation.SeqRelEnum.RETURN);
    }

    @Test
    public void changePeersTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setFirst(classes.get(2), 2);
        assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(2) && relation.getFirst().getValue() == 2);
        relation.setSecond(classes.get(3), 3);
        assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(3) && relation.getSecond().getValue() == 3);
    }

    @Test
    public void samePeerTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        assertTrue(relation.setSecond(classes.get(0), 1));
        assertTrue(relation.setType(SeqRelation.SeqRelEnum.DESTRUCTION));
        assertTrue((UMLClass) relation.getFirst().getKey() == (UMLClass) relation.getSecond().getKey());
        assertTrue(relation.getFirst().getValue() == 0 && relation.getSecond().getValue() == 1);
        assertTrue(relation.getType() == SeqRelation.SeqRelEnum.DESTRUCTION);
    }

    // @Test
    // public void samePeerTest2() {
    //     SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
    //     assertFalse(relation.setSecond(classes.get(0), 0));
    //     assertFalse(relation.setFirst(classes.get(1), 0));
    //     assertTrue(relation.getFirst().getKey() == classes.get(0));
    //     assertTrue(relation.getFirst().getValue() == 0);
    //     assertTrue(relation.getSecond().getKey() == classes.get(1));
    //     assertTrue(relation.getSecond().getValue() == 0);
    // }

    @Test
    public void setMethodTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(1), 0, classes.get(0), 0);
        assertTrue(relation.setMethod("method(10, \"string\")"));
        assertTrue(relation.getMethod().equals("method"));
        assertTrue(relation.checkCorrect());
    }

    @Test
    public void setNoteTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setNote("note this");
        assertTrue(relation.getNote().equals("note this"));
    }

    @Test
    public void changeMethodParamsTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setMethod("method(10, \"string\")");
        method.setParameters(new String[] {"int", "string", "int"});
        assertFalse(relation.checkCorrect());
    }

    @Test
    public void deleteMethodTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setMethod("method(10, \"string\")");
        classes.get(0).removeMethod(0);
        assertFalse(relation.checkCorrect());
    }

    @Test
    public void wrongParamCountTest() {
        method.setParameters(new String[] {"int"});
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        assertFalse(relation.setMethod("method()"));
    }

    @Test
    public void oneParamTest() {
        method.setParameters(new String[] {"int"});
        SeqRelation relation = new SeqRelation("relation", null, classes.get(1), 0, classes.get(0), 0);
        assertTrue(relation.setMethod("method(10)"));
    }

    @Test
    public void zeroParamTest() {
        method.setParameters(new String[] {});
        SeqRelation relation = new SeqRelation("relation", null, classes.get(1), 0, classes.get(0), 0);
        assertFalse(relation.setMethod("method(10)"));   // there should be nothing between parenthesis except white spaces
        assertTrue(relation.setMethod("method(   )"));
    }

    @Test
    public void setNotMethodTest() {
        SeqRelation relation = new SeqRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        assertFalse(relation.setMethod("attribute(nieco)"));
    }

    @Test
    public void setInterfaceTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        UMLInterface inter = new UMLInterface("name", null);
        assertFalse(relation.setFirst(inter, 0));
        assertFalse(relation.setSecond(inter, 0));
    }

    @Test
    public void setFirstTest() {
        SeqRelation relation = new SeqRelation("relation", null);
        assertFalse(relation.setSecond(classes.get(0), 0));
    }
}
