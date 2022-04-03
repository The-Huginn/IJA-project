package backend.diagramObject;

import java.util.SortedMap;
import java.util.TreeMap;

public class Type extends Element{
    private static SortedMap<String, Type> instances = null;
    private boolean isUserDefined;

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

        instances = new TreeMap<>();
        for (String key:types)
            instances.put(key, new Type(key, false));

        return true;
    }

    /**
     * @brief Clears all types from Map
     */
    public static void clearTypes() {
        instances.clear();
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

        instances.put(typeName, new Type(typeName, true));
        return true;
    }

    /**
     * @param typeName
     * @return True upon deletion of type defined by user, otherwise false
     */
    public static boolean removeType(String typeName) {

        if (!instances.containsKey(typeName) || instances == null )
            return false;

        if (instances.get(typeName).isUserDefined()){
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
}
