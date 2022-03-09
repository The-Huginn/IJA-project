package backend.diagramObject;

public class Element {
    private String name;

    /**
     * @param name
     */
    public Element(String name) {

    }

    /**
     * @param newName
     * @return True if successful otherwise false
     */
    public boolean setName(String newName) {
        return false;
    }

    /**
     * @return Name of the Element
     */
    public String getName() {
        return null;
    }

    @Override
    public boolean equals(Object anotherObject) {
        return false;
    }

    @Override
    public int hashCode() {
        return -1;
    }
}