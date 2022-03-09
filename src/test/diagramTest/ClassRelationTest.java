package test.diagramTest;

import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;
import backend.diagram.ClassRelation;
import backend.diagram.ClassRelation.ClassRelEnum;
import test.diagramObjectTest.helpers.TypeHelper;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ClassRelationTest {
    private ArrayList<UMLClass> classes;

    @BeforeEach
    public void setup() {
        TypeHelper.setup();
        classes = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            classes.add(new UMLClass("first class" + String.valueOf(i), null));
    }

    @AfterEach
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
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.ASSOCIATION);
        Assert.assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(0));
        Assert.assertTrue(relation.getSecond().getKey() == classes.get(1));
    }

    @Test
    public void setRelationTest() {
        ClassRelation relation = new ClassRelation("relation", null);
        relation.setFirst(classes.get(0), 0);
        relation.setSecond(classes.get(1), 0);
        Assert.assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(0));
        Assert.assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(1));
    }

    @Test
    public void changeTypeTest() {
        ClassRelation relation = new ClassRelation("relation", null);
        relation.setType(ClassRelation.ClassRelEnum.AGGREGATION);
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.AGGREGATION);
    }

    @Test
    public void changePeersTest() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setFirst(classes.get(2), 0);
        Assert.assertTrue((UMLClass) relation.getFirst().getKey() == classes.get(2));
        relation.setSecond(classes.get(3), 0);
        Assert.assertTrue((UMLClass) relation.getSecond().getKey() == classes.get(3));
    }

    @Test
    public void samePeerTest() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        relation.setSecond(classes.get(0), 0);
        relation.setType(ClassRelation.ClassRelEnum.COMPOSITION);
        Assert.assertTrue((UMLClass) relation.getFirst().getKey() == (UMLClass) relation.getSecond().getKey());
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.COMPOSITION);
    }

    @Test
    public void inheritInterfaceTest() {
        UMLInterface inter = new UMLInterface("name", null);
        UMLInterface newInter = new UMLInterface("another name", null);
        ClassRelation relation = new ClassRelation("relation", null, inter, 0, newInter, 0);
        Assert.assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);   // when there is interface as first it can only generalize
    }

    @Test
    public void inheritInterfaceTest2() {
        UMLInterface inter = new UMLInterface("name", null);
        ClassRelation relation = new ClassRelation("relation", null);
        relation.setFirst(inter, 0);
        Assert.assertTrue(relation.getType() == ClassRelEnum.GENERALIZATION);
        Assert.assertFalse(relation.setSecond(classes.get(0), 0));
    }

    @Test
    public void requireFirstTest() {
        ClassRelation relation = new ClassRelation("relation", null);
        Assert.assertFalse(relation.setSecond(classes.get(0), 0));
    }
    
    @Test
    public void implementInterfaceTest() {
        ClassRelation relation = new ClassRelation("relation", null);
        relation.setFirst(classes.get(0), 0);
        Assert.assertTrue(relation.getType() == ClassRelEnum.ASSOCIATION);
        UMLInterface inter = new UMLInterface("name", null);
        Assert.assertTrue(relation.setSecond(inter, 0));
        Assert.assertTrue(relation.getType() == ClassRelEnum.IMPLEMENTS);
    }

    @Test
    public void implementInterfaceTest2() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        UMLInterface inter = new UMLInterface("name", null);
        Assert.assertTrue(relation.setSecond(inter, 0));
        Assert.assertTrue(relation.getType() == ClassRelEnum.IMPLEMENTS);
    }

    @Test
    public void implementInterfaceTest3() {
        ClassRelation relation = new ClassRelation("relation", null, classes.get(0), 0, classes.get(1), 0);
        UMLInterface inter = new UMLInterface("name", null);
        Assert.assertFalse(relation.setFirst(inter, 0));    // no valid relation interface -> class
    }
}
