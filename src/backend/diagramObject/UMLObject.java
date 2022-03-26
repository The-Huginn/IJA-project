package backend.diagramObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.diagram.ClassDiagram;

public abstract class UMLObject extends Element{
    private final ClassDiagram parent;
    protected List<Attribute> variables;
    protected List<Method> methods;

    public UMLObject() {super();this.parent = null;}

    /**
     * @param name
     * @param parent Under which parent this UMLObject lives
     */
    public UMLObject(String name, ClassDiagram parent) {
        super(name);
        this.parent = parent;
        this.variables = new ArrayList<Attribute>();
        this.methods = new ArrayList<Method>();
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
    public List<Attribute> getVariables() {
        return Collections.unmodifiableList(this.variables);
    }

    /**
     * @return Array with methods
     */
    public List<Method> getMethods() {
        return Collections.unmodifiableList(this.methods);
    }

    /**
     * @brief Upon invalid index nothing happens
     * @param index
     */
    public void removeVariable(int index) {

        if (index < 0 || index >= this.variables.size())
            return;

        variables.remove(index); 
    }

    /**
     * @brief Upon invalid index nothing happens
     * @param index
     */
    public void removeMethod(int index) {

        if (index < 0 || index >= this.methods.size())
            return;
            
        this.methods.remove(index);
    }

    /**
     * @return
     */
    protected final ClassDiagram getParent() {
        return this.parent;
    }
}
