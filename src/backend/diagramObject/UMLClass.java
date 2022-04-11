/**
 * @file UMLClass.java
 * @author Rastislav Budinsky (xbudin05), Vladimir Meciar (xmecia00)
 * @brief This file contains class UMLClass
 */
package backend.diagramObject;

import java.util.ArrayDeque;
import java.util.Deque;

import org.json.JSONObject;

import backend.diagram.ClassDiagram;

public class UMLClass extends UMLObject {

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();

    private enum UndoType {
        addVariable,
        addMethod,
        others
    }
    
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

        if (this.getParent() == null) {
            undo_stack.addFirst(UndoType.others);

            return super.setName(newName);
        }

        for (UMLClass umlClass : this.getParent().getClasses())
            if (umlClass.getName().equals(newName))
                return false;
        
        undo_stack.addFirst(UndoType.others);

        return super.setName(newName);
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

        undo_stack.addFirst(UndoType.addVariable);
        
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

        undo_stack.addFirst(UndoType.addMethod);

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
