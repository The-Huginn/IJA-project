package backend.diagramObject;

import backend.diagram.ClassDiagram;
import backend.diagramObject.UMLObject;

public class UMLInterface extends UMLObject{
    
    /**
     * @param name
     * @param parent Under which parent this UMLInterface lives
     */
    public UMLInterface(String name, ClassDiagram parent) {

    }

    /**
     * @note Checks, whether another UMLInterface with the same name exists
     */
    @Override
    public boolean setName(String newName) {
        return false;
    }

    /**
     * @note Checks, whether added Attribute has unique name, Visibility is immutable and public
     */
    @Override
    public boolean addVariable(Attribute variable) {
        return false;
    }

    /**
     * @note Checks, whether added Method has unique signature and name, Visibility is immutable and public
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
