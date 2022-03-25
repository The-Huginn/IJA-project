package backend.diagramObject;

import java.util.ArrayList;

import backend.diagramObject.Attribute;
import backend.diagramObject.Type;

public class Method extends Attribute{
    private ArrayList<Type> parameters;

    /**
     * 
     * @param name
     * @param parent Under which parent will this Method live
     */
    public Method(String name, UMLObject parent) {
        super();
    }

    /**
     * @param name
     * @param parent Under which parent will this Method live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     */
    public Method(String name, UMLObject parent, Type type, Visibility visibility) {
        super();
    }

    /**
     * @param name
     * @param parent Under which parent will this Method live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     * @param isVisibilityChangable upon true Visibility will become immutable
     */
    public Method(String name, UMLObject parent, Type type, Visibility visibility, boolean isVisibilityChangable) {
        super();
    }

    /**
     * @param name
     * @param parent Under which parent will this Method live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     * @param parameters array of string representation of Types
     */
    public Method(String name, UMLObject parent, Type type, Visibility visibility, String[] parameters) {
        super();
    }

    /**
     * @param name
     * @param parent Under which parent will this Method live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     * @param isVisibilityChangable upon true Visibility will become immutable
     * @param parameters array of string representation of Types
     */
    public Method(String name, UMLObject parent, Type type, Visibility visibility, boolean isVisibilityChangable, String[] parameters) {
        super();
    }

    /**
     * @brief Allows to rename Method unless another Method with the very same signature exists
     */
    @Override
    public boolean setName(String newName) {
        return false;
    }

    /**
     * @return Immutable Array of Types
     */
    public ArrayList<Type> getParameters() {
        return null;
    }

    /**
     * @param newParameters array of string representation of Types
     * @return Success of the operation
     */
    public boolean setParameters(String[] newParameters) {
        return false;
    }
}
