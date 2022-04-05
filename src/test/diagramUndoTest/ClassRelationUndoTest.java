package test.diagramUndoTest;

import org.junit.Assert;
import org.junit.Test;

import backend.diagram.ClassRelation;
import backend.diagram.ClassRelation.ClassRelEnum;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;

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

        Assert.assertTrue(relation.getFirst().getKey() == umlInterface);

        relation.undo();

        Assert.assertTrue(relation.getFirst().getKey() == umlClass);
    }

    @Test
    public void setSecondTest() {
        ClassRelation relation = new ClassRelation("name", null);

        relation.setFirst(new UMLClass("name", null), 0);

        UMLClass umlClass = new UMLClass("another name", null);
        UMLClass umlClass2 = new UMLClass("name", null);

        Assert.assertTrue(relation.setSecond(umlClass, 0));
        Assert.assertTrue(relation.setSecond(umlClass2, 0));
        Assert.assertTrue(relation.setSecond(new UMLClass("another another name", null), 0));

        relation.undo();

        Assert.assertTrue(relation.getSecond().getKey() == umlClass2);

        relation.undo();

        Assert.assertTrue(relation.getSecond().getKey() == umlClass);
    }

    @Test
    public void setSecondWithTypeTest() {
        ClassRelation relation = new ClassRelation("name", null);

        relation.setFirst(new UMLClass("name", null), 0);

        UMLClass umlClass = new UMLClass("another name", null);
        UMLInterface umlInterface = new UMLInterface("name", null);

        Assert.assertTrue(relation.setSecond(umlClass, 0, ClassRelEnum.AGGREGATION));
        Assert.assertTrue(relation.setSecond(umlInterface, 0, ClassRelEnum.IMPLEMENTS));
        Assert.assertTrue(relation.setSecond(new UMLClass("another another name", null), 0, ClassRelEnum.ASSOCIATION));

        relation.undo();

        Assert.assertTrue(relation.getSecond().getKey() == umlInterface);
        Assert.assertTrue(relation.getType() == ClassRelEnum.IMPLEMENTS);

        relation.undo();

        Assert.assertTrue(relation.getSecond().getKey() == umlClass);
        Assert.assertTrue(relation.getType() == ClassRelEnum.AGGREGATION);

        relation.undo();

        Assert.assertTrue(relation.getSecond().getKey() == null);
        Assert.assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
    }

    @Test
    public void setTypeTest() {
        ClassRelation relation = new ClassRelation("name", null);

        relation.setType(ClassRelEnum.GENERALIZATION);
        relation.setType(ClassRelEnum.AGGREGATION);

        relation.undo();

        Assert.assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);

        relation.undo();

        Assert.assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
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

        Assert.assertTrue(relation.getType() == ClassRelEnum.AGGREGATION);
        Assert.assertTrue(relation.getFirst().getKey() == firstClass);
        Assert.assertTrue(relation.getSecond().getKey() == thirdClass);

        relation.undo();

        Assert.assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);
        Assert.assertTrue(relation.getFirst().getKey() == firstClass);
        Assert.assertTrue(relation.getSecond().getKey() == secondClass);

        relation.undo();

        Assert.assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);
        Assert.assertTrue(relation.getFirst().getKey() == firstClass);
        Assert.assertTrue(relation.getSecond().getKey() == null);

        relation.undo();

        Assert.assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
        Assert.assertTrue(relation.getFirst().getKey() == firstClass);
        Assert.assertTrue(relation.getSecond().getKey() == null);

        relation.undo();

        Assert.assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
        Assert.assertTrue(relation.getFirst().getKey() == null);
        Assert.assertTrue(relation.getSecond().getKey() == null);
    }
}
