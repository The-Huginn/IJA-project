package test.diagramTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import backend.diagram.ClassDiagram;
import backend.diagram.SeqRelation;
import backend.diagram.SeqDiagram;
import backend.diagramObject.UMLClass;
import javafx.util.Pair;

public class SeqDiagramTest {
    private ClassDiagram classDiagram;
    private SeqDiagram diagram;
    private List<UMLClass> classes;

    @Before
    public void setup() {
        classDiagram = new ClassDiagram("name");
        diagram = new SeqDiagram("name", classDiagram);
        classes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            // Please do not change names!
            classes.add(new UMLClass("class " + String.valueOf(i), classDiagram));
            classDiagram.addClass(classes.get(i));
        }
    }

    @After
    public void tearDown() {
        diagram = null;
        classes.clear();
    }

    private void addInstances(int instance) {
        for (UMLClass umlClass : classes)
            diagram.addInstance(umlClass, instance);
    }
    
    @Test
    public void addRelationTest() {
        SeqRelation relation = new SeqRelation("name", diagram);
        Assert.assertTrue(diagram.addRelation(relation));
    }

    @Test
    public void removeRelationTest() {
        SeqRelation relation = new SeqRelation("name", diagram);
        SeqRelation relation2 = new SeqRelation("name", diagram);
        SeqRelation relation3 = new SeqRelation("another name", diagram);
        diagram.addRelation(relation);
        Assert.assertTrue(diagram.addRelation(relation2));
        Assert.assertTrue(diagram.addRelation(relation3));
        Assert.assertTrue(diagram.getRelations().size() == 3);
        Assert.assertTrue(diagram.getRelations().get(0) == relation);
        Assert.assertTrue(diagram.getRelations().get(1) == relation2);
        Assert.assertTrue(diagram.getRelations().get(2) == relation3);
    }

    @Test
    public void addInstanceTest() {
        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addInstance(umlClass, 0));

        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addInstance(umlClass, 1));
    }

    @Test
    public void addSameInstanceTest() {
        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addInstance(umlClass, 0));

        for (UMLClass umlClass : classes)
            Assert.assertFalse(diagram.addInstance(umlClass, 0));
    }

    @Test
    public void getInstancesTest() {
        addInstances(0);
        addInstances(1);

        List<Pair<UMLClass, Integer>> instances = diagram.getInstances();

        Assert.assertTrue(instances.size() == 2 * classes.size());

        for (int i = 0; i < classes.size(); i++) {
            Assert.assertTrue(instances.get(i).getKey() == classes.get(i) && instances.get(i).getValue() == 0);
            Assert.assertTrue(instances.get(i + classes.size()).getKey() == classes.get(i) && instances.get(i + classes.size()).getValue() == 1);
        }
    }

    @Test
    public void removeInstanceTest() {
        addInstances(0);

        diagram.removeInstance(2);
        classes.remove(2);

        List<Pair<UMLClass, Integer>> instances = diagram.getInstances();

        Assert.assertTrue(instances.size() == classes.size());

        for (int i = 0; i < classes.size(); i++)
            Assert.assertTrue(instances.get(i).getKey() == classes.get(i) && instances.get(i).getValue() == 0);
    }

    @Test
    public void removeInstanceTest2() {
        addInstances(0);

        diagram.removeInstance(classes.size() - 1);
        diagram.addInstance(classes.get(classes.size() - 1), 0);

        List<Pair<UMLClass, Integer>> instances = diagram.getInstances();

        Assert.assertTrue(instances.size() == classes.size());

        for (int i = 0; i < classes.size(); i++)
            Assert.assertTrue(instances.get(i).getKey() == classes.get(i) && instances.get(i).getValue() == 0);
    }

    @Test
    public void missingClassTest() {
        UMLClass randomClass = new UMLClass("random", null);
        Assert.assertFalse(diagram.addInstance(randomClass, 0));
        Assert.assertTrue(diagram.getInstances().size() == 0);
    }

    @Test
    public void checkCorrectTest() {
        addInstances(0);
        Assert.assertTrue(diagram.checkCorrect());
    }

    @Test
    public void checkCorrectTest2() {
        addInstances(0);
        classDiagram.removeClass(classes.size() - 1);
        Assert.assertFalse(diagram.checkCorrect());
    }

    @Test
    public void removeObjectAndRelationTest() {
        diagram.addInstance(classes.get(0), 0);
        diagram.addInstance(classes.get(1), 1);
        SeqRelation rel = new SeqRelation("name", diagram, classes.get(0), 0, classes.get(1), 1);
        Assert.assertTrue(diagram.addRelation(rel));
        diagram.removeInstance(0);
        Assert.assertTrue(diagram.getInstances().size() == 1);
        Assert.assertTrue(diagram.getInstances().get(0).getKey() == classes.get(1));
        Assert.assertTrue(diagram.getInstances().get(0).getValue() == 1);
        Assert.assertTrue(diagram.getRelations().size() == 0);  // We have removed class with it's relationships
    }

    @Test
    public void removeObjectAndRelationTest2() {
        diagram.addInstance(classes.get(0), 0);
        diagram.addInstance(classes.get(1), 1);
        SeqRelation rel = new SeqRelation("name", diagram, classes.get(1), 1, classes.get(0), 0);
        Assert.assertTrue(diagram.addRelation(rel));
        diagram.removeInstance(0);
        Assert.assertTrue(diagram.getInstances().size() == 1);
        Assert.assertTrue(diagram.getInstances().get(0).getKey() == classes.get(1));
        Assert.assertTrue(diagram.getInstances().get(0).getValue() == 1);
        Assert.assertTrue(diagram.getRelations().size() == 0);  // We have removed class with it's relationships
    }

    @Test
    public void removeObjectAndRelationTest3() {
        diagram.addInstance(classes.get(0), 0);
        diagram.addInstance(classes.get(1), 1);
        diagram.addInstance(classes.get(2), 2);
        SeqRelation rel = new SeqRelation("name", diagram, classes.get(0), 0, classes.get(1), 1);
        SeqRelation rel2 = new SeqRelation("name", diagram, classes.get(1), 1, classes.get(2), 2);
        Assert.assertTrue(diagram.addRelation(rel));
        Assert.assertTrue(diagram.addRelation(rel2));
        diagram.removeInstance(0);
        Assert.assertTrue(diagram.getInstances().size() == 2);
        Assert.assertTrue(diagram.getInstances().get(0).getKey() == classes.get(1));
        Assert.assertTrue(diagram.getInstances().get(0).getValue() == 1);
        Assert.assertTrue(diagram.getInstances().get(1).getKey() == classes.get(2));
        Assert.assertTrue(diagram.getInstances().get(1).getValue() == 2);
        Assert.assertTrue(diagram.getRelations().size() == 1);  // We have removed class with it's relationships
        Assert.assertTrue(diagram.getRelations().get(0) == rel2);
    }
}
