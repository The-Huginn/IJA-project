package backend.diagramObject;

import backend.diagram.ClassDiagram;
import backend.diagramObject.UMLObject;

public class UMLClass extends UMLObject {
    
    public UMLClass(String name, ClassDiagram parent) {

    }

    public boolean addVariable(Attribute variable) {
        return false;
    }

    public boolean addMethod(Method method) {
        return false;
    }

    public boolean checkCorrect() {
        return false;
    }
}
