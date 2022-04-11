/**
 * @file AdvancedTest.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains advanced tests for overall functionality
 */
package test.diagramTest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import backend.diagram.ClassDiagram;
import backend.diagram.ClassRelation;
import backend.diagram.SeqDiagram;
import backend.diagram.SeqRelation;
import backend.diagram.ClassRelation.ClassRelEnum;
import backend.diagramObject.Attribute;
import backend.diagramObject.Method;
import backend.diagramObject.Type;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLInterface;
import test.diagramObjectTest.helpers.TypeHelper;

public class AdvancedTest {
    private ClassDiagram mainDiagram;
    private ArrayList<UMLClass> classes;
    private ArrayList<UMLInterface> interfaces;

    @Before
    public void setup() {
        TypeHelper.setup();

        mainDiagram = new ClassDiagram("name");
        classes = new ArrayList<>();
        interfaces = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            // Please do not change names!
            classes.add(new UMLClass("class " + String.valueOf(i), mainDiagram));
            // Please do not change names!
            interfaces.add(new UMLInterface("interface " + String.valueOf(i), mainDiagram));
        }

        for (UMLClass umlClass : classes)
            mainDiagram.addClass(umlClass);

        for (UMLInterface umlInterface : interfaces)
            mainDiagram.addInterface(umlInterface);
    }

    @After
    public void tearDown() {
        TypeHelper.tearDown();

        mainDiagram = null;
        classes.clear();
        interfaces.clear();
    }

    @Test
    public void addSeqDiagramsTest() {
        SeqDiagram diagram = new SeqDiagram("name", mainDiagram);
        SeqDiagram diagram2 = new SeqDiagram("another name", mainDiagram);
        Assert.assertTrue(mainDiagram.addDiagram(diagram));
        Assert.assertTrue(mainDiagram.addDiagram(diagram2));
    }

    @Test
    public void addSameSeqDiagramTest() {
        SeqDiagram diagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(diagram);
        SeqDiagram diagram2 = new SeqDiagram("name", mainDiagram);
        Assert.assertFalse(mainDiagram.addDiagram(diagram2));
    }

    @Test
    public void renameSeqDiagramTest() {
        mainDiagram.addDiagram(new SeqDiagram("name", mainDiagram));
        mainDiagram.addDiagram(new SeqDiagram("another name", mainDiagram));
        Assert.assertTrue(mainDiagram.getDiagrams().size() == 2);
        Assert.assertTrue(mainDiagram.getDiagrams().get(1).getName().equals("another name"));
        Assert.assertFalse(mainDiagram.getDiagrams().get(1).setName("name"));
    }

    @Test
    public void checkCorrectTest() {
        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params));

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        seqDiagram.addInstance(umlClass, 0);
        seqDiagram.addInstance(mainDiagram.getClasses().get(1), 0);

        SeqRelation relation = new SeqRelation("first relation", seqDiagram);
        relation.setFirst(umlClass, 0);
        relation.setSecond(mainDiagram.getClasses().get(1), 0);
        relation.setMethod("method(10)");

        seqDiagram.addRelation(relation);

        Assert.assertTrue(mainDiagram.checkCorrect());
    }

    @Test
    public void checkCorrectTest2() {
        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params));

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(umlClass, 0);
        seqDiagram.addInstance(mainDiagram.getClasses().get(1), 0);

        SeqRelation relation = new SeqRelation("first relation", seqDiagram);
        relation.setFirst(umlClass, 0);
        relation.setSecond(mainDiagram.getClasses().get(1), 0);
        relation.setMethod("method(10)");

        seqDiagram.addRelation(relation);

        umlClass.removeMethod(0);   // we remove the called 'method(10)'

        Assert.assertFalse(mainDiagram.checkCorrect());
    }

    @Test
    public void checkInheritanceTest() {
        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params));

        UMLClass secondUmlClass = mainDiagram.getClasses().get(1);
        ClassRelation classRelation = new ClassRelation("inheritance", mainDiagram, secondUmlClass, 0, umlClass, 0);
        Assert.assertTrue(classRelation.setType(ClassRelEnum.GENERALIZATION));  // secondUmlClass inherits 'method(int)'
        mainDiagram.addRelation(classRelation);

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(umlClass, 0);
        seqDiagram.addInstance(secondUmlClass, 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, secondUmlClass, 0, umlClass, 0);
        seqDiagram.addRelation(seqRelation);
        Assert.assertTrue(seqRelation.setMethod("method(10)")); // inherited method

        Assert.assertTrue(mainDiagram.checkCorrect());
    }

    @Test
    public void checkInheritanceTest2() {
        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params));

        UMLClass secondUmlClass = mainDiagram.getClasses().get(1);
        ClassRelation classRelation = new ClassRelation("inheritance", mainDiagram, secondUmlClass, 0, umlClass, 0);
        Assert.assertTrue(classRelation.setType(ClassRelEnum.GENERALIZATION));  // secondUmlClass inherits 'method(int)'
        mainDiagram.addRelation(classRelation);

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(umlClass, 0);
        seqDiagram.addInstance(secondUmlClass, 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, secondUmlClass, 0, umlClass, 0);
        seqDiagram.addRelation(seqRelation);
        
        Assert.assertTrue(seqRelation.setMethod("method(10)")); // inherited method

        umlClass.removeMethod(0);   // no more inherited method

        Assert.assertFalse(mainDiagram.checkCorrect());
    }
    
    @Test
    public void checkInheritanceTest3() {
        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params));

        UMLClass secondUmlClass = mainDiagram.getClasses().get(1);
        ClassRelation classRelation = new ClassRelation("inheritance", mainDiagram, secondUmlClass, 0, umlClass, 0);
        Assert.assertTrue(classRelation.setType(ClassRelEnum.GENERALIZATION));  // secondUmlClass inherits 'method(int)'
        mainDiagram.addRelation(classRelation);

        UMLClass thirdUmlClass = mainDiagram.getClasses().get(2);
        ClassRelation classRelation2 = new ClassRelation("inheritance", mainDiagram, thirdUmlClass, 0, secondUmlClass, 0);
        Assert.assertTrue(classRelation2.setType(ClassRelEnum.GENERALIZATION));  // thirdUmlClass inherits 'method(int)' from umlClass
        mainDiagram.addRelation(classRelation2);

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(thirdUmlClass, 0);
        seqDiagram.addInstance(classes.get(3), 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, thirdUmlClass, 0, classes.get(3), 0);
        seqDiagram.addRelation(seqRelation);
        
        Assert.assertTrue(seqRelation.setMethod("method(10)")); // inherited method

        Assert.assertTrue(mainDiagram.checkCorrect());

        umlClass.removeMethod(0);   // no more inherited method

        Assert.assertFalse(mainDiagram.checkCorrect());
    }

    @Test
    public void checkInheritanceTest4() {
        String[] params = {"int"};
        UMLInterface umlInterface = mainDiagram.getInterfaces().get(0);
        Assert.assertTrue(umlInterface.addMethod(new Method("method", umlInterface, Type.getType("int"), Attribute.Visibility.PUBLIC, false, params)));

        UMLClass umlClass = mainDiagram.getClasses().get(0);
        ClassRelation classRelation = new ClassRelation("implements", mainDiagram, umlClass, 0, umlInterface, 0);
        Assert.assertTrue(classRelation.getType() == ClassRelEnum.IMPLEMENTS);  // umlClass implements 'method(int)'
        mainDiagram.addRelation(classRelation);

        UMLClass secondUmlClass = mainDiagram.getClasses().get(1);
        ClassRelation classRelation2 = new ClassRelation("inheritance", mainDiagram, secondUmlClass, 0, umlClass, 0);
        Assert.assertTrue(classRelation2.setType(ClassRelEnum.GENERALIZATION));  // secondUmlClass inherits 'method(int)' from umlInterface
        mainDiagram.addRelation(classRelation2);

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(secondUmlClass, 0);
        seqDiagram.addInstance(classes.get(2), 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, secondUmlClass, 0, classes.get(2), 0);
        seqDiagram.addRelation(seqRelation);

        Assert.assertTrue(seqRelation.setMethod("method(10)")); // inherited method

        Assert.assertTrue(mainDiagram.checkCorrect());

        umlInterface.removeMethod(0);   // no more inherited method

        Assert.assertFalse(mainDiagram.checkCorrect());
    }

    @Test
    public void checkInheritanceTest5() {
        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PRIVATE, params));  // We are not able to inherit this method

        UMLClass secondUmlClass = mainDiagram.getClasses().get(1);
        ClassRelation classRelation = new ClassRelation("inheritance", mainDiagram, secondUmlClass, 0, umlClass, 0);
        Assert.assertTrue(classRelation.setType(ClassRelEnum.GENERALIZATION));
        mainDiagram.addRelation(classRelation);

        UMLClass thirdUmlClass = mainDiagram.getClasses().get(2);
        ClassRelation classRelation2 = new ClassRelation("inheritance", mainDiagram, thirdUmlClass, 0, secondUmlClass, 0);
        Assert.assertTrue(classRelation2.setType(ClassRelEnum.GENERALIZATION));
        mainDiagram.addRelation(classRelation2);

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(thirdUmlClass, 0);
        seqDiagram.addInstance(classes.get(3), 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, thirdUmlClass, 0, classes.get(3), 0);
        seqDiagram.addRelation(seqRelation);

        Assert.assertFalse(seqRelation.setMethod("method(10)"));
        Assert.assertFalse(mainDiagram.checkCorrect());
    }

    @Test
    public void missingInstanceTest() {
        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        UMLClass umlClass = new UMLClass("name", new ClassDiagram("new name"));
        Assert.assertFalse(seqDiagram.addInstance(umlClass, 0));
    }

    @Test
    public void missingInstanceTest2() {
        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(classes.get(0), 0);
        seqDiagram.addInstance(classes.get(1), 1);

        SeqRelation relation = new SeqRelation("relation", seqDiagram);
        Assert.assertFalse(relation.setFirst(classes.get(0), 1)); // We dont have this instanceNumber
    }

    @Test
    public void removeClassAndRelationsTest() {
        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params));

        mainDiagram.addRelation(new ClassRelation("first", mainDiagram, classes.get(0), 0, classes.get(1), 0, ClassRelEnum.GENERALIZATION));
        mainDiagram.addRelation(new ClassRelation("second", mainDiagram, classes.get(1), 0, classes.get(2), 0, ClassRelEnum.GENERALIZATION));
        mainDiagram.addRelation(new ClassRelation("third", mainDiagram, classes.get(2), 0, classes.get(3), 0, ClassRelEnum.GENERALIZATION));

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);

        seqDiagram.addInstance(classes.get(2), 0);
        seqDiagram.addInstance(classes.get(2), 1);

        SeqRelation seqRelation = new SeqRelation("name", seqDiagram, classes.get(2), 0, classes.get(2), 1);
        seqRelation.setMethod("method(10)");

        Assert.assertTrue(seqDiagram.addRelation(seqRelation));
        mainDiagram.removeRelation(0);

        Assert.assertFalse(seqDiagram.checkCorrect());
    }
}
