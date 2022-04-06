package backend.diagramObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import backend.diagram.ClassDiagram;
import javafx.util.Pair;

public abstract class UMLObject extends Element{
    private final ClassDiagram parent;
    protected List<Attribute> variables = new ArrayList<>();
    protected List<Method> methods = new ArrayList<>();

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Pair<Integer, Attribute>> undo_remove = new ArrayDeque<>();

    private enum UndoType {
        removeVariable,
        removeMethod,
        others
    }

    public UMLObject() {super();this.parent = null;}

    /**
     * @param name
     * @param parent Under which parent this UMLObject lives
     */
    public UMLObject(String name, ClassDiagram parent) {
        super(name);
        this.parent = parent;
    }

    @Override
    public boolean setName(String newName) {
        undo_stack.addFirst(UndoType.others);

        return super.setName(newName);
    }
    
    /**
     * @param attribute
     * @return Success of the operation
     */
    abstract boolean addVariable(Attribute variable);

    /**
     * @param method
     * @return Success of the operation
     */
    abstract boolean addMethod(Method method);

    /**
     * @return True if everything is ok
     */
    abstract boolean checkCorrect();

    /**
     * @return Array with variables
     */
    public List<Attribute> getVariables() {
        return Collections.unmodifiableList(this.variables);
    }

    /**
     * @return Array with methods
     */
    public List<Method> getMethods() {
        return Collections.unmodifiableList(this.methods);
    }

    /**
     * @brief Upon invalid index nothing happens
     * @param index
     */
    public void removeVariable(int index) {

        if (index < 0 || index >= this.variables.size())
            return;

        undo_stack.addFirst(UndoType.removeVariable);
        undo_remove.addFirst(new Pair<Integer,Attribute>(index, variables.get(index)));

        variables.remove(index); 
    }

    /**
     * @brief Upon invalid index nothing happens
     * @param index
     */
    public void removeMethod(int index) {

        if (index < 0 || index >= this.methods.size())
            return;

        undo_stack.addFirst(UndoType.removeMethod);
        undo_remove.addFirst(new Pair<Integer,Attribute>(index, methods.get(index)));
            
        this.methods.remove(index);
    }

    /**
     * @return
     */
    protected final ClassDiagram getParent() {
        return this.parent;
    }

    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.removeVariable) {
            Pair<Integer, Attribute> top = undo_remove.pop();

            variables.add(top.getKey(), top.getValue());
        } else if (type == UndoType.removeMethod) {
            Pair<Integer, Attribute> top = undo_remove.pop();

            methods.add(top.getKey(), (Method)top.getValue());
        } else if (type == UndoType.others) {
            super.undo();
        }
    }
}
