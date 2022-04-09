package backend.app;

import java.util.ArrayList;
import java.util.List;

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
import backend.jsonHandler.Saver;
import test.diagramObjectTest.helpers.TypeHelper;

public class tmp_main {
    
    public static void main(String[] args) {
        TypeHelper.setup();
        Type.addType("random");

        ClassDiagram mainDiagram = new ClassDiagram("name");
        List<UMLClass>classes = new ArrayList<UMLClass>();
        List<UMLInterface> interfaces = new ArrayList<UMLInterface>();

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

        String[] params = {"int"};
        UMLClass umlClass = mainDiagram.getClasses().get(0);
        umlClass.addMethod(new Method("method", umlClass, Type.getType("int"), Attribute.Visibility.PUBLIC, params));
    
        UMLClass secondUmlClass = mainDiagram.getClasses().get(1);
        ClassRelation classRelation = new ClassRelation("inheritance", mainDiagram, secondUmlClass, 0, umlClass, 0);
        classRelation.setType(ClassRelEnum.GENERALIZATION);
        mainDiagram.addRelation(classRelation);
    
        UMLClass thirdUmlClass = mainDiagram.getClasses().get(2);
        ClassRelation classRelation2 = new ClassRelation("inheritance", mainDiagram, thirdUmlClass, 0, secondUmlClass, 0);
        classRelation2.setType(ClassRelEnum.GENERALIZATION);
        mainDiagram.addRelation(classRelation2);
    
        SeqDiagram seqDiagram = new SeqDiagram("name", mainDiagram);
        mainDiagram.addDiagram(seqDiagram);
    
        seqDiagram.addInstance(thirdUmlClass, 0);
        seqDiagram.addInstance(classes.get(3), 0);
    
        SeqRelation seqRelation = new SeqRelation("first relation", seqDiagram, thirdUmlClass, 0, classes.get(3), 0);
        seqDiagram.addRelation(seqRelation);
        seqRelation.setMethod("method(10)");

        // Saver.save(mainDiagram, "../path/diagramSave.json");
        ClassDiagram diagram = Saver.load("../path/diagramSave.json");

        if (diagram == null)
            System.out.println("Problem");
        else {
            System.out.println("No problem");
            System.out.println("Diagram is correct: " + diagram.checkCorrect());
        }
    }
}
