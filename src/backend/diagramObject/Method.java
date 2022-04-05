package backend.diagramObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Method extends Attribute{
    private List<Type> parameters = new ArrayList<>();

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<List<Type>> undo_params = new ArrayDeque<>();

    private enum UndoType {
        setParameters,
        others
    }

    /**
     * 
     * @param name
     * @param parent Under which parent will this Method live
     */
    public Method(String name, UMLObject parent) {
        super(name, parent);
    }

    /**
     * @param name
     * @param parent Under which parent will this Method live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     */
    public Method(String name, UMLObject parent, Type type, Visibility visibility) {
        super(name, parent, type, visibility);
    }

    /**
     * @param name
     * @param parent Under which parent will this Method live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     * @param isVisibilityChangable upon true Visibility will become immutable
     */
    public Method(String name, UMLObject parent, Type type, Visibility visibility, boolean isVisibilityChangable) {
        super(name, parent, type, visibility, isVisibilityChangable);
    }

    /**
     * @param name
     * @param parent Under which parent will this Method live
     * @param type Type of variable or method
     * @param visibility Visibility of this attribute
     * @param parameters array of string representation of Types
     */
    public Method(String name, UMLObject parent, Type type, Visibility visibility, String[] parameters) {

        super(name, parent, type, visibility);

        for (String param : parameters) {            
            if (Type.getType(param) == null)
                continue;

            this.parameters.add(Type.getType(param));
        }
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
        
        super(name, parent, type, visibility, isVisibilityChangable);

        for (String param : parameters) {
            if (Type.getType(param) == null)
                continue;

            this.parameters.add(Type.getType(param));
        }
    }

    @Override
    public boolean equals(Object anotherObject) {

        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof Method))
            return false;

        Method method = (Method)anotherObject;
        
        return  method.getName().equals(this.getName())  &&
                method.getType() == this.getType() &&
                method.getParameters().equals(this.getParameters());
    }

    /**
     * @brief Allows to rename Method unless another Method with the very same signature exists
     */
    @Override
    public boolean setName(String newName) {

        // Just for test cases
        if (this.getParent() == null){
            undo_stack.addFirst(UndoType.others);

            super.forceSetName(newName);
            return true;
        }

        for (Method method : this.getParent().getMethods())
            if (method.getName().equals(newName) && method.getType() == this.getType() && method.getParameters().equals(this.getParameters()))
                return false;

        undo_stack.addFirst(UndoType.others);
        
        super.forceSetName(newName);
        return true;
    }

    /**
     * @return Immutable Array of Types
     */
    public List<Type> getParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    /**
     * @param newParameters array of string representation of Types
     * @return Success of the operation
     */
    public boolean setParameters(String[] newParameters) {

        for (String param : newParameters)
            if (Type.getType(param) == null)
                return false;

        if (this.getParent() != null)
            for (Method method : this.getParent().getMethods())
                if (method.getName().equals(this.getName()) && method.getType() == this.getType() && method.getParameters().size() == newParameters.length) {

                    boolean same = true;
                    List<Type> params = method.getParameters();

                    for (int i = 0; i < params.size(); i++)
                        if (Type.getType(newParameters[i]) != params.get(i))
                            same = false;

                    if (same)
                        return false;
                }

        undo_stack.addFirst(UndoType.setParameters);
        undo_params.addFirst(this.parameters);

        this.parameters = new ArrayList<>();
        for (String param : newParameters)
            this.parameters.add(Type.getType(param));
        
        return true;
    }

    @Override
    public void setType(Type newType) {
        undo_stack.add(UndoType.others);
        super.setType(newType);
    }

    @Override
    public void setVisibility(Visibility newVisibility) {
        undo_stack.add(UndoType.others);
        super.setVisibility(newVisibility);
    }

    public void undo() {
        
        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.setParameters) {
            this.parameters = undo_params.pop();
        } else if (type == UndoType.others) {
            super.undo();
        }
    }
}
