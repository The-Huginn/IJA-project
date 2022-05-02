/**
 * @file Attribute.java
 * @author Rastislav Budinsky (xbudin05), Vladimir Meciar (xmecia00)
 * @brief This file contains class Attribute, inherited by Method
 */
package com.ija.backend.diagramObject;

import java.util.ArrayDeque;
import java.util.Deque;

import org.json.JSONObject;

public class Attribute extends Element {
    public enum Visibility {
        PRIVATE,
        PUBLIC,
        PROTECTED,
        PACKAGE
    }

    private final UMLObject parent;
    private Type type = null;
    private Visibility visibility = Visibility.PUBLIC;
    private boolean isVisibilityChangable = true;

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<String> undo_name = new ArrayDeque<>();
    private Deque<Type> undo_type = new ArrayDeque<>();
    private Deque<Visibility> undo_visibility = new ArrayDeque<>();

    private enum UndoType {
        setName,
        setType,
        setVisibility,
        forceSetName
    }


    /**
     * @param name
     * @param parent Under which parent will this Attribute live
     */
    public Attribute(String name, UMLObject parent) {
        super(name);
        this.parent = parent;
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
    }

    /**
     * @brief Constructor for creating Attribute with unchangable visibility
     * @param name
     * @param parent Under which parent will this Attribute live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     * @param isVisibilityChangable upon false Visibility will become immutable
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

    @Override
    public String toString() {
        final String[] visibilities = {"- ", "+ ", "# ", "~ "};
        return visibilities[getVisibility().ordinal()] + getName() + " : " + getType().getName();
    }

    /**
     * @brief Checks for other attributes in parent if they have the same name or not
     */
    @Override
    public boolean setName(String newName) {
        
        // Just for test cases
        if (parent == null) {
            undo_stack.addFirst(UndoType.setName);
            undo_name.addFirst(this.getName());

            super.setName(newName);
            return true;
        }

        for (Attribute tmpAttribute : parent.getVariables())
            if (tmpAttribute.equals(this))
                return false;

        undo_stack.addFirst(UndoType.setName);

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

        if (newType == null || this.getType() == newType)
            return;
        
        undo_stack.addFirst(UndoType.setType);
        undo_type.addFirst(this.getType());

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
        if (!this.isVisibilityChangable())
            return;

        undo_stack.addFirst(UndoType.setVisibility);
        undo_visibility.addFirst(this.getVisibility());

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

        undo_stack.addFirst(UndoType.forceSetName);
        undo_name.addFirst(this.getName());

        super.setName(newName);
    }

    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.setName) {
            super.undo();
        } else if (type == UndoType.setType) {
            Type top = undo_type.pop();

            this.type = top;
        } else if (type == UndoType.setVisibility) {
            Visibility top = undo_visibility.pop();

            this.visibility = top;
        } else if (type == UndoType.forceSetName) {
            super.undo();
        }
    }

    @Override
    public JSONObject getJSON() {
        JSONObject json = super.getJSON();

        json.put("type", getType().getName());
        json.put("visibility", getVisibility().ordinal());
        json.put("isVisibilityChangable", isVisibilityChangable());

        return json;
    }

    @Override
    public boolean setJSON(JSONObject json) {

        if (!json.has("type") || !json.has("visibility") || !json.has("isVisibilityChangable"))
            return false;

        int vis = json.getInt("visibility");

        if (vis < 0 || vis >= Visibility.values().length)
            return false;

        type = Type.getType(json.getString("type"));
        visibility = Visibility.values()[vis];
        isVisibilityChangable = json.getBoolean("isVisibilityChangable");

        return super.setJSON(json);
    }
}
