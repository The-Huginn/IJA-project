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

    private UMLObject parent;
    private Type type;
    private Visibility visibility;
    private boolean isVisibilityChangable;

    /**
     * @param name
     */
    public Attribute(String name, UMLObject parent) {

    }

    /**
     * @brief Constructor for creating Attribute with changable visibility
     * @param name
     * @param type
     * @param visibility
     */
    public Attribute(String name, UMLObject parent, Type type, Visibility visibility) {

    }

    /**
     * @brief Constructor for creating Attribute with unchangable visibility
     * @param name
     * @param type
     * @param visibility
     * @param isVisibilityChangable
     */
    public Attribute(String name, UMLObject parent, Type type, Visibility visibility, boolean isVisibilityChangable) {

    }

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
}
