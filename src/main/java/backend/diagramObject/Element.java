/**
 * @file Element.java
 * @author Rastislav Budinsky (xbudin05), Vladimir Meciar (xmecia00)
 * @brief This file contains Base class Element
 */
package backend.diagramObject;

import java.util.ArrayDeque;
import java.util.Deque;

import org.json.JSONObject;

import backend.undoInterface;

public class Element implements undoInterface{
    private Deque<String> name_stack = new ArrayDeque<>();
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
        this.name_stack.addFirst(name);
        this.name = newName;

        return true;
    }

    /**
     * @return Name of the Element
     */
    public String getName() {
        return this.name;
    }

    public void undo() {
        if (!this.name_stack.isEmpty())
            this.name = this.name_stack.pop();
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

    /**
     * Converts representation of the object into JSON format
     * @return
     */
    public JSONObject getJSON() {
        JSONObject json = new JSONObject();
        json.put("name", getName());

        return json;
    }

    /**
     * Sets without much controls attributes to values in JSON
     * @param parent
     * @param json JSONObject to be read from
     * @return success of the operation
     */
    public boolean setJSON(JSONObject json) {

        if (!json.has("name"))
            return false;

        name = json.getString("name");

        return true;
    }
}