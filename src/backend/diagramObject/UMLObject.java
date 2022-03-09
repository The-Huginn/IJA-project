package backend.diagramObject;

import backend.diagramObject.Element;
import backend.diagramObject.Attribute;
import backend.diagramObject.Method;

import java.util.ArrayList;

import backend.diagram.ClassDiagram;
import backend.diagram.Diagram;

public abstract class UMLObject extends Element{
    private final ClassDiagram parent;
    private ArrayList<Attribute> variables;
    private ArrayList<Method> methods;

    /**
     * @param name
     * @param parent Under which parent this UMLObject lives
     */
    public UMLObject(String name, ClassDiagram parent) {
        
    }
    
    /**
     * @param attribute
     * @return Success of the operation
     */
    abstract boolean addVariable(Attribute variable);

    /**
     * @param method
     * @return Success of the operation
     */
    abstract boolean addMethod(Method method);

    /**
     * @return True if everything is ok
     */
    abstract boolean checkCorrect();

    /**
     * @return Array with variables
     */
    public ArrayList<Attribute> getVariables() {
        return null;
    }

    /**
     * @return Array with methods
     */
    public ArrayList<Method> getMethods() {
        return null;
    }

    /**
     * @brief Upon invalid index nothing happens
     * @param index
     */
    public void removeVariable(int index) {

    }

    /**
     * @brief Upon invalid index nothing happens
     * @param index
     */
    public void removeMethod(int index) {

    }

    /**
     * @return
     */
    protected final Diagram getParent() {
        return null;
    }
}
