/**
 * @file ClassDiagramUndoTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for undo of ClassDiagram class
 */
package test.diagramUndoTest;

import org.junit.Assert;
import org.junit.Test;

import backend.diagram.ClassDiagram;
import backend.diagram.ClassRelation;
import backend.diagram.SeqDiagram;
import backend.diagram.ClassRelation.ClassRelEnum;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;

public class ClassDiagramUndoTest {
    
    @Test
    public void setNameTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        diagram.setName("newName");
        diagram.setName("another newName");

        diagram.undo();

        Assert.assertTrue(diagram.getName().equals("newName"));

        diagram.undo();
        
        Assert.assertTrue(diagram.getName().equals("name"));
    }

    @Test
    public void addClassTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        UMLClass class1 = new UMLClass("name", diagram);
        UMLClass class2 = new UMLClass("another name", diagram);

        diagram.addClass(class1);
        diagram.addClass(class2);
        diagram.addClass(new UMLClass("another another name", diagram));

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 2);
        Assert.assertTrue(diagram.getClasses().get(0) == class1);
        Assert.assertTrue(diagram.getClasses().get(1) == class2);

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getClasses().get(0) == class1);

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 0);
    }

    @Test
    public void removeClassTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        UMLClass class1 = new UMLClass("name", diagram);
        UMLClass class2 = new UMLClass("another name", diagram);
        UMLClass class3 = new UMLClass("another another name", diagram);

        diagram.addClass(class1);
        diagram.addClass(class2);
        diagram.addClass(class3);

        diagram.removeClass(0);
        diagram.removeClass(1);
        diagram.removeClass(0);

        diagram.setName("name");

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 0);
        
        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getClasses().get(0) == class2);

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 2);
        Assert.assertTrue(diagram.getClasses().get(0) == class2);
        Assert.assertTrue(diagram.getClasses().get(1) == class3);

        diagram.undo();
        Assert.assertTrue(diagram.getClasses().size() == 3);
        Assert.assertTrue(diagram.getClasses().get(0) == class1);
        Assert.assertTrue(diagram.getClasses().get(1) == class2);
        Assert.assertTrue(diagram.getClasses().get(2) == class3);
    }

    @Test
    public void addInterfaceTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        UMLInterface umlInterface1 = new UMLInterface("name", diagram);
        UMLInterface umlInterface2 = new UMLInterface("another name", diagram);

        diagram.addInterface(umlInterface1);
        diagram.addInterface(umlInterface2);
        diagram.addClass(new UMLClass("another another name", diagram));

        diagram.undo();

        Assert.assertTrue(diagram.getInterfaces().size() == 2);
        Assert.assertTrue(diagram.getInterfaces().get(0) == umlInterface1);
        Assert.assertTrue(diagram.getInterfaces().get(1) == umlInterface2);

        diagram.undo();

        Assert.assertTrue(diagram.getInterfaces().size() == 1);
        Assert.assertTrue(diagram.getInterfaces().get(0) == umlInterface1);

        diagram.undo();

        Assert.assertTrue(diagram.getInterfaces().size() == 0);
    }

    @Test
    public void removeInterfaceTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        UMLInterface umlInterface1 = new UMLInterface("name", diagram);
        UMLInterface umlInterface2 = new UMLInterface("another name", diagram);
        UMLInterface umlInterface3 = new UMLInterface("another another name", diagram);

        diagram.addInterface(umlInterface1);
        diagram.addInterface(umlInterface2);
        diagram.addInterface(umlInterface3);

        diagram.removeInterface(0);
        diagram.removeInterface(1);
        diagram.removeInterface(0);

        diagram.setName("name");

        diagram.undo();

        Assert.assertTrue(diagram.getInterfaces().size() == 0);
        
        diagram.undo();

        Assert.assertTrue(diagram.getInterfaces().size() == 1);
        Assert.assertTrue(diagram.getInterfaces().get(0) == umlInterface2);

        diagram.undo();

        Assert.assertTrue(diagram.getInterfaces().size() == 2);
        Assert.assertTrue(diagram.getInterfaces().get(0) == umlInterface2);
        Assert.assertTrue(diagram.getInterfaces().get(1) == umlInterface3);

        diagram.undo();
        Assert.assertTrue(diagram.getInterfaces().size() == 3);
        Assert.assertTrue(diagram.getInterfaces().get(0) == umlInterface1);
        Assert.assertTrue(diagram.getInterfaces().get(1) == umlInterface2);
        Assert.assertTrue(diagram.getInterfaces().get(2) == umlInterface3);
    }

    @Test
    public void addDiagramTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        SeqDiagram seqDiagram1 = new SeqDiagram("name", diagram);
        SeqDiagram seqDiagram2 = new SeqDiagram("another name", diagram);

        diagram.addDiagram(seqDiagram1);
        diagram.addDiagram(seqDiagram2);
        diagram.addClass(new UMLClass("another another name", diagram));

        diagram.undo();

        Assert.assertTrue(diagram.getDiagrams().size() == 2);
        Assert.assertTrue(diagram.getDiagrams().get(0) == seqDiagram1);
        Assert.assertTrue(diagram.getDiagrams().get(1) == seqDiagram2);

        diagram.undo();

        Assert.assertTrue(diagram.getDiagrams().size() == 1);
        Assert.assertTrue(diagram.getDiagrams().get(0) == seqDiagram1);

        diagram.undo();

        Assert.assertTrue(diagram.getDiagrams().size() == 0);
    }

    @Test
    public void removeDiagramTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        SeqDiagram seqDiagram1 = new SeqDiagram("name", diagram);
        SeqDiagram seqDiagram2 = new SeqDiagram("another name", diagram);
        SeqDiagram seqDiagram3 = new SeqDiagram("another another name", diagram);

        diagram.addDiagram(seqDiagram1);
        diagram.addDiagram(seqDiagram2);
        diagram.addDiagram(seqDiagram3);

        diagram.removeDiagram(0);
        diagram.removeDiagram(1);
        diagram.removeDiagram(0);

        diagram.setName("name");

        diagram.undo();

        Assert.assertTrue(diagram.getDiagrams().size() == 0);
        
        diagram.undo();

        Assert.assertTrue(diagram.getDiagrams().size() == 1);
        Assert.assertTrue(diagram.getDiagrams().get(0) == seqDiagram2);

        diagram.undo();

        Assert.assertTrue(diagram.getDiagrams().size() == 2);
        Assert.assertTrue(diagram.getDiagrams().get(0) == seqDiagram2);
        Assert.assertTrue(diagram.getDiagrams().get(1) == seqDiagram3);

        diagram.undo();
        Assert.assertTrue(diagram.getDiagrams().size() == 3);
        Assert.assertTrue(diagram.getDiagrams().get(0) == seqDiagram1);
        Assert.assertTrue(diagram.getDiagrams().get(1) == seqDiagram2);
        Assert.assertTrue(diagram.getDiagrams().get(2) == seqDiagram3);
    }

    @Test
    public void addRelationTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        UMLClass class1 = new UMLClass("name", diagram);
        UMLClass class2 = new UMLClass("another name", diagram);

        diagram.addClass(class1);
        diagram.addClass(class2);

        ClassRelation relation = new ClassRelation("name", diagram, class1, 0, class2, 0);
        ClassRelation relation2 = new ClassRelation("another name", diagram, class2, 0, class1, 0, ClassRelEnum.AGGREGATION);

        diagram.addRelation(relation);
        diagram.addRelation(relation2);

        diagram.setName("newName");

        diagram.undo();

        Assert.assertTrue(diagram.getRelations().size() == 2);
        Assert.assertTrue(diagram.getRelations().get(0) == relation);
        Assert.assertTrue(diagram.getRelations().get(1) == relation2);

        diagram.undo();

        Assert.assertTrue(diagram.getRelations().size() == 1);
        Assert.assertTrue(diagram.getRelations().get(0) == relation);

        diagram.undo();

        Assert.assertTrue(diagram.getRelations().size() == 0);
    }

    @Test
    public void removeRelationTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        UMLClass class1 = new UMLClass("name", diagram);
        UMLClass class2 = new UMLClass("another name", diagram);

        diagram.addClass(class1);
        diagram.addClass(class2);

        ClassRelation relation = new ClassRelation("name", diagram, class1, 0, class2, 0);
        ClassRelation relation2 = new ClassRelation("another name", diagram, class2, 0, class1, 0, ClassRelEnum.AGGREGATION);
        ClassRelation relation3 = new ClassRelation("another another name", diagram, class1, 0, class2, 0, ClassRelEnum.COMPOSITION);

        diagram.addRelation(relation);
        diagram.addRelation(relation2);
        diagram.addRelation(relation3);

        diagram.removeRelation(0);
        diagram.removeRelation(1);
        diagram.removeRelation(0);

        diagram.setName("newName");

        diagram.undo();

        Assert.assertTrue(diagram.getRelations().size() == 0);

        diagram.undo();

        Assert.assertTrue(diagram.getRelations().size() == 1);
        Assert.assertTrue(diagram.getRelations().get(0) == relation2);

        diagram.undo();

        Assert.assertTrue(diagram.getRelations().size() == 2);
        Assert.assertTrue(diagram.getRelations().get(0) == relation2);
        Assert.assertTrue(diagram.getRelations().get(1) == relation3);

        diagram.undo();

        Assert.assertTrue(diagram.getRelations().size() == 3);
        Assert.assertTrue(diagram.getRelations().get(0) == relation);
        Assert.assertTrue(diagram.getRelations().get(1) == relation2);
        Assert.assertTrue(diagram.getRelations().get(2) == relation3);
    }

    @Test
    public void allTest() {
        ClassDiagram diagram = new ClassDiagram("name");

        UMLClass class1 = new UMLClass("name", diagram);
        UMLInterface interface1 = new UMLInterface("name", diagram);
        ClassRelation relation = new ClassRelation("name", diagram, class1, 0, interface1, 0, ClassRelEnum.IMPLEMENTS);
        SeqDiagram seqDiagram = new SeqDiagram("name", diagram);

        diagram.addClass(class1);
        diagram.addInterface(interface1);
        diagram.addRelation(relation);
        diagram.addDiagram(seqDiagram);

        diagram.setName("newName");

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getInterfaces().size() == 1);
        Assert.assertTrue(diagram.getRelations().size() == 1);
        Assert.assertTrue(diagram.getDiagrams().size() == 1);

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getInterfaces().size() == 1);
        Assert.assertTrue(diagram.getRelations().size() == 1);
        Assert.assertTrue(diagram.getDiagrams().size() == 0);

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getInterfaces().size() == 1);
        Assert.assertTrue(diagram.getRelations().size() == 0);
        Assert.assertTrue(diagram.getDiagrams().size() == 0);

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getInterfaces().size() == 0);
        Assert.assertTrue(diagram.getRelations().size() == 0);
        Assert.assertTrue(diagram.getDiagrams().size() == 0);

        diagram.undo();

        Assert.assertTrue(diagram.getClasses().size() == 0);
        Assert.assertTrue(diagram.getInterfaces().size() == 0);
        Assert.assertTrue(diagram.getRelations().size() == 0);
        Assert.assertTrue(diagram.getDiagrams().size() == 0);
    }
}
