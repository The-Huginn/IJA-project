package backend.diagramObject;

import backend.diagram.ClassDiagram;
import backend.diagramObject.UMLObject;

public class UMLClass extends UMLObject {
    
    public UMLClass(String name, ClassDiagram parent) {

    }

    @Override
    public boolean setName(String newName) {
        return false;
    }

    @Override
    public boolean addVariable(Attribute variable) {
        return false;
    }

    @Override
    public boolean addMethod(Method method) {
        return false;
    }

    @Override
    public boolean checkCorrect() {
        return false;
    }
}
