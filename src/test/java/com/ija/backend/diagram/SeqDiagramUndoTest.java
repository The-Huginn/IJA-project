/**
 * @file SeqDiagramUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of SeqDiagram class
 */
package com.ija.backend.diagram;
import static org.junit.Assert.*;
import org.junit.Test;

import com.ija.backend.diagramObject.UMLClass;

public class SeqDiagramUndoTest {
    
    @Test
    public void setNameTest() {
        SeqDiagram diagram = new SeqDiagram("name", null);

        diagram.setName("newName");
        diagram.setName("another newName");

        diagram.undo();

        assertTrue(diagram.getName().equals("newName"));

        diagram.undo();

        assertTrue(diagram.getName().equals("name"));
    }

    @Test
    public void addInstanceTest() {
        SeqDiagram diagram = new SeqDiagram("name", null);

        UMLClass umlClass1 = new UMLClass("name1", null);
        UMLClass umlClass2 = new UMLClass("name2", null);

        diagram.addInstance(umlClass1, 0);
        diagram.addInstance(umlClass2, 0);
        diagram.addInstance(new UMLClass("another name", null), 0);

        diagram.undo();

        assertTrue(diagram.getInstances().size() == 2);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass1);
        assertTrue(diagram.getInstances().get(1).getKey() == umlClass2);

        diagram.undo();

        assertTrue(diagram.getInstances().size() == 1);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass1);

        diagram.undo();
        
        assertTrue(diagram.getInstances().size() == 0);
    }

    @Test
    public void removeInstanceTest() {
        SeqDiagram diagram = new SeqDiagram("name", null);

        UMLClass umlClass1 = new UMLClass("name1", null);
        UMLClass umlClass2 = new UMLClass("name2", null);

        diagram.addInstance(umlClass1, 0);
        diagram.addInstance(umlClass2, 0);
        diagram.addInstance(umlClass1, 1);

        diagram.removeInstance(0);
        diagram.removeInstance(1);
        diagram.removeInstance(0);

        diagram.undo();

        assertTrue(diagram.getInstances().size() == 1);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass2);

        diagram.undo();

        assertTrue(diagram.getInstances().size() == 2);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass2);
        assertTrue(diagram.getInstances().get(1).getKey() == umlClass1 && diagram.getInstances().get(1).getValue() == 1);

        diagram.undo();

        assertTrue(diagram.getInstances().size() == 3);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass1 && diagram.getInstances().get(0).getValue() == 0);
        assertTrue(diagram.getInstances().get(1).getKey() == umlClass2);
        assertTrue(diagram.getInstances().get(2).getKey() == umlClass1 && diagram.getInstances().get(2).getValue() == 1);
    }
    
    @Test
    public void addRelationTest() {
        SeqDiagram diagram = new SeqDiagram("name", null);

        UMLClass umlClass1 = new UMLClass("name1", null);
        UMLClass umlClass2 = new UMLClass("name2", null);

        diagram.addInstance(umlClass1, 0);
        diagram.addInstance(umlClass2, 0);

        SeqRelation relation = new SeqRelation("name", diagram, umlClass1, 0, umlClass2, 0);
        SeqRelation relation2 = new SeqRelation("another name", diagram, umlClass2, 0, umlClass1, 0);

        diagram.addRelation(relation);
        diagram.addRelation(relation2);

        diagram.setName("newName");

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 2);
        assertTrue(diagram.getRelations().get(0) == relation);
        assertTrue(diagram.getRelations().get(1) == relation2);

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 1);
        assertTrue(diagram.getRelations().get(0) == relation);

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 0);
    }

    @Test
    public void removeRelationTest() {
        SeqDiagram diagram = new SeqDiagram("name", null);

        UMLClass umlClass1 = new UMLClass("name1", null);
        UMLClass umlClass2 = new UMLClass("name2", null);

        diagram.addInstance(umlClass1, 0);
        diagram.addInstance(umlClass2, 0);

        SeqRelation relation = new SeqRelation("name", diagram, umlClass1, 0, umlClass2, 0);
        SeqRelation relation2 = new SeqRelation("another name", diagram, umlClass2, 0, umlClass1, 0);
        SeqRelation relation3 = new SeqRelation("another another name", diagram, umlClass2, 0, umlClass1, 0);

        diagram.addRelation(relation);
        diagram.addRelation(relation2);
        diagram.addRelation(relation3);

        diagram.removeRelation(0);
        diagram.removeRelation(1);
        diagram.removeRelation(0);

        diagram.setName("newName");

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 0);

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 1);
        assertTrue(diagram.getRelations().get(0) == relation2);

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 2);
        assertTrue(diagram.getRelations().get(0) == relation2);
        assertTrue(diagram.getRelations().get(1) == relation3);

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 3);
        assertTrue(diagram.getRelations().get(0) == relation);
        assertTrue(diagram.getRelations().get(1) == relation2);
        assertTrue(diagram.getRelations().get(2) == relation3);
    }

    @Test
    public void allTest() {
        SeqDiagram diagram = new SeqDiagram("name", null);

        UMLClass umlClass1 = new UMLClass("name1", null);
        UMLClass umlClass2 = new UMLClass("name2", null);

        diagram.addInstance(umlClass1, 0);
        diagram.addInstance(umlClass2, 0);

        SeqRelation relation = new SeqRelation("name", diagram, umlClass1, 0, umlClass2, 0);

        diagram.addRelation(relation);
        diagram.setName("newName");

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 1);
        assertTrue(diagram.getRelations().get(0) == relation);
        
        assertTrue(diagram.getInstances().size() == 2);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass1);
        assertTrue(diagram.getInstances().get(1).getKey() == umlClass2);

        diagram.undo();

        assertTrue(diagram.getRelations().size() == 0);
        
        assertTrue(diagram.getInstances().size() == 2);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass1);
        assertTrue(diagram.getInstances().get(1).getKey() == umlClass2);

        diagram.undo();

        assertTrue(diagram.getInstances().size() == 1);
        assertTrue(diagram.getInstances().get(0).getKey() == umlClass1);

        diagram.undo();

        assertTrue(diagram.getInstances().size() == 0);
    }
}
