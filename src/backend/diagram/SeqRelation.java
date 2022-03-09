package backend.diagram;

import backend.diagram.Relation;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLObject;

public class SeqRelation extends Relation{
    private SeqRelEnum type;
    private String methodName;
    private String methodParams;
    private int methodParamsCount;
    private String note;

    public enum SeqRelEnum {
        SYNCHROUNOUS,
        ASYNCHRONOUS,
        RETURN,
        CREATION,
        DESTRUCTION
    }

    /**
     * @param name
     */
    public SeqRelation(String name, Diagram parent) {

    }

    public SeqRelation(String name, Diagram parent, UMLClass firstClass, int firstInstance, UMLClass secondClass, int secondInstance) {

    }

    @Override
    public boolean checkCorrect() {
        return false;
    }

    public boolean setFirst(UMLObject instance, Integer instanceNumber) {
        return false;
    }

    public boolean setSecond(UMLObject instance, Integer instanceNumber) {
        return false;
    }

    /**
     * @return String with method invoking relation
     */
    public String getMethod() {
        return null;
    }

    /**
     * @param methodCall
     * @return True if method was changed
     */
    public boolean setMethod(String methodCall) {
        return false;
    }

    /**
     * @return String containing note
     */
    public String getNote() {
        return null;
    }

    /**
     * @param newNote
     */
    public void setNote(String newNote) {
        
    }

    /**
     * @return
     */
    public SeqRelEnum getType() {
        return null;
    }

    /**
     * @param newType
     */
    public void setType(SeqRelEnum newType) {

    }
}
