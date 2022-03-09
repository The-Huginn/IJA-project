package backend.diagramObject;

import backend.diagramObject.Element;
import backend.diagramObject.UMLObject;
import backend.diagramObject.Type;

public class Attribute extends Element {
    public enum Visibility {
        PRIVATE,
        PUBLIC,
        PROTECTED,
        PACKAGE
    }

    private final UMLObject parent;
    private Type type;
    private Visibility visibility;
    private boolean isVisibilityChangable;

    /**
     * @param name
     * @param parent Under which parent will this Attribute live
     */
    public Attribute(String name, UMLObject parent) {

    }

    /**
     * @brief Constructor for creating Attribute with changable visibility
     * @param name
     * @param parent Under which parent will this Attribute live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     */
    public Attribute(String name, UMLObject parent, Type type, Visibility visibility) {

    }

    /**
     * @brief Constructor for creating Attribute with unchangable visibility
     * @param name
     * @param parent Under which parent will this Attribute live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     * @param isVisibilityChangable upon true Visibility will become immutable
     */
    public Attribute(String name, UMLObject parent, Type type, Visibility visibility, boolean isVisibilityChangable) {

    }

    /**
     * @brief Checks for other attributes in parent if they have the same name or not
     */
    @Override
    public boolean setName(String newName) {
        return false;
    }

    /**
     * @return Type of the Attribute
     */
    public Type getType() {
        return null;
    }

    /**
     * @brief null is ignored
     * @param newType
     */
    public void setType(Type newType) {

    }
    
    /**
     * @return Visibility of the Attribute
     */
    public Visibility getVisibility() {
        return null;
    }

    /**
     * @brief By default is changable, can be forbidden upon creation with special constructor
     * @param newVisibility
     */
    public void setVisibility(Visibility newVisibility) {
        
    }

    /**
     * @return True if visibility changable otherwise false
     */
    public boolean isVisibilityChangable() {
        return false;
    }

    /**
     * @return
     */
    protected final UMLObject getParent() {
        return null;
    }
}
