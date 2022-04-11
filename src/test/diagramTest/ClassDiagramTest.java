/**
 * @file ClassDiagramTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains tests for ClassDiagram
 */
package test.diagramTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import backend.diagram.ClassDiagram;
import backend.diagram.ClassRelation;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;

public class ClassDiagramTest {
    private ClassDiagram diagram;
    private List<UMLClass> classes;
    private List<UMLInterface> interfaces;

    @Before
    public void setup() {
        diagram = new ClassDiagram("name");
        classes = new ArrayList<>();
        interfaces = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            // Please do not change names!
            classes.add(new UMLClass("class " + String.valueOf(i), diagram));
            // Please do not change names!
            interfaces.add(new UMLInterface("interface " + String.valueOf(i), diagram));
        }
    }

    @After
    public void tearDown() {
        diagram = null;
        classes.clear();
        interfaces.clear();
    }

    @Test
    public void addRelationTest() {
        ClassRelation relation = new ClassRelation("name", diagram);
        Assert.assertTrue(diagram.addRelation(relation));
    }

    @Test
    public void removeRelationTest() {
        ClassRelation relation = new ClassRelation("name", diagram);
        ClassRelation relation2 = new ClassRelation("another name", diagram);
        ClassRelation relation3 = new ClassRelation("another another name", diagram);
        diagram.addRelation(relation);
        Assert.assertTrue(diagram.addRelation(relation2));
        Assert.assertTrue(diagram.addRelation(relation3));
        Assert.assertTrue(diagram.getRelations().size() == 3);
        Assert.assertTrue(diagram.getRelations().get(0) == relation);
        Assert.assertTrue(diagram.getRelations().get(1) == relation2);
        Assert.assertTrue(diagram.getRelations().get(2) == relation3);
    }

    @Test
    public void addObjectTest() {
        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addClass(umlClass));

        for (UMLInterface umlInterface : interfaces)
            Assert.assertTrue(diagram.addInterface(umlInterface));
    }

    // This might not be correct way as after constructor of UMLObject
    // we should also add it to diagram.
    // In that case this problem does not happen.
    @Test
    public void addObjectTest2() {
        classes.get(0).setName("class 1");
        diagram.addClass(classes.get(0));
        Assert.assertFalse(diagram.addClass(classes.get(1)));
    }

    @Test
    public void addObjectTest3() {
        interfaces.get(0).setName("class 1");
        interfaces.get(1).setName("class 2");

        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addClass(umlClass));

        for (UMLInterface umlInterface : interfaces)
            Assert.assertTrue(diagram.addInterface(umlInterface));
    }

    @Test
    public void addObjectTest4() {
        classes.get(0).setName("interface 1");
        classes.get(1).setName("interface 2");

        for (UMLInterface umlInterface : interfaces)
            Assert.assertTrue(diagram.addInterface(umlInterface));

        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addClass(umlClass));
    }

    @Test
    public void getObjects() {
        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addClass(umlClass));
        for (UMLInterface umlInterface : interfaces)
            Assert.assertTrue(diagram.addInterface(umlInterface));

        List<UMLClass> returnClasses = diagram.getClasses();
        List<UMLInterface> returnInterfaces = diagram.getInterfaces();

        Assert.assertTrue(returnClasses.size() == classes.size() && returnInterfaces.size() == interfaces.size());      

        for (int i = 0; i < classes.size(); i++)
            Assert.assertTrue(classes.get(i) == returnClasses.get(i));

        for (int i = 0; i < interfaces.size(); i++)
            Assert.assertTrue(interfaces.get(i) == returnInterfaces.get(i));
    }

    @Test
    public void removeObjects() {
        for (UMLClass umlClass : classes)
            Assert.assertTrue(diagram.addClass(umlClass));
        for (UMLInterface umlInterface : interfaces)
            Assert.assertTrue(diagram.addInterface(umlInterface));

        diagram.removeClass(2);
        classes.remove(2);
        diagram.removeInterface(1);
        interfaces.remove(1);

        List<UMLClass> returnClasses = diagram.getClasses();
        List<UMLInterface> returnInterfaces = diagram.getInterfaces();

        Assert.assertTrue(returnClasses.size() == classes.size() && returnInterfaces.size() == interfaces.size());      

        for (int i = 0; i < classes.size(); i++)
            Assert.assertTrue(classes.get(i) == returnClasses.get(i));

        for (int i = 0; i < interfaces.size(); i++)
            Assert.assertTrue(interfaces.get(i) == returnInterfaces.get(i));
    }

    @Test
    public void removeObjectAndRelationTest() {
        diagram.addClass(classes.get(0));
        diagram.addClass(classes.get(1));
        ClassRelation rel = new ClassRelation("name", diagram, classes.get(0), 0, classes.get(1), 0);
        Assert.assertTrue(diagram.addRelation(rel));
        diagram.removeClass(0);
        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getClasses().get(0) == classes.get(1));
        Assert.assertTrue(diagram.getRelations().size() == 0);  // We have removed class with it's relationships
    }

    @Test
    public void removeObjectAndRelationTest2() {
        diagram.addClass(classes.get(0));
        diagram.addClass(classes.get(1));
        ClassRelation rel = new ClassRelation("name", diagram, classes.get(1), 0, classes.get(0), 0);
        Assert.assertTrue(diagram.addRelation(rel));
        diagram.removeClass(0);
        Assert.assertTrue(diagram.getClasses().size() == 1);
        Assert.assertTrue(diagram.getClasses().get(0) == classes.get(1));
        Assert.assertTrue(diagram.getRelations().size() == 0);  // We have removed class with it's relationships
    }

    @Test
    public void removeObjectAndRelationTest3() {
        diagram.addClass(classes.get(0));
        diagram.addClass(classes.get(1));
        diagram.addClass(classes.get(2));
        ClassRelation rel = new ClassRelation("name", diagram, classes.get(0), 0, classes.get(1), 0);
        ClassRelation rel2 = new ClassRelation("name", diagram, classes.get(1), 0, classes.get(2), 0);
        Assert.assertTrue(diagram.addRelation(rel));
        Assert.assertTrue(diagram.addRelation(rel2));
        diagram.removeClass(0);
        Assert.assertTrue(diagram.getClasses().size() == 2);
        Assert.assertTrue(diagram.getClasses().get(0) == classes.get(1));
        Assert.assertTrue(diagram.getClasses().get(1) == classes.get(2));
        Assert.assertTrue(diagram.getRelations().size() == 1);  // We have removed class with it's relationships
        Assert.assertTrue(diagram.getRelations().get(0) == rel2);
    }

    // tests for UMLClass and UMLInterface
    // tests for recursive checkCorrect
}
