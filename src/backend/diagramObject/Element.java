package backend.diagramObject;

public class Element {
    private String name;

    public Element() {};

    /**
     * @param name
     */
    public Element(String name) {
        this.name = name;
    }

    /**
     * @param newName
     * @return True if successful otherwise false
     */
    public boolean setName(String newName) {
        this.name = newName;
        return true;
    }

    /**
     * @return Name of the Element
     */
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object anotherObject) {
        
        if (anotherObject== this) {
            return true;
        }
 
        if (!(anotherObject instanceof Element)) {
            return false;
        }
         
        Element tmp = (Element) anotherObject;
         
        return (tmp.getName().equals(this.getName()));
    }
}