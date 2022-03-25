package backend.diagramObject;

import java.util.SortedMap;

import backend.diagramObject.Element;

public class Type extends Element{
    private static SortedMap<String, Type> instances;
    private boolean isUserDefined;

    // Maybe default constructor is needed, then create dummy instance

    /**
     * @param name of new Type
     */
    private Type(String name) {
        super();
    }

    /**
     * @brief Does not allow to change name to another existing Type or rename immutablly defined types
     */
    @Override
    public boolean setName(String newName) {
        return false;
    }

    /**
     * @brief Can be called only upon empty Map. All types defined this way are immutable unless clearTypes() called.
     * @param types
     * @return True upon success
     */
    public static boolean initTypes(String[] types) {
        return false;
    }

    /**
     * @brief Clears all types from Map
     */
    public static void clearTypes() {

    }

    /**
     * @param typeName
     * @return instance for typeName
     */
    public static Type getType(String typeName) {
        return null;
    }

    /**
     * @param typeName
     * @return True upon success otherwise false
     */
    public static boolean addType(String typeName) {
        return false;
    }

    /**
     * @param typeName
     * @return True upon deletion of type defined by user, otherwise false
     */
    public static boolean removeType(String typeName) {
        return false;
    }

    /**
     * @return
     */
    public boolean isUserDefined() {
        return false;
    }
}
