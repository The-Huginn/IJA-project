package test.diagramTest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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

public class AdvancedTest {
    private ClassDiagram mainDiagram;
    private ArrayList<UMLClass> classes;
    private ArrayList<UMLInterface> interfaces;

    @BeforeEach
    public void setup() {
        mainDiagram = new ClassDiagram("name");
        classes = new ArrayList<>();

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

    @AfterEach
    public void tearDown() {
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
        Assert.assertTrue(mainDiagram.getDiagrams().size() == 0);
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
        seqDiagram.addInstance(umlClass, 0);
        seqDiagram.addInstance(secondUmlClass, 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, secondUmlClass, 0, umlClass, 0);
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
        seqDiagram.addInstance(umlClass, 0);
        seqDiagram.addInstance(secondUmlClass, 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, secondUmlClass, 0, umlClass, 0);
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

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        seqDiagram.addInstance(thirdUmlClass, 0);
        seqDiagram.addInstance(classes.get(3), 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, thirdUmlClass, 0, classes.get(3), 0);
        Assert.assertTrue(seqRelation.setMethod("method(10)")); // inherited method

        Assert.assertTrue(mainDiagram.checkCorrect());

        umlClass.removeMethod(0);   // no more inherited method

        Assert.assertFalse(mainDiagram.checkCorrect());
    }

    @Test
    public void checkInheritanceTest4() {
        String[] params = {"int"};
        UMLInterface umlInterface = mainDiagram.getInterfaces().get(0);
        umlInterface.addMethod(new Method("method", umlInterface, Type.getType("int"), Attribute.Visibility.PUBLIC, params));

        UMLClass umlClass = mainDiagram.getClasses().get(0);
        ClassRelation classRelation = new ClassRelation("implements", mainDiagram, umlClass, 0, umlInterface, 0);
        Assert.assertTrue(classRelation.getType() == ClassRelEnum.IMPLEMENTS);  // umlClass implements 'method(int)'
        mainDiagram.addRelation(classRelation);

        UMLClass secondUmlClass = mainDiagram.getClasses().get(1);
        ClassRelation classRelation2 = new ClassRelation("inheritance", mainDiagram, secondUmlClass, 0, umlClass, 0);
        Assert.assertTrue(classRelation2.setType(ClassRelEnum.GENERALIZATION));  // secondUmlClass inherits 'method(int)' from umlInterface

        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        seqDiagram.addInstance(secondUmlClass, 0);
        seqDiagram.addInstance(classes.get(2), 0);

        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, secondUmlClass, 0, classes.get(2), 0);
        Assert.assertTrue(seqRelation.setMethod("method(10)")); // inherited method

        Assert.assertTrue(mainDiagram.checkCorrect());

        umlInterface.removeMethod(0);   // no more inherited method

        Assert.assertFalse(mainDiagram.checkCorrect());
    }
}
