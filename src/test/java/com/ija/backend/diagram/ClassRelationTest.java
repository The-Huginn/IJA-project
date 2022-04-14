/**
 * @file ClassRelationTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for ClassRelation class
 */
package com.ija.backend.diagram;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagram.ClassRelation.ClassRelEnum;
import com.ija.backend.diagramObject.helpers.TypeHelper;
import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class ClassRelationTest {
    private ArrayList<UMLClass> classes;

    @Before
    public void setup() {
        TypeHelper.setup();
        classes = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            classes.add(new UMLClass("first class" + String.valueOf(i), null));
    }

    @After
    public void tearDown() {
        TypeHelper.tearDown();
        classes.clear();
    }

    // private Pair<UMLClass, Integer> createPair(UMLClass class1, int number) {
    //     return new Pair<UMLClass, Integer>(class1, number);
    // }

    @Test
    public void getterTest() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        assertTrue(relation.getType() == ClassRelation.ClassRelEnum.ASSOCIATION);
        assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(0));
        assertTrue(relation.getSecond().getKey() == classes.get(1));
    }

    @Test
    public void setRelationTest() {
        ClassRelation relation = new ClassRelation("relation", null);
        assertTrue(relation.setFirst(classes.get(0), 0));
        assertTrue(relation.setSecond(classes.get(1), 0));
        assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(0));
        assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(1));
    }

    @Test
    public void changeTypeTest() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setType(ClassRelation.ClassRelEnum.AGGREGATION);
        assertTrue(relation.getType() == ClassRelation.ClassRelEnum.AGGREGATION);
    }

    @Test
    public void changePeersTest() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setFirst(classes.get(2), 0);
        assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(2));
        relation.setSecond(classes.get(3), 0);
        assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(3));
    }

    @Test
    public void samePeerTest() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setSecond(classes.get(0), 0);
        relation.setType(ClassRelation.ClassRelEnum.COMPOSITION);
        assertTrue((UMLClass) relation.getFirst().getKey() == (UMLClass) relation.getSecond().getKey());
        assertTrue(relation.getType() == ClassRelation.ClassRelEnum.COMPOSITION);
    }

    @Test
    public void inheritInterfaceTest() {
        UMLInterface inter = new UMLInterface("name", null);
        UMLInterface newInter = new UMLInterface("another name", null);
        ClassRelation relation = new ClassRelation("relation", null, inter, 0, newInter, 0);
        assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);   // when there is interface as first it can only generalize
    }

    @Test
    public void inheritInterfaceTest2() {
        UMLInterface inter = new UMLInterface("name", null);
        ClassRelation relation = new ClassRelation("relation", null);
        relation.setFirst(inter, 0);
        relation.setType(ClassRelEnum.GENERALIZATION);
        assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);
        assertFalse(relation.setSecond(classes.get(0), 0));
    }

    @Test
    public void requireFirstTest() {
        ClassRelation relation = new ClassRelation("relation", null);
        assertFalse(relation.setSecond(classes.get(0), 0));
    }
    
    @Test
    public void implementInterfaceTest() {
        ClassRelation relation = new ClassRelation("relation", null);
        relation.setFirst(classes.get(0), 0);
        assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
        UMLInterface inter = new UMLInterface("name", null);
        assertTrue(relation.setSecond(inter, 0, ClassRelEnum.IMPLEMENTS));
        assertTrue(relation.getType() == ClassRelEnum.IMPLEMENTS);
    }

    @Test
    public void implementInterfaceTest2() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        UMLInterface inter = new UMLInterface("name", null);
        assertTrue(relation.setSecond(inter, 0, ClassRelEnum.IMPLEMENTS));
        assertTrue(relation.getType() == ClassRelEnum.IMPLEMENTS);
    }

    @Test
    public void implementInterfaceTest3() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        UMLInterface inter = new UMLInterface("name", null);
        assertFalse(relation.setFirst(inter, 0));    // no valid relation interface -> class
    }
}
