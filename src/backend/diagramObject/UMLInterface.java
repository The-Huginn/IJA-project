package backend.diagramObject;

import backend.diagram.ClassDiagram;
import backend.diagramObject.Attribute.Visibility;

public class UMLInterface extends UMLObject{
    
    /**
     * @param name
     * @param parent Under which parent this UMLInterface lives
     */
    public UMLInterface(String name, ClassDiagram parent) {
        super(name, parent);
    }

    @Override
    public boolean equals(Object anotherObject) {

        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof UMLInterface))
            return false;

        return ((UMLInterface)anotherObject).getName().equals(this.getName());
    }

    /**
     * @note Checks, whether another UMLInterface with the same name exists
     */
    @Override
    public boolean setName(String newName) {
        
        for (UMLInterface interface1 : this.getParent().getInterfaces())
            if (this.equals(interface1))
                return false;
        
        super.setName(newName);
        return true;
        
    }

    /**
     * @note Checks, whether added Attribute has unique name, Visibility is immutable and public
     */
    @Override
    public boolean addVariable(Attribute variable) {

        if (variable instanceof Method)
            return false;

        for (Attribute attribute : variable.getParent().getVariables())
            if (attribute.equals(variable))
                return false;

        if (variable.getVisibility() != Visibility.PUBLIC || variable.isVisibilityChangable())
            return false;

        this.variables.add(variable);
        return true;
    }

    /**
     * @note Checks, whether added Method has unique signature and name, Visibility is immutable and public
     */
    @Override
    public boolean addMethod(Method method) {

        for (Method method1 : method.getParent().getMethods())
            if (method.equals(method1))
                return false;
        
        if (method.getVisibility() != Visibility.PUBLIC || method.isVisibilityChangable())
            return false;
        
        this.methods.add(method);
        return true ;
    }

    /**
     * @note Checks, whether everything is ok
     */
    @Override
    public boolean checkCorrect() {
    
        for (Method method1 : this.getMethods()) {

            if (method1.isVisibilityChangable() || method1.getVisibility() != Visibility.PUBLIC)
                return false;

            for (Method method2 : this.getMethods())
                if (method1.equals(method2) && method1 != method2)
                    return false;
        }

        for (Attribute attribute1 : this.getVariables()){

            if (attribute1.isVisibilityChangable() || attribute1.getVisibility() != Visibility.PUBLIC || (attribute1 instanceof Method))
                return false;
            
            for (Attribute attribute2 : this.getVariables())
                if(attribute1.equals(attribute2) && attribute1 != attribute2)
                    return false;
        }

        return true;
    }
}
