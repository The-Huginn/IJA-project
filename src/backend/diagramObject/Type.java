package backend.diagramObject;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.SortedMap;
import java.util.TreeMap;

import javafx.util.Pair;

public class Type extends Element{
    private static SortedMap<String, Type> instances = null;
    private boolean isUserDefined;

    // variables for undo operations
    private static Deque<UndoType> undo_stack = new ArrayDeque<>();
    private static Deque<SortedMap<String, Type>> undo_initTypes = new ArrayDeque<>();
    private static Deque<Pair<String, Type>> undo_Type = new ArrayDeque<>();

    private enum UndoType {
        SetName,
        initTypes,
        clearTypes,
        addType,
        removeType
    }

    public Type() {
        
    }

    /**
     * @param name of new Type
     */
    private Type(String name, boolean isUserDefined) {
        super(name);
        this.isUserDefined = isUserDefined;   
    }

    /**
     * @brief Does not allow to change name to another existing Type or rename immutablly defined types
     */
    @Override
    public boolean setName(String newName) {

        if (!this.isUserDefined())
            return false;

        if (instances.containsKey(newName))
            return false;

        undo_stack.addFirst(UndoType.SetName);
        undo_Type.addFirst(new Pair<String,Type>(this.getName(), this));
            
        instances.remove(this.getName());
        instances.put(newName, this);

        return super.setName(newName);
    }

    /**
     * @brief Can be called only upon empty Map. All types defined this way are immutable unless clearTypes() called.
     * @param types
     * @return True upon success
     */
    public static boolean initTypes(String[] types) {
        
        if (instances != null)
            return false;

        undo_stack.addFirst(UndoType.initTypes);

        instances = new TreeMap<>();
        for (String key:types)
            instances.put(key, new Type(key, false));

        return true;
    }

    /**
     * @brief Clears all types from Map
     */
    public static void clearTypes() {
        
        if (instances == null)
            return;

        undo_stack.addFirst(UndoType.clearTypes);

        undo_initTypes.addFirst(instances);

        instances = null;
    }

    /**
     * @param typeName
     * @return instance for typeName
     */
    public static Type getType(String typeName) {

        if (instances == null || !instances.containsKey(typeName))
            return null;
        
        return instances.get(typeName);
    }

    /**
     * @param typeName
     * @return True upon success otherwise false
     */
    public static boolean addType(String typeName) {

        if (instances.containsKey(typeName))
            return false;

        undo_stack.addFirst(UndoType.addType);

        Type newType = new Type(typeName, true);

        undo_Type.addFirst(new Pair<String,Type>(typeName, newType));
        instances.put(typeName, newType);

        return true;
    }

    /**
     * @param typeName
     * @return True upon deletion of type defined by user, otherwise false
     */
    public static boolean removeType(String typeName) {

        if (!instances.containsKey(typeName) || instances == null )
            return false;

        if (instances.get(typeName).isUserDefined()) {

            undo_stack.addFirst(UndoType.removeType);

            undo_Type.addFirst(new Pair<String,Type>(typeName, instances.get(typeName)));

            instances.remove(typeName);
            return true;
        }

        return false;
    }

    /**
     * @return
     */
    public boolean isUserDefined() {
        return this.isUserDefined;
    }

    /**
     * @brief This method renames the Type via the Element's undo
     */
    public void undoName() {
        super.undo();
    }

    public void undo() {
        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.SetName) {
            Pair<String, Type> top = undo_Type.pop();

            instances.remove(top.getValue().getName());
            instances.put(top.getKey(), top.getValue());

            top.getValue().undoName();

        } else if (type == UndoType.initTypes) {
            instances = null;
        } else if (type == UndoType.clearTypes) {
            instances = undo_initTypes.pop();
        } else if (type == UndoType.addType) {
            Pair<String, Type> top = undo_Type.pop();

            instances.remove(top.getKey());
            
        } else if (type == UndoType.removeType) {
            Pair<String, Type> top = undo_Type.pop();

            instances.put(top.getKey(), top.getValue());
        }
    }
}
