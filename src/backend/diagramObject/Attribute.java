package backend.diagramObject;

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
        super(name);
        this.parent = parent;
        this.type = null;
        this.visibility = Visibility.PUBLIC;
        this.isVisibilityChangable = true;
    }

    /**
     * @brief Constructor for creating Attribute with changable visibility
     * @param name
     * @param parent Under which parent will this Attribute live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     */
    public Attribute(String name, UMLObject parent, Type type, Visibility visibility) {
        super(name);
        this.parent = parent;
        this.type = type;
        this.visibility = visibility;
        this.isVisibilityChangable = true;
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
        super(name);
        this.parent = parent;
        this.type = type;
        this.visibility = visibility;
        this.isVisibilityChangable = isVisibilityChangable;
    }

    @Override
    public boolean equals(Object anotherObject) {
        
        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof Attribute))
            return false;

        return ((Attribute)anotherObject).getName().equals(this.getName());
    }

    /**
     * @brief Checks for other attributes in parent if they have the same name or not
     */
    @Override
    public boolean setName(String newName) {
        
        // Just for test cases
        if (parent == null){
            super.setName(newName);
            return true;
        }

        for (Attribute tmpAttribute : parent.getVariables())
            if (tmpAttribute.equals(this))
                return false;  

        super.setName(newName);
        return true;
    }

    /**
     * @return Type of the Attribute
     */
    public Type getType() {
        return this.type;
    }

    /**
     * @brief null is ignored
     * @param newType
     */
    public void setType(Type newType) {

        if (newType == null)
            return;
        
        this.type = newType;
    }
    
    /**
     * @return Visibility of the Attribute
     */
    public Visibility getVisibility() {
        return this.visibility;
    }

    /**
     * @brief By default is changable, can be forbidden upon creation with special constructor
     * @param newVisibility
     */
    public void setVisibility(Visibility newVisibility) {
        if (this.isVisibilityChangable())
            this.visibility = newVisibility;    
    }

    /**
     * @return True if visibility changable otherwise false
     */
    public boolean isVisibilityChangable() {
        return this.isVisibilityChangable;      
    }

    /**
     * @return
     */
    protected final UMLObject getParent() {
        return this.parent;
    }

    /**
     * @brief This method should be called only to forcibly rename Attribute
     */
    protected void forceSetName(String newName) {
        super.setName(newName);
    }
}
