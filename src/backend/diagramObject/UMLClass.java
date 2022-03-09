package backend.diagramObject;

import backend.diagram.ClassDiagram;
import backend.diagramObject.UMLObject;

public class UMLClass extends UMLObject {
    
    /**
     * @param name
     * @param parent Under which parent this UMLClass lives
     */
    public UMLClass(String name, ClassDiagram parent) {

    }

    /**
     * @note Checks, whether another UMLClass with the same name exists
     */
    @Override
    public boolean setName(String newName) {
        return false;
    }

    /**
     * @note Checks, whether added Attribute has unique name
     */
    @Override
    public boolean addVariable(Attribute variable) {
        return false;
    }

    /**
     * @note Checks, whether added Method has unique name
     */
    @Override
    public boolean addMethod(Method method) {
        return false;
    }

    /**
     * @note Checks, whether everything is ok
     */
    @Override
    public boolean checkCorrect() {
        return false;
    }
}
