/**
 * @file ClassRelationUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of ClassRelation class
 */
package com.ija.backend.diagram;
import static org.junit.Assert.*;
import org.junit.Test;

import com.ija.backend.diagram.ClassRelation.ClassRelEnum;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;

public class ClassRelationUndoTest {
    
    @Test
    public void setFirstTest() {
        ClassRelation relation = new ClassRelation("name", null);

        UMLClass umlClass = new UMLClass("name", null);
        UMLInterface umlInterface = new UMLInterface("name", null);

        relation.setFirst(umlClass, 0);
        relation.setFirst(umlInterface, 0);
        relation.setFirst(new UMLClass("random", null), 0);

        relation.undo();

        assertTrue(relation.getFirst().getKey() == umlInterface);

        relation.undo();

        assertTrue(relation.getFirst().getKey() == umlClass);
    }

    @Test
    public void setSecondTest() {
        ClassRelation relation = new ClassRelation("name", null);

        relation.setFirst(new UMLClass("name", null), 0);

        UMLClass umlClass = new UMLClass("another name", null);
        UMLClass umlClass2 = new UMLClass("name", null);

        assertTrue(relation.setSecond(umlClass, 0));
        assertTrue(relation.setSecond(umlClass2, 0));
        assertTrue(relation.setSecond(new UMLClass("another another name", null), 0));

        relation.undo();

        assertTrue(relation.getSecond().getKey() == umlClass2);

        relation.undo();

        assertTrue(relation.getSecond().getKey() == umlClass);
    }

    @Test
    public void setSecondWithTypeTest() {
        ClassRelation relation = new ClassRelation("name", null);

        relation.setFirst(new UMLClass("name", null), 0);

        UMLClass umlClass = new UMLClass("another name", null);
        UMLInterface umlInterface = new UMLInterface("name", null);

        assertTrue(relation.setSecond(umlClass, 0, ClassRelEnum.AGGREGATION));
        assertTrue(relation.setSecond(umlInterface, 0, ClassRelEnum.IMPLEMENTS));
        assertTrue(relation.setSecond(new UMLClass("another another name", null), 0, ClassRelEnum.ASSOCIATION));

        relation.undo();

        assertTrue(relation.getSecond().getKey() == umlInterface);
        assertTrue(relation.getType() == ClassRelEnum.IMPLEMENTS);

        relation.undo();

        assertTrue(relation.getSecond().getKey() == umlClass);
        assertTrue(relation.getType() == ClassRelEnum.AGGREGATION);

        relation.undo();

        assertTrue(relation.getSecond().getKey() == null);
        assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
    }

    @Test
    public void setTypeTest() {
        ClassRelation relation = new ClassRelation("name", null);

        relation.setType(ClassRelEnum.GENERALIZATION);
        relation.setType(ClassRelEnum.AGGREGATION);

        relation.undo();

        assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);

        relation.undo();

        assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
    }

    @Test
    public void allTest() {
        ClassRelation relation = new ClassRelation("name", null);

        UMLClass firstClass = new UMLClass("name", null);
        UMLClass secondClass = new UMLClass("another name", null);
        UMLClass thirdClass = new UMLClass("another another name", null);

        relation.setFirst(firstClass, 0);
        relation.setType(ClassRelEnum.GENERALIZATION);
        relation.setSecond(secondClass, 0);
        relation.setSecond(thirdClass, 0, ClassRelEnum.AGGREGATION);
        relation.setName("new name");

        relation.undo();

        assertTrue(relation.getType() == ClassRelEnum.AGGREGATION);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == thirdClass);

        relation.undo();

        assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == secondClass);

        relation.undo();

        assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == null);

        relation.undo();

        assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == null);

        relation.undo();

        assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
        assertTrue(relation.getFirst().getKey() == null);
        assertTrue(relation.getSecond().getKey() == null);
    }
}
