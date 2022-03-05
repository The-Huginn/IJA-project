package test.diagramTest;

import backend.diagramObject.UMLClass;
import backend.diagram.ClassRelation;

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
        classes = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            classes.add(new UMLClass("first class" + String.valueOf(i)));
    }

    @AfterEach
    public void tearDown() {
        classes.clear();
    }

    @Test
    public void getterTest() {
        ClassRelation relation = new ClassRelation("relation", new Pair<UMLClass, UMLClass>(classes.get(0), classes.get(1)));
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.ASSOCIATION);
        Assert.assertTrue(relation.getFirst() == classes.get(0));
        Assert.assertTrue(relation.getSecond() == classes.get(1));
    }

    @Test
    public void setRelationTest() {
        ClassRelation relation = new ClassRelation("relation");
        relation.setFirst(classes.get(0));
        relation.setSecond(classes.get(1));
        Assert.assertTrue(relation.getFirst() == classes.get(0));
        Assert.assertTrue(relation.getSecond() == classes.get(1));
    }

    @Test
    public void changeTypeTest() {
        ClassRelation relation = new ClassRelation("relation");
        relation.setType(ClassRelation.ClassRelEnum.AGGREGATION);
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.AGGREGATION);
    }

    @Test
    public void changePeersTest() {
        ClassRelation relation = new ClassRelation("relation", new Pair<UMLClass, UMLClass>(classes.get(0), classes.get(1)));
        relation.setFirst(classes.get(2));
        Assert.assertTrue(relation.getFirst() == classes.get(2));
        relation.setSecond(classes.get(3));
        Assert.assertTrue(relation.getSecond() == classes.get(3));
    }

    @Test
    public void samePeerTest() {
        ClassRelation relation = new ClassRelation("relation", new Pair<UMLClass, UMLClass>(classes.get(0), classes.get(1)));
        relation.setSecond(classes.get(0));
        relation.setType(ClassRelation.ClassRelEnum.COMPOSITION);
        Assert.assertTrue(relation.getFirst() == relation.getSecond());
        Assert.assertTrue(relation.getType() == ClassRelation.ClassRelEnum.COMPOSITION);
    }
}
