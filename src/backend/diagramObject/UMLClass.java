package backend.diagramObject;

import backend.diagram.ClassDiagram;

public class UMLClass extends UMLObject {
    
    /**
     * @param name
     * @param parent Under which parent this UMLClass lives
     */
    public UMLClass(String name, ClassDiagram parent) {
        super(name, parent);
    }

    @Override
    public boolean equals(Object anotherObject) {

        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof UMLClass))
            return false;

        return ((UMLClass)anotherObject).getName().equals(this.getName());
    }

    /**
     * @note Checks, whether another UMLClass with the same name exists
     */
    @Override
    public boolean setName(String newName) {

        for (UMLClass umlClass : this.getParent().getClasses())
            if (umlClass.getName().equals(newName))
                return false;
        
        super.setName(newName);
        return true;
    }

    /**
     * @note Checks, whether added Attribute has unique name
     */
    @Override
    public boolean addVariable(Attribute variable) {

        if (variable instanceof Method)
            return false;

        for (Attribute attribute : this.variables)
            if (attribute.equals(variable))
                return false;
        
        this.variables.add(variable);
        return true;
    }

    /**
     * @note Checks, whether added Method has unique name
     */
    @Override
    public boolean addMethod(Method method) {

        for (Method aux_method : this.methods)
            if (aux_method.equals(method))
                return false;

        this.methods.add(method);
        return true;
    }

    /**
     * @note Checks, whether everything is ok
     */
    @Override
    public boolean checkCorrect() {

        for (Attribute attribute : this.getVariables())
            for (Attribute attribute2 : this.getVariables())
                if (attribute.equals(attribute2) && attribute != attribute2)
                    return false;
        
        for (Method method : this.getMethods())
            for (Method method2 : this.getMethods())
                if (method.equals(method2) && method != method2)
                    return false;

        return true;
    }
}
