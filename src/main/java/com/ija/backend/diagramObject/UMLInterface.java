/**
 * @file UMLInterface.java
 * @author Rastislav Budinsky (xbudin05), Vladimir Meciar (xmecia00)
 * @brief This file contains class UMLInterface
 */
package com.ija.backend.diagramObject;

import java.util.ArrayDeque;
import java.util.Deque;

import org.json.JSONObject;

import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagramObject.Attribute.Visibility;

public class UMLInterface extends UMLObject{

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();

    private enum UndoType {
        addVariable,
        addMethod,
        others
    }
    
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
        
        if (this.getParent() == null) {
            undo_stack.addFirst(UndoType.others);

            return super.setName(newName);
        }

        for (UMLInterface interface1 : this.getParent().getInterfaces())
            if (this.equals(interface1))
                return false;

        undo_stack.addFirst(UndoType.others);
        
        return super.setName(newName);
        
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

        undo_stack.addFirst(UndoType.addVariable);

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

        undo_stack.addFirst(UndoType.addMethod);
        
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

    @Override
    public void removeVariable(int index) {
        undo_stack.addFirst(UndoType.others);
        super.removeVariable(index);
    }

    @Override
    public void removeMethod(int index) {
        undo_stack.addFirst(UndoType.others);
        super.removeMethod(index);
    }

    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.addVariable) {
            variables.remove(variables.size() - 1);
        } else if (type == UndoType.addMethod) {
            methods.remove(methods.size() - 1);
        } else if (type == UndoType.others) {
            super.undo();
        }
    }

    @Override
    public JSONObject getJSON() {
        return super.getJSON();
    }

    @Override
    public boolean setJSON(JSONObject json) {
        return super.setJSON(json);
    }
}
