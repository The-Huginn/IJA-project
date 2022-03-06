package test.diagramTest;

import backend.diagramObject.UMLClass;
import backend.diagram.ClassRelation;

import test.diagramObjectTest.helpers.TypeHelper;

import java.util.ArrayList;
import javafx.util.Pair;

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
            classes.add(new UMLClass("first class" + String.valueOf(i)));
    }

    @AfterEach
    public void tearDown() {
        TypeHelper.tearDown();
        classes.clear();
    }

    private Pair<UMLClass, Integer> createPair(UMLClass class1, int number) {
        return new Pair<UMLClass, Integer>(class1, number);
    }

    @Test
    public void getterTest() {
        ClassRelation relation = new ClassRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.ASSOCIATION);
        Assert.assertTrue(relation.getFirst().getKey() == classes.get(0));
        Assert.assertTrue(relation.getSecond().getKey() == classes.get(1));
    }

    @Test
    public void setRelationTest() {
        ClassRelation relation = new ClassRelation("relation");
        relation.setFirst(createPair(classes.get(0), 0));
        relation.setSecond(createPair(classes.get(1), 0));
        Assert.assertTrue(relation.getFirst().getKey() == classes.get(0));
        Assert.assertTrue(relation.getSecond().getKey() == classes.get(1));
    }

    @Test
    public void changeTypeTest() {
        ClassRelation relation = new ClassRelation("relation");
        relation.setType(ClassRelation.ClassRelEnum.AGGREGATION);
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.AGGREGATION);
    }

    @Test
    public void changePeersTest() {
        ClassRelation relation = new ClassRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        relation.setFirst(createPair(classes.get(2), 0));
        Assert.assertTrue(relation.getFirst().getKey() == classes.get(2));
        relation.setSecond(createPair(classes.get(3), 0));
        Assert.assertTrue(relation.getSecond().getKey() == classes.get(3));
    }

    @Test
    public void samePeerTest() {
        ClassRelation relation = new ClassRelation("relation", new Pair<Pair<UMLClass, Integer>, Pair<UMLClass, Integer>>(createPair(classes.get(0), 0), createPair(classes.get(1), 0)));
        relation.setSecond(createPair(classes.get(0), 0));
        relation.setType(ClassRelation.ClassRelEnum.COMPOSITION);
        Assert.assertTrue(relation.getFirst().getKey() == relation.getSecond());
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.COMPOSITION);
    }
}
