package backend.diagram;

import java.util.ArrayDeque;
import java.util.Deque;

import backend.diagramObject.Element;
import backend.diagramObject.UMLObject;
import javafx.util.Pair;

public abstract class Relation extends Element{
    protected Pair<UMLObject, Integer> first = new Pair<UMLObject,Integer>(null, -1);
    protected Pair<UMLObject, Integer> second = new Pair<UMLObject,Integer>(null, -1);
    private final Diagram parent;

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();

    private enum UndoType {
        others
    }

    public Relation(){super();this.parent = null;};

    /**
     * @param name
     * @param parent Under which parent this Relation lives
     */
    public Relation(String name, Diagram parent) {
        super(name);
        this.parent = parent;
    }

    /**
     * @param name
     * @param parent Under which parent this Relation lives
     * @param firstInstance Relation starts from this UMLObject
     * @param firstInstanceNumber instance number of the first UMLObject instance
     * @param secondInstance Relation ends in this UMLObject
     * @param secondInstanceNumber instance number of the second UMLObject instance
     */
    public Relation(String name, Diagram parent, UMLObject firstInstance, int firstInstanceNumber, UMLObject secondInstance, int secondInstanceNumber) {
        super(name);
        this.parent = parent;
        this.first = new Pair<UMLObject,Integer>(firstInstance, firstInstanceNumber);
        this.second = new Pair<UMLObject,Integer>(secondInstance, secondInstanceNumber);
    }

    @Override
    public boolean setName(String newName) {
        undo_stack.addFirst(UndoType.others);
        return super.setName(newName);
    }

    /**
     * @return Checks, whether everything is ok
     */
    abstract boolean checkCorrect();

    /**
     * @return Start point of relation
     */
    public Pair<UMLObject, Integer> getFirst() {
        return first;
    }

    /**
     * @return End point of relation
     */
    public Pair<UMLObject, Integer> getSecond() {
        return second;
    }

    /**
     * @param instance
     * @param instanceNumber
     * @return Success of operation
     */
    abstract boolean setFirst(UMLObject instance, Integer instanceNumber);

    /**
     * @param instance
     * @param instanceNumber
     * @return Success of operation
     */
    abstract boolean setSecond(UMLObject instance, Integer instanceNumber);

    protected final Diagram getParent() {
        return this.parent;
    }

    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.others) {
            super.undo();
        }
    }
}
