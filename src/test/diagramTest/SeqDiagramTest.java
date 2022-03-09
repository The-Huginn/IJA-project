package test.diagramTest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import backend.diagram.ClassDiagram;
import backend.diagram.SeqRelation;
import backend.diagram.SeqDiagram;
import backend.diagram.SeqRelation;
import backend.diagramObject.UMLClass;
import javafx.util.Pair;

public class SeqDiagramTest {
    private ClassDiagram classDiagram;
    private SeqDiagram diagram;
    private ArrayList<UMLClass> classes;

    @BeforeEach
    public void setup() {
        classDiagram = new ClassDiagram("name");
        diagram = new SeqDiagram("name", classDiagram);
        classes = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            // Please do not change names!
            classes.add(new UMLClass("class " + String.valueOf(i), classDiagram));
    }

    @AfterEach
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

        ArrayList<Pair<UMLClass, Integer>> instances = diagram.getInstances();

        Assert.assertTrue(instances.size() == 2 * classes.size());

        for (int i = 0; i < classes.size(); i++) {
            Assert.assertTrue(instances.get(i).getKey() == classes.get(i) && instances.get(i).getValue() == 0);
            Assert.assertTrue(instances.get(i + classes.size()).getKey() == classes.get(i) && instances.get(i + classes.size()).getValue() == 0);
        }
    }

    @Test
    public void removeInstanceTest() {
        addInstances(0);

        diagram.removeInstance(2);
        classes.remove(2);

        ArrayList<Pair<UMLClass, Integer>> instances = diagram.getInstances();

        Assert.assertTrue(instances.size() == classes.size());

        for (int i = 0; i < classes.size(); i++)
            Assert.assertTrue(instances.get(i).getKey() == classes.get(i) && instances.get(i).getValue() == 0);
    }

    @Test
    public void removeInstanceTest2() {
        addInstances(0);

        diagram.removeInstance(classes.size() - 1);
        diagram.addInstance(classes.get(classes.size() - 1), 0);

        ArrayList<Pair<UMLClass, Integer>> instances = diagram.getInstances();

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
}
