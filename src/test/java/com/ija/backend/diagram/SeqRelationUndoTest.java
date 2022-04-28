/**
 * @file SeqRelationUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of SeqRelation class
 */
package com.ija.backend.diagram;
import static org.junit.Assert.*;
import org.junit.Test;

import com.ija.backend.diagram.SeqRelation.SeqRelEnum;
import com.ija.backend.diagramObject.UMLClass;

public class SeqRelationUndoTest {
    
    @Test
    public void setFirstTest() {
        SeqRelation relation = new SeqRelation("name", null);

        UMLClass umlClass = new UMLClass("name", null);
        UMLClass umlClass2 = new UMLClass("another name", null);

        relation.setFirst(umlClass, 0);
        relation.setFirst(umlClass2, 0);
        relation.setFirst(new UMLClass("random", null), 0);

        relation.undo();

        assertTrue(relation.getFirst().getKey() == umlClass2);

        relation.undo();

        assertTrue(relation.getFirst().getKey() == umlClass);
    }

    @Test
    public void setSecondTest() {
        SeqRelation relation = new SeqRelation("name", null);

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
        SeqRelation relation = new SeqRelation("name", null);

        relation.setFirst(new UMLClass("name", null), 0);

        UMLClass umlClass = new UMLClass("another name", null);
        UMLClass umlClass2 = new UMLClass("name", null);

        assertTrue(relation.setSecond(umlClass, 0, SeqRelEnum.ASYNCHRONOUS));
        assertTrue(relation.setSecond(umlClass2, 0, SeqRelEnum.CREATION));
        assertTrue(relation.setSecond(new UMLClass("another another name", null), 0, SeqRelEnum.RETURN));

        relation.undo();

        assertTrue(relation.getSecond().getKey() == umlClass2);
        assertTrue(relation.getType() == SeqRelEnum.CREATION);

        relation.undo();

        assertTrue(relation.getSecond().getKey() == umlClass);
        assertTrue(relation.getType() == SeqRelEnum.ASYNCHRONOUS);

        relation.undo();

        assertTrue(relation.getSecond().getKey() == null);
        assertTrue(relation.getType() == SeqRelEnum.SYNCHROUNOUS);
    }

    @Test
    public void setTypeTest() {
        SeqRelation relation = new SeqRelation("name", null);

        relation.setType(SeqRelEnum.RETURN);
        relation.setType(SeqRelEnum.CREATION);

        relation.undo();

        assertTrue(relation.getType() == SeqRelEnum.RETURN);

        relation.undo();

        assertTrue(relation.getType() == SeqRelEnum.SYNCHROUNOUS);
    }

    @Test
    public void allTest() {
        SeqRelation relation = new SeqRelation("name", null);

        UMLClass firstClass = new UMLClass("name", null);
        UMLClass secondClass = new UMLClass("another name", null);
        UMLClass thirdClass = new UMLClass("another another name", null);

        relation.setFirst(firstClass, 0);
        relation.setType(SeqRelEnum.DESTRUCTION);
        relation.setSecond(secondClass, 0);
        relation.setSecond(thirdClass, 0, SeqRelEnum.CREATION);
        relation.setName("new name");

        relation.undo();

        assertTrue(relation.getType() == SeqRelEnum.CREATION);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == thirdClass);

        relation.undo();

        assertTrue(relation.getType() == SeqRelEnum.DESTRUCTION);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == secondClass);

        relation.undo();

        assertTrue(relation.getType() == SeqRelEnum.DESTRUCTION);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == null);

        relation.undo();

        assertTrue(relation.getType() == SeqRelEnum.SYNCHROUNOUS);
        assertTrue(relation.getFirst().getKey() == firstClass);
        assertTrue(relation.getSecond().getKey() == null);

        relation.undo();

        assertTrue(relation.getType() == SeqRelEnum.SYNCHROUNOUS);
        assertTrue(relation.getFirst().getKey() == null);
        assertTrue(relation.getSecond().getKey() == null);
    }
}
