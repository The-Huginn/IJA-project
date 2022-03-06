package backend.diagramObject;

import backend.diagram.ClassDiagram;
import backend.diagramObject.UMLObject;

public class UMLInterface extends UMLObject{
    
    public UMLInterface(String name, ClassDiagram parent) {

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
